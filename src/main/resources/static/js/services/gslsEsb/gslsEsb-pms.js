let ifpbntDailyData;    /* 정보공시내역 최근 30일 통계 데이터 */
let anlrveDailyData;    /* 세입세출내역 최근 30일 통계 데이터 */
let fnlttDailyData;     /* 재무제표결산서 최근 30일 통계 데이터 */
let gslsPmsExcutMonthlyData;    /* 수급자집행정보 최근 6개월 월별 데이터 */
let gslsPmsDdtlbzYearlyData;    /* 상세내역사업정보 최근 3년 연도별 데이터 */
let picker;
document.addEventListener("DOMContentLoaded", function () {
    datePickerInit();
    init();
    createModalGrid(); /* detailModal.js */

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

    // 기간별 통계 조회 버튼
    gsap.from(".stats-btn", {
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
            console.dir(data);

            //데이터가 존재하지 않을 시 API 호출 상태 ICON 업데이트
            if(!data) {
                setApiFailureIcon();
            }

            ifpbntDailyData = data.ifpbntCntDaily;
            anlrveDailyData = data.anlrveCntDaily;
            fnlttDailyData = data.fnlttCntDaily;
            gslsPmsExcutMonthlyData = data.excutCntMonthly;
            gslsPmsDdtlbzYearlyData = data.ddtlbzCntYearly;

            doAnimation();
            createGslsAllStatChart();
            createExcutMonthlyChart();
            createDdtlbzYearlyChart();
        },
        error: function(error) {
            console.error('Error:', error);
            setApiFailureIcon();
        }
    });
}

// 연도별 정보공시내역 건수
function createGslsAllStatChart() {
    {
        const el = document.getElementById('gslsAllStatChart');
        const data = {
            categories: [],
            series: [
                {
                    name: '정보공시내역 건수',
                    data: [],
                },
                {
                    name: '세입세출내역 건수',
                    data: [],
                },
                {
                    name: '재무제표결산 내역 건수',
                    data: [],
                }
            ],
        };

        // 정보공시내역 세팅
        if(ifpbntDailyData != null && ifpbntDailyData.length > 0) {
            for( var i = 0 ; i < ifpbntDailyData.length ; i++) {
                // TODO baseDt는 정보공시내역, 세입세출내역, 재무제표결산서 데이터 리스트의 length가 모두 동일하다고 가정하고 세팅. 더 좋은 방법 모색하기
                data.categories.push(ifpbntDailyData[i].baseDt);
                data.series[0].data.push(Number(ifpbntDailyData[i].cnt))
            }
        }

        // 세입세출내역 세팅
        if(anlrveDailyData != null && anlrveDailyData.length > 0) {
            for( var i = 0 ; i < anlrveDailyData.length ; i++) {
                data.series[0].data.push(Number(anlrveDailyData[i].cnt))
            }
        }

        // 재무제표결산서 세팅
        if(fnlttDailyData != null && fnlttDailyData.length > 0) {
            for( var i = 0 ; i < fnlttDailyData.length ; i++) {
                data.series[0].data.push(Number(fnlttDailyData[i].cnt))
            }
        }

        const theme = getTheme();

        const options = {
            chart: { title: '국고보조금(PMS) 통계 차트', width: 'auto', height: 350 },
            xAxis: {
                title: 'day',
            },
            yAxis: {
                title: 'Count',
            },
            tooltip: {
                formatter: (value) => `${value}건`,
            },
            legend: {
                align: 'bottom',
            },
            series: {
                dataLabels: {
                    visible: true,
                    offsetY: -10
                },
            },
            theme,
        };

        toastui.Chart.lineChart({ el, data, options });
    }
}

// 월별 정보공시내역 건수
function createExcutMonthlyChart() {
    {
        const el = document.getElementById('gslsExcutChart');
        let data = {
            categories: [],
            series: [
                {
                    name: '수급자집행정보 건수',
                    data: []
                }
            ],
        };

        for( var i = 0 ; i < gslsPmsExcutMonthlyData.length ; i++) {
            data.categories.push(gslsPmsExcutMonthlyData[i].baseDt.substring(0, 7)); /* YYYY-MM까지 표현 */
            data.series[0].data.push(Number(gslsPmsExcutMonthlyData[i].cnt));
        }

        const theme = getTheme();

        const options = {
            chart: { title: '최근 6개월 수급자집행정보 통계', width: 'auto', height: 270 },
            legend: {visible: false},
            xAxis: {pointOnColumn:false, title: {text: 'month'}},
            yAxis: {title: 'count'},
            tooltip: {
                formatter: (value) => `${value}건`,
            },
            series: {
                dataLabels: {
                    visible: true,
                },
                selectable: true
            },
            theme,
        };

        toastui.Chart.columnChart({ el, data, options });
    }
}

// 연도별 세입세출내역 건수
function createDdtlbzYearlyChart() {
    {
        const el = document.getElementById('gslsDdtlbzChart');
        let data = {
            categories: [],
            series: [
                {
                    name: '상세내역사업정보 건수',
                    data: []
                }
            ],
        };

        for( var i = 0 ; i < gslsPmsDdtlbzYearlyData.length ; i++) {
            data.categories.push(gslsPmsDdtlbzYearlyData[i].baseDt.substring(0, 4)); /* YYYY 까지 표현 */
            data.series[0].data.push(Number(gslsPmsDdtlbzYearlyData[i].cnt));
        }

        const theme = getTheme();

        const options = {
            chart: { title: '최근 3년 상세정보내역사업 통계', width: 'auto', height: 270 },
            legend: {visible: false},
            xAxis: {pointOnColumn:false, title: {text: 'year'}},
            yAxis: {title: 'count'},
            tooltip: {
                formatter: (value) => `${value}건`,
            },
            series: {
                dataLabels: {
                    visible: true,
                },
                selectable: true
            },
            theme,
        };

        toastui.Chart.columnChart({ el, data, options });
    }
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
    picker = rangeDatePickerInit();
}

function doAnimation() {
    // 차트 카드 애니메이션
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

    // 최근 30일 정보공시내역 건수 sum
    let ifpbntSum = 0;
    for( var i = 0 ; i < ifpbntDailyData.length ; i++ ) {
        ifpbntSum += Number(ifpbntDailyData[i].cnt);
    }

    /* 최근 30일 정보공시내역 건수 */
    gsap.to("#ifpbntCnt", {
        innerText: ifpbntSum,
        duration: 3,
        snap: "innerText",
        onUpdate: function() {
            document.querySelector("#ifpbntCnt").innerText =
                Math.floor(this.targets()[0].innerText).toLocaleString();
        }
    })

    // 최근 30일 세입세출내역 건수 sum
    let anlrveSum = 0;
    for( var i = 0 ; i < fnlttDailyData.length ; i++ ) {
        anlrveSum += Number(anlrveDailyData[i].cnt);
    }

    /* 최근 30일 세입세출내역 건수 */
    gsap.to("#anlrveCnt", {
        innerText: anlrveSum,
        duration: 3,
        snap: "innerText",
        onUpdate: function() {
            document.querySelector("#anlrveCnt").innerText =
                Math.floor(this.targets()[0].innerText).toLocaleString();
        }
    })

    // 최근 30일 재무제표결산 내역 건수 sum
    let fnlttSum = 0;
    for( var i = 0 ; i < fnlttDailyData.length ; i++ ) {
        fnlttSum += Number(fnlttDailyData[i].cnt);
    }

    /* 최근 30일 재무제표결산 내역 건수 */
    gsap.to("#fnlttCnt", {
        innerText: fnlttSum,
        duration: 3,
        snap: "innerText",
        onUpdate: function() {
            document.querySelector("#fnlttCnt").innerText =
                Math.floor(this.targets()[0].innerText).toLocaleString();
        }
    })

    $("#ifpbntCntYmd").text("(" + ifpbntDailyData[0].baseDt + " ~ " + ifpbntDailyData[ifpbntDailyData.length-1].baseDt + " 합산 기준)");
    $("#anlrveCntYmd").text("(" + anlrveDailyData[0].baseDt + " ~ " + anlrveDailyData[anlrveDailyData.length-1].baseDt + " 합산 기준)");
    $("#fnlttCntYmd").text("(" + fnlttDailyData[0].baseDt + " ~ " + fnlttDailyData[fnlttDailyData.length-1].baseDt + " 합산 기준)");
}