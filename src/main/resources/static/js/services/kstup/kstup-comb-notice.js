let currentMemberData;
let annualMemberData;
let monthlyMemberData;

let annualLoginData;
let monthlyLoginData;
let dailyLoginData;

let annualLoginChart;
let monthlyLoginChart;
let dailyLoginChart;

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

    // 차트 카드 애니메이션
    gsap.from("#contentDiv", {
        duration: 1,
        scale: 0.9,
        opacity: 0,
        delay: 1.5,
        ease: "power2.out"
    });
})

function init(year, month) {
    $.ajax({
        url: '/kstup/intgPbancRegCnt',
        type: 'GET',
        dataType: 'json',
        success: function(data) {
            console.dir(data);

            setApiSuccessIcon();

            //데이터가 존재하지 않을 시 API 호출 상태 ICON 업데이트
            if(!data) {
                setApiFailureIcon();
            }

            setCombNotice(data);
            createPbancRegChart();
        },
        error: function(error) { // 에러 시 실행
            console.error('Error:', error);
            // 실패 여부를 H1 태그에 표현하기
            setApiFailureIcon();
        }
    });
}

function createPbancRegChart() {
    {
        const el = document.getElementById('pbancRegChart');
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
                    name: '통합공고 등록 건수',
                    data: [-3.5, -1.1, 4.0, 11.3, 17.5, 21.5, 25.9, 27.2, 24.4, 13.9, 6.6, -0.6],
                },
                {
                    name: '공고등록 기관 수',
                    data: [3.8, 5.6, 7.0, 9.1, 12.4, 15.3, 17.5, 17.8, 15.0, 10.6, 6.6, 3.7],
                },
                {
                    name: '기관별 공고 등록 건수',
                    data: [22.1, 22.0, 20.9, 18.3, 15.2, 12.8, 11.8, 13.0, 15.2, 17.6, 19.4, 21.2],
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
            chart: { title: '공고 등록 관련 건수 Chart', width: 'auto', height: 550 },
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

function validateData() {
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

function setCombNotice(obj) {
    if(!obj) {
        console.error('공고등록 관련 데이터가 비어있습니다.');
        return;
    }

    /* 통합공고 등록 건수 */
    gsap.to("#combRegCnt", {
        innerText: 1874,
        duration: 3,
        snap: "innerText",
        onUpdate: function () {
            document.querySelector("#combRegCnt").innerText =
                Math.floor(this.targets()[0].innerText).toLocaleString();
        }
    });

    /* 공고등록 기관 수 */
    gsap.to("#instCnt", {
        innerText: 78,
        duration: 3,
        snap: "innerText",
        onUpdate: function () {
            document.querySelector("#instCnt").innerText =
                Math.floor(this.targets()[0].innerText).toLocaleString();
        }
    });

    /* 기관별 공고등록 건수 */
    gsap.to("#instRegCnt", {
        innerText: 341,
        duration: 3,
        snap: "innerText",
        onUpdate: function () {
            document.querySelector("#instRegCnt").innerText =
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

    $('#combRegYmd').text('(' + year + '-' + month + '-' + day + ' 기준)');
    $('#instYmd').text('(' + year + '-' + month + '-' + day + ' 기준)');
    $('#instRegYmd').text('(' + year + '-' + month + '-' + day + ' 기준)');
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