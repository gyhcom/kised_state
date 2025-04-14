let annualLoginChart;
let monthlyLoginChart;

let incorpChart;
let visitChart;

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
    //최초 조회 시 현재 년도 가져오기
    if(!year || year === '') year = new Date().getFullYear();
    if(!month || month === '') month = new Date().getMonth() + 1;

    $.ajax({
        url: '/kstup/bizStatsInfoApi',
        type: 'GET',
        dataType: 'json',
        success: function(data) {
            console.dir(data);

            setApiSuccessIcon();

            //데이터가 존재하지 않을 시 API 호출 상태 ICON 업데이트
            if(!data) {
                setApiFailureIcon();
            }

            createVisitDailyChart(); // 일일 방문자 수 차트
        },
        error: function(error) { // 에러 시 실행
            console.error('Error:', error);
            // 실패 여부를 H1 태그에 표현하기
            setApiFailureIcon();
        }
    });
}

function createVisitDailyChart() {
    {
        const el = document.getElementById('visitCntDailyChart');
        let data = {
            categories: [],
            series: [],
        };

        const theme = getTheme();

        const options = {
            chart: {title: '일일 방문자 수', width: 'auto', height: 350},
            legend: {visible: false},
            xAxis: {pointOnColumn: false, title: {text: 'day'}},
            yAxis: {title: 'count'},
            tooltip: {
                formatter: (value) => `${value}명`,
            },
            series: { showDot: true, dataLabels: { visible: true, offsetY: -10 }, selectable: true },
            theme
        };

        toastui.Chart.columnChart({el, data, options});
    }
}

function createLoginDailyChart() {
    {
        const el = document.getElementById('visitCntDailyChart');
        let data = {
            categories: ['2025-04-07'],
            series: [
                {
                    name: '법인 설립 건수',
                    data: [20]
                }
            ],
        };

        const theme = getTheme();

        const options = {
            chart: {title: '법인 설립 건수', width: 'auto', height: 350},
            legend: {visible: false},
            xAxis: {pointOnColumn: false, title: {text: 'day'}},
            yAxis: {title: 'count'},
            tooltip: {
                formatter: (value) => `${value}명`,
            },
            series: { showDot: true, dataLabels: { visible: true, offsetY: -10 }, selectable: true },
            theme
        };

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

function setStatbizCnt(obj) {
    if(!obj) {
        console.error('법인설립시스템 데이터가 비어있습니다.');
        return;
    }

    /* 회원수 */
    gsap.to("#visitCnt", {
        innerText: 14621,
        duration: 3,
        snap: "innerText",
        onUpdate: function () {
            document.querySelector("#visitCnt").innerText =
                Math.floor(this.targets()[0].innerText).toLocaleString();
        }
    });

    /* 로그인 수 */
    gsap.to("#incorpCnt", {
        innerText: 7856,
        duration: 3,
        snap: "innerText",
        onUpdate: function () {
            document.querySelector("#incorpCnt").innerText =
                Math.floor(this.targets()[0].innerText).toLocaleString();
        }
    });

    /* 로그인(중복제거) 수 */
    gsap.to("#fIncorpCnt", {
        innerText: 4211,
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

    $('#visitYmd').text('(' + year + '-' + month + '-' + day + ' 기준)');
    $('#incorpYmd').text('(' + year + '-' + month + '-' + day + ' 기준)');
    $('#fIncorpYmd').text('(' + year + '-' + month + '-' + day + ' 기준)');
}