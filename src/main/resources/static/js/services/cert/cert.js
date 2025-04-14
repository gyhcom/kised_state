let currentMemberData;
let annualMemberData;
let monthlyMemberData;

let annualLoginData;
let monthlyLoginData;
let dailyLoginData;

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
})

function init(year, month) {
    setCertCnt();
    createCertDailyChart();
    // $.ajax({
    //     url: '/kstup/getMembAndLoginCnt?year='+year+'&month='+month,
    //     type: 'GET',
    //     dataType: 'json',
    //     success: function(data) {
    //         /**
    //          * [0] : 현재 회원 수
    //          * [1] : 연도별 회원 수
    //          * [2] : 월별 회원 수
    //          * [3] : 연도별 로그인 수
    //          * [4] : 월별 로그인 수
    //          * [5] : 일일 로그인 수
    //          */
    //         currentMemberData = data[0];
    //         annualMemberData = data[1];
    //         monthlyMemberData = data[2];
    //
    //         annualLoginData = data[3];
    //         monthlyLoginData = data[4];
    //         dailyLoginData = data[5];
    //
    //         setApiSuccessIcon();
    //
    //         //데이터가 존재하지 않을 시 API 호출 상태 ICON 업데이트
    //         if(!validateData()) {
    //             setApiFailureIcon();
    //         }
    //
    //         //createAnnualChart();
    //         //createMonthlyChart();
    //         createMembDailyChart(); // 일일 회원 수 차트
    //         createLoginDailyChart(); // 일일 로그인 수 차트
    //         createVisitDailyChart(); // 일일 방문자 수 차트
    //
    //         //회원수 세팅
    //         $('#currentMembCnt').text(currentMemberData[0].cnt);
    //     },
    //     error: function(error) { // 에러 시 실행
    //         console.error('Error:', error);
    //         // 실패 여부를 H1 태그에 표현하기
    //         setApiFailureIcon();
    //     }
    // });
}

function createCertDailyChart() {
    {
        const el = document.getElementById('certCntDailyChart');
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
                    name: '확인서 신청 건수',
                    data: [-3.5, -1.1, 4.0, 11.3, 17.5, 21.5, 25.9, 27.2, 24.4, 13.9, 6.6, -0.6],
                },
                {
                    name: '확인서 발급 건수',
                    data: [3.8, 5.6, 7.0, 9.1, 12.4, 15.3, 17.5, 17.8, 15.0, 10.6, 6.6, 3.7],
                },
                {
                    name: '확인서 반려 건수',
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
            chart: { title: '확인서 건수 Chart', width: 'auto', height: 550 },
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

function setCertCnt(obj) {
    // if(!obj) {
    //     console.error('창업기업확인 DATA IS NULL');
    //     return;
    // }

    // 통계 카드 애니메이션
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

    /* 확인서 신청 건수 */
    gsap.to("#applyCnt", {
        innerText: 3512,
        duration: 3,
        snap: "innerText",
        onUpdate: function () {
            document.querySelector("#applyCnt").innerText =
                Math.floor(this.targets()[0].innerText).toLocaleString();
        }
    });

    /* 확인서 발급 건수 */
    gsap.to("#issueCnt", {
        innerText: 24621, // 실제 요소의 값에서 '24621' 값이 증가되는 것을 보여주기 때문에 요소의 값은 0이어야 한다
        duration: 3,
        snap: "innerText",
        onUpdate: function () {
            document.querySelector("#issueCnt").innerText =
                Math.floor(this.targets()[0].innerText).toLocaleString();
        }
    });

    /* 확인서 반려 건수 */
    gsap.to("#rejectCnt", {
        innerText: 24621, // 실제 요소의 값에서 '24621' 값이 증가되는 것을 보여주기 때문에 요소의 값은 0이어야 한다
        duration: 3,
        snap: "innerText",
        onUpdate: function () {
            document.querySelector("#rejectCnt").innerText =
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

    $('#applyYmd').text('(' + year + '-' + month + '-' + day + ' 기준)');
    $('#issueYmd').text('(' + year + '-' + month + '-' + day + ' 기준)');
    $('#rejectYmd').text('(' + year + '-' + month + '-' + day + ' 기준)');
}