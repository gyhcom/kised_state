let dailyKeywordData;
let weeklyKeywordData;

document.addEventListener("DOMContentLoaded", function () {
    datePickerInit();
    init();
})

function init() {
    $.ajax({
        url: '/kstup/lginCnt',
        type: 'GET',
        dataType: 'json',
        success: function(data) {
            console.dir(data);

            setApiSuccessIcon();

            //데이터가 존재하지 않을 시 API 호출 상태 ICON 업데이트
            if(!data) {
                setApiFailureIcon();
            }

            dailyKeywordData[2];
            weeklyKeywordData[3];

            doAnimation(data);
            createUserActChart();
            createPopKeywordChart();
        },
        error: function(error) { // 에러 시 실행
            console.error('Error:', error);
            // 실패 여부를 H1 태그에 표현하기
            setApiFailureIcon();
        }
    });
}

function createUserActChart() {
    {
        const el = document.getElementById('annualChart');
        const data = {
            categories: [
                '2025-03-29',
                '2025-03-30',
                '2025-03-31',
                '2025-04-01',
                '2025-04-02',
                '2025-04-03',
                '2025-04-04',
                '2025-04-05',
                '2025-04-06',
                '2025-04-07',
                '2025-04-08',
                '2025-04-09',
            ],
            series: [
                {
                    name: '회원 수',
                    data: [-3.5, -1.1, 4.0, 11.3, 17.5, 21.5, 25.9, 27.2, 24.4, 13.9, 6.6, -0.6],
                },
                {
                    name: '로그인 수',
                    data: [3.8, 5.6, 7.0, 9.1, 12.4, 15.3, 17.5, 17.8, 15.0, 10.6, 6.6, 3.7],
                },
                {
                    name: '로그인 수(중복 제거)',
                    data: [22.1, 22.0, 20.9, 18.3, 15.2, 12.8, 11.8, 13.0, 15.2, 17.6, 19.4, 21.2],
                },
                {
                    name: '방문자 수',
                    data: [-10.3, -9.1, -4.1, 4.4, 12.2, 16.3, 18.5, 16.7, 10.9, 4.2, -2.0, -7.5],
                }
            ],
        };
        const theme = {
            series: {
                dataLabels: {
                    fontFamily: 'monaco',
                    fontSize: 10,
                    fontWeight: 300,
                    useSeriesColor: true,
                    textBubble: {
                        visible: true,
                        paddingY: 3,
                        paddingX: 6,
                        arrow: {
                            visible: true,
                            width: 5,
                            height: 5,
                            direction: 'bottom',
                        },
                    },
                },
            },
        };

        const options = {
            chart: { title: '사용자 활동 Chart', width: 'auto', height: 550 },
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

function createMonthlyChart() {
    // month 차트
    {
        const el = document.getElementById('loginCntMonthChart');
        let data = {
            categories: [],
            series: [],
        };

        let monthly_data = [];

        // 각 서비스의 API 호출 성공 여부에 영향을 미치게 하지 않기 위함
        if( monthlyLoginData != null && monthlyLoginData.length > 0 ) {
            //categories 세팅
            for( var i = 0 ; i < monthlyLoginData.length ; i++ ) {
                /**
                 * categories(예시로는 페이지 별 사용률에서 '페이지')가 서비스마다 동일하게 가져올 수 있다면
                 * 하나의 차트에 여러 서비스를 보여줄 수 있음
                 */
                data.categories.push(monthlyLoginData[i].month);
                //임시용이기 때문에 만들어진 함수 재사용함. usage -> cnt로 바뀌어야 하는게 맞음
                monthly_data.push(monthlyLoginData[i].usage);
            }

            data.series.push({
                name : 'Count',
                data : monthly_data
            })
        }

        const theme = getTheme();

        const options = {
            chart: {title: monthlyLoginData[0].year + '년 월별 로그인 수', width: 'auto', height: 350},
            legend: {visible: false},
            xAxis: {pointOnColumn: false, title: {text: 'year'}},
            yAxis: {title: 'count'},
            series: {
                showDot: true, dataLabels: { visible: true, offsetY: -10 }, selectable: true
            },
            theme
        };
        //차트 색상 변경
        options.theme.series.colors = ['#49c9ed'];

        monthlyLoginChart = toastui.Chart.columnChart({ el, data, options });

        monthlyLoginChart.on('selectSeries', function(e) {
            const year = monthlyLoginData[0].year;
            const month = e.column[0].data.category;
            monthlyLoginChartClick(year, month);
        })
    }
}

function createMembDailyChart() {
    {
        const el = document.getElementById('membCntDailyChart');
        let data = {
            categories: [],
            series: []
        };

        let daily_data = [];

        // 각 서비스의 API 호출 성공 여부에 영향을 미치게 하지 않기 위함
        if( dailyLoginData != null && dailyLoginData.length > 0 ) {
            //categories 세팅
            for( var i = 0 ; i < dailyLoginData.length ; i++ ) {
                /**
                 * categories(예시로는 페이지 별 사용률에서 '페이지')가 서비스마다 동일하게 가져올 수 있다면
                 * 하나의 차트에 여러 서비스를 보여줄 수 있음
                 */
                data.categories.push(dailyLoginData[i].day);
                daily_data.push(dailyLoginData[i].cnt);
            }

            data.series.push({
                name : 'Count',
                data : daily_data
            })
        }

        const theme = getTheme();

        const options = {
            chart: {title: dailyLoginData[0].year + '년 ' + dailyLoginData[0].month + '월 일별 회원 수', width: 'auto', height: 350},
            legend: {visible: false},
            xAxis: {pointOnColumn: false, title: {text: 'day'}},
            yAxis: {title: 'count'},
            series: {
                dataLabels: {
                    visible: true,
                },
                selectable: true
            },
            theme
        };
        //차트 색상 변경
        options.theme.series.colors = ['#49eddd'];

        dailyLoginChart = toastui.Chart.columnChart({ el, data, options });
    }
}

function createPopKeywordChart() {
    createDailyPopKeyword();
    createWeeklyPopKeyword();
}

function createWeeklyPopKeyword() {
    {
        const el = document.getElementById('popKeywordWeeklyChart');
        let data = {
            // page -> 이 값들은 모두 동일할 필요가 있을 것 같음.(여러 시스템의 데이터를 하나의 차트에서 보여준다면)
            categories: ['최근 7일 인기검색어 Top10'],
            series: [],
        };

        // 각 서비스의 API 호출 성공 여부에 영향을 미치게 하지 않기 위함
        if( weeklyKeywordData != null && weeklyKeywordData.resultData.length > 0 ) {
            for( var i = 0 ; i < weeklyKeywordData.resultData.length ; i++) {
                let obj = {};
                obj.name = weeklyKeywordData.resultData[i].rank;
                obj.data = 10;  // pie 차트는 '100'이라는 값이 최종적으로 제공되어야 함. 데이터 10건(Top10) * 10은 100이기 때문에 이렇게 세팅함
                data.series.push(obj);
            }
        }

        const theme = getTheme();

        const options = {
            chart: {title: '최근 7일 인기검색어 Top10', width: 'auto', height: 500},
            series: {
                dataLabels: {
                    visible: true,
                    pieSeriesName: {
                        visible: true,
                    },
                },
            },
            theme,
            legend: {
                visible: true
            }
        };

        toastui.Chart.pieChart({el, data, options});
    }
}

function createDailyPopKeyword() {
    {
        const el = document.getElementById('popKeywordDailyChart');
        let data = {
            categories: ['일일 인기검색어 Top10'],
            series: [],
        };

        // 각 서비스의 API 호출 성공 여부에 영향을 미치게 하지 않기 위함
        if( dailyKeywordData != null && dailyKeywordData.resultData.length > 0 ) {
            for( var i = 0 ; i < dailyKeywordData.resultData.length ; i++) {
                let obj = {};
                obj.name = dailyKeywordData.resultData[i].rank;
                obj.data = 10;  // pie 차트는 '100'이라는 값이 최종적으로 제공되어야 함. 데이터 10건(Top10) * 10은 100이기 때문에 이렇게 세팅함
                data.series.push(obj);
            }
        }

        const theme = getTheme();

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

        const options = {
            chart: {title: year + '년 ' + month + '월 ' + day + '일 인기 검색 키워드 Top10', width: 'auto', height: 500},
            series: {
                dataLabels: {
                    visible: true,
                    pieSeriesName: {
                        visible: true,
                    },
                },
                selectable: false,
            },
            theme,
            legend: {
                visible: true
            }
        };

        toastui.Chart.pieChart({el, data, options});
    }
}

function getTheme() {
    return {
        series: {
            lineWidth: 2,
            strokeStyle: '#000000',
            hover: {
              color: '#335F70',
              lineWidth: 2,
              strokeStyle: '#000000',
              shadowColor: 'rgba(0, 0, 0, 0.5)',
              shadowBlur: 10,
            },
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

function validateData(obj) {
    if(!currentMemberData || currentMemberData.length <= 0) return false;
    if(!annualMemberData || annualMemberData.length <= 0) return false;
    if(!monthlyMemberData || monthlyMemberData.length <= 0) return false;

    if(!annualLoginData || annualLoginData.length <= 0) return false;
    if(!monthlyLoginData || monthlyLoginData.length <= 0) return false;
    if(!dailyLoginData || dailyLoginData.length <= 0) return false;

    return true;
}

function datePickerInit() {
    rangeDatePickerInit()
}

function doAnimation() {
    if(!obj) {
        console.error('K-STARTUP DATA IS NULL');
        return;
    }

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
        ease: "power3.out"
    });

    // 통계 카드 애니메이션
    gsap.from(".card-group", {
        duration: 1,
        y: 50,
        opacity: 0,
        stagger: 0.2,
        delay: 1,
        ease: "back.out(1.4)"
    });

    /* 회원수 */
    gsap.to("#currentMembCnt", {
        innerText: 14621,
        duration: 3,
        snap: "innerText",
        onUpdate: function () {
            document.querySelector("#currentMembCnt").innerText =
                Math.floor(this.targets()[0].innerText).toLocaleString();
        }
    });

    /* 로그인 수 */
    gsap.to("#loginCnt", {
        innerText: 7856,
        duration: 3,
        snap: "innerText",
        onUpdate: function () {
            document.querySelector("#loginCnt").innerText =
                Math.floor(this.targets()[0].innerText).toLocaleString();
        }
    });

    /* 로그인(중복제거) 수 */
    gsap.to("#loginOrigCnt", {
        innerText: 4211,
        duration: 3,
        snap: "innerText",
        onUpdate: function () {
            document.querySelector("#loginOrigCnt").innerText =
                Math.floor(this.targets()[0].innerText).toLocaleString();
        }
    });

    /* 방문자 수 */
    gsap.to("#visitCnt", {
        innerText: 6608,
        duration: 3,
        snap: "innerText",
        onUpdate: function () {
            document.querySelector("#visitCnt").innerText =
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

    $('#currMembYmd').text('(' + year + '-' + month + '-' + day + ' 기준)');
    $('#loginYmd').text('(' + year + '-' + month + '-' + day + ' 기준)');
    $('#loginOrigYmd').text('(' + year + '-' + month + '-' + day + ' 기준)');
    $('#visitYmd').text('(' + year + '-' + month + '-' + day + ' 기준)');
}


// function annualMemberRateInit(obj) {
//     $('#yearMembCnt').text(obj[0].cnt);
//
//     $('#yearMembTitle').text(obj[0].year+"년도 기준 회원수");
//
//     var calcedRate = calcMemberRate(obj[0].cnt);
//
//     //monthMembRate
//     calcedRate >= 0 ? $('#yearMembRate').css('color', 'red') : $('#yearMembRate').css('color', 'blue')
//
//     $('#yearMembRate').text(calcedRate+"% (현재 대비)");
// }
//
// function monthlyMemberRateInit(obj) {
//     $('#monthMembCnt').text(obj[0].cnt);
//
//     $('#monthMembTitle').text(obj[0].year+"년 "+obj[0].month+"월 기준 회원수");
//
//     var calcedRate = calcMemberRate(obj[0].cnt);
//
//     calcedRate >= 0 ? $('#monthMembRate').css('color', 'red') : $('#monthMembRate').css('color', 'blue');
//
//     $('#monthMembRate').text(calcedRate+"% (현재 대비)");
// }
//
// //회원수 현재 대비 증감률 계산
// //{(변화한 값 – 처음 값) / 처음 값} x 100 = 변화율(%)
// function calcMemberRate(cnt) {
//     //console.log("cnt : " + cnt + " / current" + currentMemberData[0].cnt);
//     var rate = ((parseInt(cnt) - parseInt(currentMemberData[0].cnt)) / parseInt(currentMemberData[0].cnt)) * 100;
//     //소숫점 반올림
//     rate = rate.toFixed(2);
//
//     return parseInt(cnt) >= parseInt(currentMemberData[0].cnt) ? "+" + rate : rate;
// }