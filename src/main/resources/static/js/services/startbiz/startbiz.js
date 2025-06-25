let visitDailyData;
let visitMonthlyData;
let visitYearlyData;
let incorpDailyData;
let incorpMonthlyData;
let incorpYearlyData;

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
        ease: "power3.out"
    });

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
    gsap.from(".contentDiv", {
        duration: 1,
        scale: 0.9,
        opacity: 0,
        delay: 1.5,
        ease: "power2.out"
    });
})

function init() {
    $.ajax({
        url: '/startbiz/bizStatsInfoApi',
        type: 'GET',
        dataType: 'json',
        success: function(data) {
            console.dir(data);
            setApiSuccessIcon();

            //데이터가 존재하지 않을 시 API 호출 상태 ICON 업데이트
            if(!data) {
                setApiFailureIcon();
            }

            visitDailyData = data.visitCntDailyList;
            visitMonthlyData = data.visitCntMonthlyList;
            visitYearlyData = data.visitCntYearlyList;
            incorpDailyData = data.incorpCntDailyList;
            incorpMonthlyData = data.incorpCntMonthlyList;
            incorpYearlyData = data.incorpCntYearlyList;

            setStatbizCnt(data);
            createChart();
        },
        error: function(error) { // 에러 시 실행
            console.error('Error:', error);
            // 실패 여부를 H1 태그에 표현하기
            setApiFailureIcon();
        }
    });
}

function createChart() {
    createVisitDailyChart();        // 일일 방문자 수 차트
    createVisitMonthlyChart();      // 월별 방문자 수 차트
    createVisitYearlyChart();       // 연도별 방문자 수 차트
    createIncorpDailyCntChart();    // 일일 법인설립 건수 차트
    createIncorpMonthlyCntChart();  // 원별 법인설립 건수 차트
    createIncorpYearlyCntChart();   // 연도별 법인설립 건수 차트
}

function createVisitDailyChart() {
    {
        const el = document.getElementById('visitCntDailyChart');
        let data = {
            categories: [],
            series: [
                {
                    name: '방문자 수',
                    data: []
                }
            ],
        };

        for( var i = 0 ; i < visitDailyData.length ; i++) {
            data.categories.push(visitDailyData[i].baseDt);
            data.series[0].data.push(Number(visitDailyData[i].cnt));
        }

        const theme = getTheme();

        const options = {
            chart: {title: '일일 방문자 수', width: 'auto', height: 350},
            legend: {visible: false},
            xAxis: {pointOnColumn: false, title: {text: 'day'}},
            yAxis: {title: 'count'},
            tooltip: {
                formatter: (value) => `${value}명`,
            },
            series: {
                dataLabels: {
                    visible: true
                },
                selectable: true
            },
            theme
        };

        //차트 색상 변경
        options.theme.series.colors = ['#02de61'];

        toastui.Chart.columnChart({el, data, options});
    }
}

function createVisitMonthlyChart() {
    {
        const el = document.getElementById('visitCntMonthlyChart');
        let data = {
            categories: [],
            series: [
                {
                    name: '방문자 수',
                    data: []
                }
            ],
        };

        for( var i = 0 ; i < visitMonthlyData.length ; i++) {
            data.categories.push(visitMonthlyData[i].baseDt.substring(0, 7));
            data.series[0].data.push(Number(visitMonthlyData[i].cnt));
        }

        const theme = getTheme();

        const options = {
            chart: {title: '최근 6개월 방문자 수', width: 'auto', height: 350},
            legend: {visible: false},
            xAxis: {pointOnColumn: false, title: {text: 'month'}},
            yAxis: {title: 'count'},
            tooltip: {
                formatter: (value) => `${value}명`,
            },
            series: {
                dataLabels: {
                    visible: true
                },
                selectable: true
            },
            theme
        };

        //차트 색상 변경
        options.theme.series.colors = ['#02de61'];

        toastui.Chart.columnChart({el, data, options});
    }
}

function createVisitYearlyChart() {
    {
        const el = document.getElementById('visitCntYearlyChart');
        let data = {
            categories: [],
            series: [
                {
                    name: '방문자 수',
                    data: []
                }
            ],
        };

        for( var i = 0 ; i < visitYearlyData.length ; i++) {
            data.categories.push(visitYearlyData[i].baseDt.substring(0, 4));
            data.series[0].data.push(Number(visitYearlyData[i].cnt));
        }

        const theme = getTheme();

        const options = {
            chart: {title: '최근 3년 방문자 수', width: 'auto', height: 350},
            legend: {visible: false},
            xAxis: {pointOnColumn: false, title: {text: 'year'}},
            yAxis: {title: 'count'},
            tooltip: {
                formatter: (value) => `${value}명`,
            },
            series: {
                dataLabels: {
                    visible: true
                },
                selectable: true
            },
            theme
        };

        //차트 색상 변경
        options.theme.series.colors = ['#02de61'];

        toastui.Chart.columnChart({el, data, options});
    }
}

function createIncorpDailyCntChart() {
    {
        const el = document.getElementById('incorpCntDailyChart');
        let data = {
            categories: [],
            series: [
                {
                    name: '법인 설립 건수',
                    data: []
                }
            ],
        };

        for( var i = 0 ; i < incorpDailyData.length ; i++) {
            data.categories.push(incorpDailyData[i].baseDt);
            data.series[0].data.push(Number(incorpDailyData[i].cnt));
        }

        const theme = getTheme();

        const options = {
            chart: {title: '법인 설립 건수', width: 'auto', height: 350},
            legend: {visible: false},
            xAxis: {pointOnColumn: false, title: {text: 'day'}},
            yAxis: {title: 'count'},
            tooltip: {
                formatter: (value) => `${value}명`,
            },
            series: {
                dataLabels: {
                    visible: true
                },
                selectable: true
            },
            theme
        };

        //차트 색상 변경
        options.theme.series.colors = ['#98fac2'];

        toastui.Chart.columnChart({el, data, options});
    }
}

function createIncorpMonthlyCntChart() {
    {
        const el = document.getElementById('incorpCntMonthlyChart');
        let data = {
            categories: [],
            series: [
                {
                    name: '법인 설립 건수',
                    data: []
                }
            ],
        };

        for( var i = 0 ; i < incorpMonthlyData.length ; i++) {
            data.categories.push(incorpMonthlyData[i].baseDt.substring(0, 7));
            data.series[0].data.push(Number(incorpMonthlyData[i].cnt));
        }

        const theme = getTheme();

        const options = {
            chart: {title: '최근 6개월 법인 설립 건수', width: 'auto', height: 350},
            legend: {visible: false},
            xAxis: {pointOnColumn: false, title: {text: 'day'}},
            yAxis: {title: 'count'},
            tooltip: {
                formatter: (value) => `${value}명`,
            },
            series: {
                dataLabels: {
                    visible: true
                },
                selectable: true
            },
            theme
        };

        //차트 색상 변경
        options.theme.series.colors = ['#98fac2'];

        toastui.Chart.columnChart({el, data, options});
    }
}

function createIncorpYearlyCntChart() {
    {
        const el = document.getElementById('incorpCntYearlyChart');
        let data = {
            categories: [],
            series: [
                {
                    name: '법인 설립 건수',
                    data: []
                }
            ],
        };

        for( var i = 0 ; i < incorpYearlyData.length ; i++) {
            data.categories.push(incorpYearlyData[i].baseDt.substring(0, 4));
            data.series[0].data.push(Number(incorpYearlyData[i].cnt));
        }

        const theme = getTheme();

        const options = {
            chart: {title: '최근 3년 법인 설립 건수', width: 'auto', height: 350},
            legend: {visible: false},
            xAxis: {pointOnColumn: false, title: {text: 'day'}},
            yAxis: {title: 'count'},
            tooltip: {
                formatter: (value) => `${value}명`,
            },
            series: {
                dataLabels: {
                    visible: true
                },
                selectable: true
            },
            theme
        };

        //차트 색상 변경
        options.theme.series.colors = ['#98fac2'];

        toastui.Chart.columnChart({el, data, options});
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
    picker = rangeDatePickerInit()
}

function setStatbizCnt(obj) {
    if(!obj) {
        console.error('STARTBIZ DATA IS NULL');
        return;
    }

    /* 방문자 수 */
    gsap.to("#visitCnt", {
        innerText: obj.visitCntDailyList[obj.visitCntDailyList.length-1].cnt,
        duration: 3,
        snap: "innerText",
        onUpdate: function () {
            document.querySelector("#visitCnt").innerText =
                Math.floor(this.targets()[0].innerText).toLocaleString();
        }
    });

    /* 법인 설립 건수 */
    gsap.to("#incorpCnt", {
        innerText: obj.incorpCntDailyList[obj.incorpCntDailyList.length-1].cnt,
        duration: 3,
        snap: "innerText",
        onUpdate: function () {
            document.querySelector("#incorpCnt").innerText =
                Math.floor(this.targets()[0].innerText).toLocaleString();
        }
    });

    /* 업종별 법인 설립 건수 */
    gsap.to("#fIncorpCnt", {
        innerText: 0,
        duration: 3,
        snap: "innerText",
        onUpdate: function () {
            document.querySelector("#fIncorpCnt").innerText =
                Math.floor(this.targets()[0].innerText).toLocaleString();
        }
    });

    const date = new Date();
    const year = date.getFullYear();
    let month = (date.getMonth()+1)+"";
    let day = date.getDate()+"";

    // 날짜가 한 자리수 일 경우 "01", "02"... 로 표현하기 위함
    if( day.length === 1 ) {
        day = "0"+day;
    }
    if( month.length === 1 ) {
        month = "0"+month;
    }

    $('#visitYmd').text('(' + obj.visitCntDailyList[obj.visitCntDailyList.length-1].baseDt + ' 기준)');
    $('#incorpYmd').text('(' + obj.incorpCntDailyList[obj.incorpCntDailyList.length-1].baseDt + ' 기준)');
}