let gslsAllStatChartData;

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
});

function init() {
    $.ajax({
        url: '/gslsEsb/getAllGslsPmsStat',
        type: 'GET',
        dataType: 'json',
        success: function(data) {
            console.dir(data)
            setApiSuccessIcon();

            //데이터가 존재하지 않을 시 API 호출 상태 ICON 업데이트
            if(!data) {
                setApiFailureIcon();
            }

            gslsAllStatChartData = data.statList;

            doAnimation(data);
            createGslsAllStatChart();
        },
        error: function(error) {
            console.error('Error:', error);
            setApiFailureIcon();
        }
    });
}

// 연도별 정보공시내역 건수
function createGslsAllStatChart() {
    const el = document.getElementById('gslsAllStatChart');
    let data = {
        categories: [],
        series: [
            {
                name : '정보공시내역 건수',
                data : []
            },
            {
                name : '세입세출내역 건수',
                data : []
            },
            {
                name : '재무제표결산 내역 건수',
                data : []
            },
            {
                name : '수급자집행정보 건수',
                data : []
            },
            {
                name : '상세내역사업정보 건수',
                data : []
            },
        ],
    };

    for( var i = 0 ; i < gslsAllStatChartData.length ; i ++ ) {
        data.categories.push(gslsAllStatChartData[i].baseDt2);
        data.series[0].data.push(Number(gslsAllStatChartData[i].ifpbntCnt));
        data.series[1].data.push(Number(gslsAllStatChartData[i].anlrveCnt));
        data.series[2].data.push(Number(gslsAllStatChartData[i].fnlttCnt));
        data.series[3].data.push(Number(gslsAllStatChartData[i].excutCnt));
        data.series[4].data.push(Number(gslsAllStatChartData[i].ddtlbzCnt));
    }

    const theme = getTheme();

    const options = {
        chart: {title: '국고보조금(PMS) 통계 차트', width: 'auto', height: 550},
        legend: {
            align: 'bottom'
        },
        xAxis: {
            title: 'day'
        },
        yAxis: {
            title: 'count'
        },
        tooltip: {
            formatter: (value) => `${value}건`,
        },
        series: {
            dataLabels: {
                visible: true,
                offsetY: -10
            }
        },
        theme
    };

    toastui.Chart.lineChart({el, data, options});
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

function datePickerInit() {
    rangeDatePickerInit();
}

function doAnimation(obj) {
    if(!obj) {
        console.error("국고보조금(PMS) 데이터가 비어있습니다.");
        return;
    }

    // 통계 카드 애니메이션
    gsap.from(".card-group", {
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

    /* 정보공시내역 건수 */
    gsap.to("#ifpbntCnt", {
        innerText: obj.latestStat.ifpbntCnt,
        duration: 3,
        snap: "innerText",
        onUpdate: function () {
            document.querySelector("#ifpbntCnt").innerText =
                Math.floor(this.targets()[0].innerText).toLocaleString();
        }
    });

    /* 세입세출내역 건수 */
    gsap.to("#anlrveCnt", {
        innerText: obj.latestStat.anlrveCnt,
        duration: 3,
        snap: "innerText",
        onUpdate: function () {
            document.querySelector("#anlrveCnt").innerText =
                Math.floor(this.targets()[0].innerText).toLocaleString();
        }
    });

    /* 재무제표결산 내역 건수 */
    gsap.to("#fnlttCnt", {
        innerText: obj.latestStat.fnlttCnt,
        duration: 3,
        snap: "innerText",
        onUpdate: function () {
            document.querySelector("#fnlttCnt").innerText =
                Math.floor(this.targets()[0].innerText).toLocaleString();
        }
    });

    /* 수급자집행정보 건수 */
    gsap.to("#excutCnt", {
        innerText: obj.latestStat.excutCnt,
        duration: 3,
        snap: "innerText",
        onUpdate: function () {
            document.querySelector("#excutCnt").innerText =
                Math.floor(this.targets()[0].innerText).toLocaleString();
        }
    });

    /* 상세내역사업정보 건수 */
    gsap.to("#ddtlbzCnt", {
        innerText: obj.latestStat.ddtlbzCnt,
        duration: 3,
        snap: "innerText",
        onUpdate: function () {
            document.querySelector("#ddtlbzCnt").innerText =
                Math.floor(this.targets()[0].innerText).toLocaleString();
        }
    });

    $('#ifpbntCntYmd').text("(" + obj.latestStat.baseDt2 + " 기준)");
    $('#anlrveCntYmd').text("(" + obj.latestStat.baseDt2 + " 기준)");
    $('#fnlttCntYmd').text("(" + obj.latestStat.baseDt2 + " 기준)");
    $('#excutCntYmd').text("(" + obj.latestStat.baseDt2 + " 기준)");
    $('#ddtlbzCntYmd').text("(" + obj.latestStat.baseDt2 + " 기준)");
}