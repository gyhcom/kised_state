let totDetCntData;
let detCntByTypeData;

document.addEventListener("DOMContentLoaded", function () {
    datePickerInit();
    init();

    // 로고/타이틀
    gsap.from("#systemNm", {
        duration: 1,
        y: -50,
        opacity: 0,
        ease: "power3.out"
    });

    // 상태 아이콘 + 텍스트
    gsap.from(["#statusIcon", "#statusText"], {
        duration: 1,
        x: -20,
        opacity: 0,
        delay: 0.3,
        stagger: 0.2,
        ease: "power2.out"
    });

    // 날짜 선택 영역 & 버튼
    gsap.from(".date-picker", {
        duration: 1,
        y: -50,
        opacity: 0,
        ease: "power3.out",
        onComplete: () => {
            document.querySelector('.date-picker').style.transform = 'none';
        }
    });

    // 그리드 애니메이션
    gsap.from(".grid-section", {
        duration: 1,
        y: 50,
        opacity: 0,
        stagger: 0.2,
        delay: 1,
        ease: "back.out(1.4)"
    });

    // 차트 카드 애니메이션
    gsap.from("#contentDiv", {
        duration: 1,
        scale: 0.9,
        opacity: 0,
        delay: 1.5,
        ease: "power2.out"
    });
});

function init() {
    $.ajax({
        url: '/fds/getFdsStatsCnt',
        type: 'GET',
        dataType: 'json',
        success: function(data) {
            console.dir(data);
            setApiSuccessIcon();

            //데이터가 존재하지 않을 시 API 호출 상태 ICON 업데이트
            if(!data) {
                setApiFailureIcon();
            }

            totDetCntData = data.cntStatsList;
            detCntByTypeData = data.detCntByTypeList;

            createTotDetCntChart();
            createDetCntByTypeGrid();
        },
        error: function(error) {
            console.error('Error:', error);
            setApiFailureIcon();
        }
    });
}

function createDetCntByTypeGrid(gridData) {
    serviceGrid = new tui.Grid({
        el: document.getElementById('grid'),
        data: detCntByTypeData,
        scrollX: false,
        scrollY: false,
        columns: [
            {
                header: '탐지유형코드',
                name: 'detTpCd',
                width: [250],
                resizable: [true]
            },
            {
                header: '탐지유형명',
                name: 'detTpNm',
                width: [800],
                resizable: [true]
            },
            {
                header: '탐지구분명',
                name: 'detSeNm',
                width: [400],
                resizable: [true]
            },
            {
                header: '탐지건수',
                name: 'cnt',
                width: [100],
                resizable: [true]
            },
            {
                header: '탐지일',
                name: 'baseDt2',
                width: [200],
                resizable: [true]
            }
        ],
        rowHeaders: ['rowNum'],
        pageOptions: {
            useClient: true,
            perPage: 10
        },
        summary: {
            position: 'bottom',
            height: 40,
            columnContent: {
                detTpCd: {
                    template(summary) {
                        return 'TOTAL : ' + summary.cnt;
                    }
                }
            }
        }
    });
}

// 연도별 사전 탐지 건수 차트 생성
function createTotDetCntChart() {
    const el = document.getElementById('totDetCntChart');
    let data = {
        categories: [],
        series: [
            {
                name: '이상거래 탐지 건수',
                data: []
            }
        ],
    };

    for( var i = 0 ; i < totDetCntData.length ; i++ ) {
        data.categories.push(totDetCntData[i].baseDt2);
        data.series[0].data.push(Number(totDetCntData[i].detTotCnt));
    }

    const theme = getTheme();

    const options = {
        chart: {title: '일일 이상거래 탐지 건수 차트', width: 'auto', height: 350},
        legend: {visible: false},
        xAxis: {pointOnColumn: false, title: {text: 'day'}},
        yAxis: {title: 'count'},
        series: {
            dataLabels: { visible: true },
            selectable: true
        },
        theme
    };

    toastui.Chart.columnChart({el, data, options});
}

function datePickerInit() {
    rangeDatePickerInit()
}

function excelDownload() {
    serviceGrid.export('xls', { onlySelected: true });
}

function getTheme() {
    return {
        series: {
            dataLabels: {
                fontFamily: 'Arial',
                fontSize: 12,
                fontWeight: 400,
                color: '#dc3545',
                textBubble: {visible: true, arrow: {visible: true}},
            },
        },
    };
}