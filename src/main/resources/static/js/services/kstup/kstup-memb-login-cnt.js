let dailyKeywordData;
let weeklyKeywordData;
let dailyCntData;

document.addEventListener("DOMContentLoaded", function () {
    datePickerInit();
    init();
})

function init() {
    $.ajax({
        url: '/kstup/getUserActStatistics',
        type: 'GET',
        dataType: 'json',
        success: function(data) {
            console.dir(data);

            setApiSuccessIcon();

            //데이터가 존재하지 않을 시 API 호출 상태 ICON 업데이트
            if(!data) {
                setApiFailureIcon();
            }

            dailyKeywordData= data.dailyPopKeyword;
            weeklyKeywordData= data.weeklyPopKeyword;
            dailyCntData= data.dailyCntList;

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
        const el = document.getElementById('membCntDailyChart');
        const data = {
            categories: [],
            series: [
                {
                    name: '회원 수',
                    data: [],
                },
                {
                    name: '로그인 수',
                    data: [],
                },
                {
                    name: '로그인 수(중복 제거)',
                    data: [],
                },
                {
                    name: '방문자 수',
                    data: [],
                }
            ],
        };

        if( dailyCntData != null ) {
            for( var i = 0 ; i < dailyCntData.length ; i++ ) {
                data.categories.push(dailyCntData[i].baseDt);
                data.series[0].data.push(Number(dailyCntData[i].mnpwCnt));
                data.series[1].data.push(Number(dailyCntData[i].lginCnt));
                data.series[2].data.push(Number(dailyCntData[i].lginDupCnt));
                data.series[3].data.push(0); // 방문자 수 API는 아직 없기 때문에 0으로 세팅함
            }
        }

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
                obj.data = (1 / weeklyKeywordData.resultData.length) * 100;  // pie 차트는 '100'이라는 값이 최종적으로 제공되어야 함.
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
                obj.data = (1 / dailyKeywordData.resultData.length) * 100;  // pie 차트는 '100'이라는 값이 최종적으로 제공되어야 함.
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

function doAnimation(obj) {
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
        ease: "power3.out",
        onComplete: () => {
            document.querySelector('.date-picker').style.transform = 'none';
        }
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
        innerText: obj.userCnt.resultData.cnt,
        duration: 3,
        snap: "innerText",
        onUpdate: function () {
            document.querySelector("#currentMembCnt").innerText =
                Math.floor(this.targets()[0].innerText).toLocaleString();
        }
    });

    /* 로그인 수 */
    gsap.to("#loginCnt", {
        innerText: obj.lginCnt.lginCnt,
        duration: 3,
        snap: "innerText",
        onUpdate: function () {
            document.querySelector("#loginCnt").innerText =
                Math.floor(this.targets()[0].innerText).toLocaleString();
        }
    });

    /* 로그인(중복제거) 수 */
    gsap.to("#loginOrigCnt", {
        innerText: obj.lginCnt.lginDuplCnt,
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
    $('#loginYmd').text('(' + obj.lginCnt.baseDt + ' 기준)');
    $('#loginOrigYmd').text('(' + obj.lginCnt.baseDt + ' 기준)');
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