let annualData;
let monthlyData;
let weeklyData;

let annualChart;
let monthlyChart;
let weeklyChart;

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
});

function chartInit() {
    //최초 조회 시 현재 년도 가져오기
    let year = new Date().getFullYear();
    let month = new Date().getMonth() + 1;

    $.ajax({
        url: '/kisedorkr/getAnnualData?year='+ year + '&month=' + month,
        type: 'GET',
        dataType: 'json',
        success: function(data) {
            /**
             * 1. 년도별, 월별, 주별 데이터 세팅
             * 2. API 호출 상태 ICON 세팅
             * 3. 차트 생성 function 실행
             */
            annualData = data[0];
            monthlyData = data[1];
            weeklyData = data[2];

            setApiSuccessIcon();

            //데이터가 존재하지 않을 시 API 호출 상태 ICON 업데이트
            if(!validateData()) {
                setApiFailureIcon();
            }

            // 성공 여부를 H1 태그에 표현하기
            createAnnualChart();
        },
        error: function(error) { // 에러 시 실행
            console.error('Error:', error);
            // 실패 여부를 H1 태그에 표현하기
            setApiFailureIcon();
        }
    });
}

function createAnnualChart() {
    //annual 차트
    {
        const el = document.getElementById('annualChart');
        const data = {
            categories: [
                '1월',
                '2월',
                '3월',
                '4월',
                '5월',
                '6월',
                '7월',
                '8월',
                '9월',
                '10월',
                '11월',
                '12월',
            ],
            series: [
                {
                    name: '2021',
                    data: [-3.5, -1.1, 4.0, 11.3, 17.5, 21.5, 25.9, 27.2, 24.4, 13.9, 6.6, -0.6],
                },
                {
                    name: '2022',
                    data: [3.8, 5.6, 7.0, 9.1, 12.4, 15.3, 17.5, 17.8, 15.0, 10.6, 6.6, 3.7],
                },
                {
                    name: '2023',
                    data: [22.1, 22.0, 20.9, 18.3, 15.2, 12.8, 11.8, 13.0, 15.2, 17.6, 19.4, 21.2],
                },
                {
                    name: '2024',
                    data: [-10.3, -9.1, -4.1, 4.4, 12.2, 16.3, 18.5, 16.7, 10.9, 4.2, -2.0, -7.5],
                },
                {
                    name: '2025',
                    data: [-13.2, -13.7, -13.1, -10.3],
                },
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
            chart: { title: '연도별 방문자수', width: 'auto', height: 550 },
            xAxis: {
                title: 'Month',
            },
            yAxis: {
                title: 'Count',
            },
            tooltip: {
                formatter: (value) => `${value}°C`,
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

        const chart = toastui.Chart.areaChart({ el, data, options });
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
    if(!annualData || annualData.length <= 0) return false;

    return true;
}

function datePickerInit() {
    rangeDatePickerInit();
}