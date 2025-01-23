let annualData;
let monthlyData;
let weeklyData;

let annualChart;
let monthlyChart;
let weeklyChart1;
let weeklyChart2;
let weeklyChart3;
let weeklyChart4;

let datepicker;

document.addEventListener("DOMContentLoaded", function () {
    chartInit();
    datePickerInit();
})

function chartInit(year, month) {
    //최초 조회 시 현재 년도 가져오기
    if(!year || year === '') year = new Date().getFullYear();
    if(!month || month === '') month = new Date().getMonth() + 1;

    $.ajax({
        url: '/kstup/getPopKeyword?year='+year+'&month='+month,
        type: 'GET',
        dataType: 'json',
        success: function(data) {
            annualData = data[0];
            monthlyData = data[1];
            weeklyData = data[2];

            setApiSuccessIcon();

            //데이터가 존재하지 않을 시 API 호출 상태 ICON 업데이트
            if(!validateData()) {
                setApiFailureIcon();
            }

            createAnnualChart();
            createMonthlyChart();
            createWeeklyChart();
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
        const el = document.getElementById('popKeywordAnnualChart');
        let data = {
            // page -> 이 값들은 모두 동일할 필요가 있을 것 같음.(여러 시스템의 데이터를 하나의 차트에서 보여준다면)
            categories: [],
            series: [],
        };

        //백분율 계산
        let searchCntSum = 0;

        // 각 서비스의 API 호출 성공 여부에 영향을 미치게 하지 않기 위함
        if( annualData != null && annualData.length > 0 ) {
            //백분율 계산
            for( var i = 0 ; i < annualData.length ; i++ ) {
                searchCntSum += annualData[i].searchCnt*1;
            }

            for( var i = 0 ; i < annualData.length ; i++) {
                let obj = {};
                obj.name = annualData[i].keyword;
                obj.data = (annualData[i].searchCnt / searchCntSum) * 100;
                data.series.push(obj);
            }

            data.categories.push(annualData[0].year);
        }

        const theme = getTheme();

        const options = {
            chart: {title: annualData[0].year + '년도 연간 인기 검색 키워드 Top10', width: 'auto', height: 500},
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

        annualChart = toastui.Chart.pieChart({el, data, options});
    }
}

function createMonthlyChart() {
    // month 차트
    {
        const el = document.getElementById('popKeywordMonthChart');
        let data = {
            categories: [],
            series: []
        };

        //백분율 계산
        let searchCntSum = 0;

        // 각 서비스의 API 호출 성공 여부에 영향을 미치게 하지 않기 위함
        if( monthlyData != null && monthlyData.length > 0 ) {
            //백분율 계산
            for( var i = 0 ; i < monthlyData.length ; i++ ) {
                searchCntSum += monthlyData[i].searchCnt*1;
            }

            for( var i = 0 ; i < monthlyData.length ; i++) {
                let obj = {};
                obj.name = monthlyData[i].keyword;
                obj.data = (monthlyData[i].searchCnt / searchCntSum) * 100;
                data.series.push(obj);
            }

            data.categories.push(monthlyData[0].month);
        }

        const theme = getTheme();

        const options = {
            chart: {title: monthlyData[0].year + '년도 ' + monthlyData[0].month + '월 인기 검색 키워드 Top10', width:'auto', height: 500},
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

        monthlyChart = toastui.Chart.pieChart({ el, data, options });
    }
}

function createWeeklyChart() {
    // week 차트
    {
        // 임시 test용
        const el = document.getElementById('popKeywordWeekChart1');
        let data = {
            categories: [],
            series: [],
        };

        //백분율 계산
        let searchCntSum = 0;

        // 각 서비스의 API 호출 성공 여부에 영향을 미치게 하지 않기 위함
        if( weeklyData != null && weeklyData.length > 0 ) {
            for( var i = 0 ; i < weeklyData.length ; i++) {
                if( weeklyData[i].week == 1 ) {
                    let obj = {};
                    obj.name = weeklyData[i].keyword;
                    obj.data = [weeklyData[i].searchCnt];
                    data.series.push(obj);
                }
            }

            data.categories.push(weeklyData[0].week);
        }

        const theme = getTheme();

        const options = {
            chart: {title: weeklyData[0].week + '주차 인기 검색 키워드 Top10', width: 'auto', height: 350},
            legend: {
                visible: false
            }
        };

        weeklyChart1 = toastui.Chart.columnChart({ el, data, options });
    }

    // 2 week 차트
    {
        // 임시 test용
        const el = document.getElementById('popKeywordWeekChart2');
        let data = {
            categories: [],
            series: [],
        };

        //백분율 계산
        let searchCntSum = 0;

        // 각 서비스의 API 호출 성공 여부에 영향을 미치게 하지 않기 위함
        if( weeklyData != null && weeklyData.length > 0 ) {
            for( var i = 0 ; i < weeklyData.length ; i++) {
                if( weeklyData[i].week == 2 ) {
                    let obj = {};
                    obj.name = weeklyData[i].keyword;
                    obj.data = [weeklyData[i].searchCnt];
                    data.series.push(obj);
                }
            }

            data.categories.push('2');
        }

        const theme = getTheme();

        const options = {
            chart: {title: '2 주차 인기 검색 키워드 Top10', width: 'auto', height: 350},
            legend: {
                visible: false
            }
        };

        weeklyChart2 = toastui.Chart.columnChart({ el, data, options });
    }

    // 3 week 차트
    {
        // 임시 test용
        const el = document.getElementById('popKeywordWeekChart3');
        let data = {
            categories: [],
            series: [],
        };

        //백분율 계산
        let searchCntSum = 0;

        // 각 서비스의 API 호출 성공 여부에 영향을 미치게 하지 않기 위함
        if( weeklyData != null && weeklyData.length > 0 ) {
            for( var i = 0 ; i < weeklyData.length ; i++) {
                if( weeklyData[i].week == 3 ) {
                    let obj = {};
                    obj.name = weeklyData[i].keyword;
                    obj.data = [weeklyData[i].searchCnt];
                    data.series.push(obj);
                }
            }

            data.categories.push('3');
        }

        const theme = getTheme();

        const options = {
            chart: {title: '3 주차 인기 검색 키워드 Top10', width: 'auto', height: 350},
            legend: {
                visible: false
            }
        };

        weeklyChart3 = toastui.Chart.columnChart({ el, data, options });
    }

    // 4 week 차트
    {
        // 임시 test용
        const el = document.getElementById('popKeywordWeekChart4');
        let data = {
            categories: [],
            series: [],
        };

        //백분율 계산
        let searchCntSum = 0;

        // 각 서비스의 API 호출 성공 여부에 영향을 미치게 하지 않기 위함
        if( weeklyData != null && weeklyData.length > 0 ) {
            for( var i = 0 ; i < weeklyData.length ; i++) {
                if( weeklyData[i].week == 4 ) {
                    let obj = {};
                    obj.name = weeklyData[i].keyword;
                    obj.data = [weeklyData[i].searchCnt];
                    data.series.push(obj);
                }
            }

            data.categories.push('4');
        }

        const theme = getTheme();

        const options = {
            chart: {title: '4 주차 인기 검색 키워드 Top10', width: 'auto', height: 350},
            legend: {
                visible: false
            }
        };

        weeklyChart4 = toastui.Chart.columnChart({ el, data, options });
    }
}

function getTheme() {
    return {
        series: {
            dataLabels: {
                fontFamily: 'monaco',
                fontSize: 12,
                pieSeriesName: {
                    fontSize: 12,
                    fontWeight: 'bold'
                }
            },
        },
    }
}

function validateData() {
    if(!annualData || annualData.length <= 0) return false;
    if(!monthlyData || monthlyData.length <= 0) return false;
    if(!weeklyData || weeklyData.length <= 0) return false;

    return true;
}

function datePickerInit() {
   datepicker = new tui.DatePicker('#wrapper', {
        language: 'en',
        date: new Date(),
        input: {
            element: '#datepicker-input',
            format: 'yyyy-MM'
        },
        type: 'month',
    });
}

function searchPopKeyword() {
    //차트 삭제 후 재생성
    annualChart.destroy();
    monthlyChart.destroy();
    weeklyChart1.destroy();
    weeklyChart2.destroy();
    weeklyChart3.destroy();
    weeklyChart4.destroy();
    
    chartInit(datepicker.getDate().getFullYear(), datepicker.getDate().getMonth()+1);
}