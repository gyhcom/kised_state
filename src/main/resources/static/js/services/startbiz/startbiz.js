let dailyCntListData;

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
})

function init() {
    $.ajax({
        url: '/startbiz/bizStatsInfoApi',
        type: 'GET',
        dataType: 'json',
        success: function(data) {
            setApiSuccessIcon();

            //데이터가 존재하지 않을 시 API 호출 상태 ICON 업데이트
            if(!data) {
                setApiFailureIcon();
            }

            dailyCntListData = data.dailyCntList;

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
    createVisitDailyChart(); // 일일 방문자 수 차트
    createCorpFndnDailyChart(); // 일일 법인설립 건수 차트
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

        for( var i = 0 ; i < dailyCntListData.length ; i++) {
            data.categories.push(dailyCntListData[i].baseDt2);
            data.series[0].data.push(Number(dailyCntListData[i].vstCnt));
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
            series: { showDot: true, dataLabels: { visible: true, offsetY: -10 }, selectable: true },
            theme
        };

        //차트 색상 변경
        options.theme.series.colors = ['#02de61'];

        toastui.Chart.columnChart({el, data, options});
    }
}

function createCorpFndnDailyChart() {
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

        for( var i = 0 ; i < dailyCntListData.length ; i++) {
            data.categories.push(dailyCntListData[i].baseDt2);
            data.series[0].data.push(Number(dailyCntListData[i].corpFndnCnt));
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
            series: { showDot: true, dataLabels: { visible: true, offsetY: -10 }, selectable: true },
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

    /* 방문자 수 */
    gsap.to("#visitCnt", {
        innerText: obj.dailyCnt.vstCnt,
        duration: 3,
        snap: "innerText",
        onUpdate: function () {
            document.querySelector("#visitCnt").innerText =
                Math.floor(this.targets()[0].innerText).toLocaleString();
        }
    });

    /* 법인 설립 건수 */
    gsap.to("#incorpCnt", {
        innerText: obj.dailyCnt.corpFndnCnt,
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

    $('#visitYmd').text('(' + obj.dailyCnt.baseDt2 + ' 기준)');
    $('#incorpYmd').text('(' + obj.dailyCnt.baseDt2 + ' 기준)');
    //$('#fIncorpYmd').text('(' + obj.dailyCnt.baseDt + ' 기준)');
}