let visitCntListData;

document.addEventListener("DOMContentLoaded", function () {
    datePickerInit();
    chartInit();

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

    // 차트 카드 애니메이션
    gsap.from("#contentDiv", {
        duration: 1,
        scale: 0.9,
        opacity: 0,
        delay: 1.5,
        ease: "power2.out"
    });
});

function chartInit() {
    //최초 조회 시 현재 년도 가져오기
    let year = new Date().getFullYear();
    let month = new Date().getMonth() + 1;

    $.ajax({
        url: '/kisedorkr/visitCnt',
        type: 'GET',
        dataType: 'json',
        success: function(data) {
            setApiSuccessIcon();

            //데이터가 존재하지 않을 시 API 호출 상태 ICON 업데이트
            // if(!data) {
            //     setApiFailureIcon();
            // }

            visitCntListData = data.visitCntList;

            setKisedorkrCnt(data);
            createDailyVisitCntChart();
        },
        error: function(error) { // 에러 시 실행
            console.error('Error:', error);
            // 실패 여부를 H1 태그에 표현하기
            setApiFailureIcon();
        }
    });
}

function createDailyVisitCntChart() {
    {
        const el = document.getElementById('dailyVisitCntChart');
        const data = {
            categories: [],
            series: [
                {
                    name: '방문자 수',
                    data: [],
                }
            ],
        };

        for( var i = 0 ; i < visitCntListData.length ; i++ ) {
            data.categories.push(visitCntListData[i].baseDt);
            data.series[0].data.push(Number(visitCntListData[i].vstCnt))
        }

        const theme = getTheme();

        const options = {
            chart: { title: '일일 방문자수', width: 'auto', height: 550 },
            xAxis: {
                title: 'day',
            },
            yAxis: {
                title: 'Count',
            },
            tooltip: {
                formatter: (value) => `${value}명`,
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

        toastui.Chart.columnChart({ el, data, options });
    }
}

function getTheme() {
    return {
        series: {
            dataLabels: {
                fontFamily: 'monaco',
                fontSize: 12,
                fontWeight: 400,
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
}

function validateData() {
    if(!annualData || annualData.length <= 0) return false;

    return true;
}

function datePickerInit() {
    rangeDatePickerInit()
}

function setKisedorkrCnt(obj) {
    if(!obj) {
        console.error('기관 홈페이지 데이터가 비어있습니다.');
        return;
    }

    let sumVisitCnt = 0;
    for( var i = 0 ; i < obj.visitCntList.length ; i ++ ) {
        sumVisitCnt += Number(obj.visitCntList[i].vstCnt);
    }

    /* 일일 방문자 수 */
    gsap.to("#dauValue", {
        innerText: obj.visitCnt.vstCnt,
        duration: 3,
        snap: "innerText",
        onUpdate: function () {
            document.querySelector("#dauValue").innerText =
                Math.floor(this.targets()[0].innerText).toLocaleString();
        }
    });

    /* 최근 30일 방문자 수 */
    gsap.to("#mauValue", {
        innerText: sumVisitCnt,
        duration: 3,
        snap: "innerText",
        onUpdate: function () {
            document.querySelector("#mauValue").innerText =
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

    $('#dailyVisitCnt').text('(' + obj.visitCnt.baseDt + ' 기준)');
}