/* TODO 최초에 한번 로딩되고 그 이후엔 js가 계속 남아 있어서 안되는 것 일수도 있음. 확인해보기
 *  DOMContentLoaded가 실행되지 않고 있음
*/
document.addEventListener("DOMContentLoaded", function () {
    chartInit();
    gridInit();
});

function chartInit(url) {
    //최초 조회 시 현재 년도 가져오기
    let year = new Date().getFullYear();
    let month = new Date().getMonth() + 1;

    if(!url) url = '/kisedorkr';

    $.ajax({
        url: url + '/getAnnualData?year='+ year + '&month=' + month,
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
            createMonthlyChart();
            //createWeeklyChart();
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
        let data = {
            // page -> 이 값들은 모두 동일할 필요가 있을 것 같음.(여러 시스템의 데이터를 하나의 차트에서 보여준다면)
            categories: [],
            series: [],
        };

        //series.data 세팅
        let annual_data = [];

        // 각 서비스의 API 호출 성공 여부에 영향을 미치게 하지 않기 위함
        if( annualData != null && annualData.length > 0 ) {
            //categories 세팅
            for( var i = 0 ; i < annualData.length ; i++ ) {
                /**
                 * categories(예시로는 페이지 별 사용률에서 '페이지')가 서비스마다 동일하게 가져올 수 있다면
                 * 하나의 차트에 여러 서비스를 보여줄 수 있음
                 */
                data.categories.push(annualData[i].year);
                annual_data.push(annualData[i].usage);
            }

            data.series.push({
                name : 'service_1',
                data : annual_data
            })
        }

        const theme = getTheme();

        const options = {
            chart: {title: '연간 방문자수', width: 'auto', height: 350},
            legend: {visible: false},
            xAxis: {pointOnColumn: false, title: {text: 'year'}},
            yAxis: {title: 'usage'},
            series: { showDot: true, dataLabels: { visible: true, offsetY: -10 }, selectable: true },
            theme
        };

        annualChart = toastui.Chart.areaChart({el, data, options});

        //area 데이터 클릭 이벤트
        annualChart.on('selectSeries', function(e) {
            //하나의 서비스만 차트에 보여지기 때문에 0번째 index를 가져온다.
            let year = e.area[0].data.category;
            annualChartClick(year);
        })
    }
}

function createMonthlyChart() {
    // month 차트
    {
        const el = document.getElementById('monthChart');
        let data = {
            categories: [],
            series: [],
            year: []
        };

        //series.data 세팅
        let monthly_data = [];

        // 각 서비스의 API 호출 성공 여부에 영향을 미치게 하지 않기 위함
        if( monthlyData != null && monthlyData.length > 0 ) {
            //categories 세팅
            for( var i = 0 ; i < monthlyData.length ; i++ ) {
                /**
                 * categories(예시로는 페이지 별 사용률에서 '페이지')가 서비스마다 동일하게 가져올 수 있다면
                 * 하나의 차트에 여러 서비스를 보여줄 수 있음
                 */
                data.categories.push(monthlyData[i].month);
                monthly_data.push(monthlyData[i].usage);
                data.year.push(monthlyData[i].year);
            }

            data.series.push({
                name : 'service_1',
                data : monthly_data
            })
        }

        const theme = getTheme();

        let title = data.year[0] + '년 월간 방문자수';
        const options = {
            chart: { title: title, width:'auto', height: 300 },
            legend: {visible: false},
            series: {
                dataLabels: {
                    visible: true,
                },
                selectable: true
            },
            theme
        };

        monthlyChart = toastui.Chart.columnChart({ el, data, options });

        // TODO : 주별 현황 대신 일별 현황이 보여진다면 월별 차트의 select 이벤트는 사라져야 할 것으로 보임
        monthlyChart.on('selectSeries', function(e) {
            //하나의 서비스만 차트에 보여지기 때문에 0번째 index를 가져온다.
            let month = e.column[0].data.category;
            let year = data.year[0];

            monthlyChartClick(year, month);
        })
    }
}

function createWeeklyChart() {
    // week 차트
    {
        const el = document.getElementById('weekChart');
        let data = {
            categories: [],
            series: [],
            year: [],
            month: []
        };

        //series.data 세팅
        let weekly_data = [];

        // 각 서비스의 API 호출 성공 여부에 영향을 미치게 하지 않기 위함
        if( weeklyData != null && weeklyData.length > 0 ) {
            //categories 세팅
            for( var i = 0 ; i < weeklyData.length ; i++ ) {
                /**
                 * categories(예시로는 페이지 별 사용률에서 '페이지')가 서비스마다 동일하게 가져올 수 있다면
                 * 하나의 차트에 여러 서비스를 보여줄 수 있음
                 */
                data.categories.push(weeklyData[i].week);
                weekly_data.push(weeklyData[i].usage);
                data.year.push(weeklyData[i].year);
                data.month.push(weeklyData[i].month);
            }

            data.series.push({
                name : 'service_1',
                data : weekly_data
            })
        }

        const theme = getTheme();

        let title = '일별 현황';
        const options = {
            chart: { title: title, height: 350 },
            legend: {visible: false},
            series: {
                dataLabels: {
                    visible: true,
                },
            },
            theme
        };

        weeklyChart = toastui.Chart.columnChart({ el, data, options });
    }
}



function annualChartClick(year) {
    if(!year) {
        alert("선택한 년도 정보를 가져올 수 없습니다");
        return;
    }

    //현재 월 세팅
    let month = new Date().getMonth() + 1;

    $.ajax({
        url: '/kisedorkr/getMonthlyData?year='+ year + '&month='+month,
        type: 'GET',
        dataType: 'json',
        success: function(data) {
            /**
             * 1. 가져온 데이터 null 체크
             * 2. 차트 초기화
             * 3. 데이터 다시 세팅
             */
            //월별 데이터, 주별 데이터
            if(data == null || data.length !== 2) {
                alert("월별 데이터가 존재하지 않습니다.");
                return;
            }

            monthlyData = data[0];
            weeklyData = data[1];

            setApiSuccessIcon();

            //데이터가 존재하지 않을 시 API 호출 상태 ICON 업데이트
            if(!validateData()) {
                setApiFailureIcon();
            }

            // 차트 destroy
            monthlyChart.destroy();
            //weeklyChart.destroy();

            // 새로운 데이터로 재생성
            createMonthlyChart();
            //createWeeklyChart()
        },
        error: function(error) { // 에러 시 실행
            console.error('Error:', error);
            setApiFailureIcon();
        }
    });
}

function monthlyChartClick(year, month) {
    if(!year) {
        alert("선택한 년도 정보를 가져올 수 없습니다");
        return;
    }

    if(!month) {
        alert("선택한 월의 정보를 가져올 수 없습니다");
        return;
    }

    $.ajax({
        url: '/kisedorkr/getWeeklyData?year='+ year + '&month='+month,
        type: 'GET',
        dataType: 'json',
        success: function(data) {
            /**
             * 1. 가져온 데이터 null 체크
             * 2. 차트 초기화
             * 3. 데이터 다시 세팅
             */
            //월별 데이터, 주별 데이터
            if(data == null || data.length !== 1) {
                alert("주별 데이터가 존재하지 않습니다.");
                return;
            }

            weeklyData = data[0];

            setApiSuccessIcon();

            //데이터가 존재하지 않을 시 API 호출 상태 ICON 업데이트
            if(!validateData()) {
                setApiFailureIcon();
            }

            // 차트 destroy
            //weeklyChart.destroy();

            // 새로운 데이터로 재생성
            //createWeeklyChart()
        },
        error: function(error) { // 에러 시 실행
            console.error('Error:', error);
            setApiFailureIcon();
        }
    });
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

function destroyChart() {
    if(annualChart && monthlyChart && weeklyChart) {
        annualChart.destroy();
        monthlyChart.destroy();
        //weeklyChart.destroy();
    }
}

function validateData() {
    if(!annualData || annualData.length <= 0) return false;
    if(!monthlyData || monthlyData.length <= 0) return false;
    if(!weeklyData || weeklyData.length <= 0) return false;

    return true;
}

function gridInit() {
    $.ajax({
        url: '/kisedorkr/getGridData',
        type: 'GET',
        dataType: 'json',
        success: function(data) {

            setApiSuccessIcon();

            if(!data || data.length <= 0) {
                setApiFailureIcon();
            }

            createGrid(data);
        },
        error: function(error) {
            console.error('Error:', error);
            setApiFailureIcon();
        }
    });
}

function createGrid(gridData) {
    serviceGrid = new tui.Grid({
        el: document.getElementById('grid'),
        data: gridData,
        scrollX: false,
        scrollY: false,
        columns: [
            {
                header: '사업명',
                name: 'bsnsNm'
            },
            {
                header: '기관명',
                name: 'facilityNm'
            },
            {
                header: '사업자등록번호',
                name: 'facilityBsnsNum'
            },
            {
                header: '상태',
                name: 'status'
            },
            {
                header: '탐지결과',
                name: 'detNm'
            }
        ],
        rowHeaders: ['rowNum'],
        pageOptions: {
            useClient: true,
            perPage: 5
        },
        summary: {
            position: 'bottom',
            height: 40,
            columnContent: {
                bsnsNm: {
                    template(summary) {
                        return 'TOTAL : ' + summary.cnt;
                    }
                }
            }
        }
    });

    $('#gridBtn').css('display', 'block');
}

function excelDownload() {
    serviceGrid.export('xls', { onlySelected: true });
}

function destroyGrid() {
    if(serviceGrid && serviceGrid.el) {
        serviceGrid.destroy();
        $('#gridBtn').css('display', 'none');
    }
}