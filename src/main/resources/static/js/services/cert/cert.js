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
})

function init(year, month) {
    //최초 조회 시 현재 년도 가져오기
    if(!year || year === '') year = new Date().getFullYear();
    if(!month || month === '') month = new Date().getMonth() + 1;

    $.ajax({
        url: '/kstup/getMembAndLoginCnt?year='+year+'&month='+month,
        type: 'GET',
        dataType: 'json',
        success: function(data) {
            /**
             * [0] : 현재 회원 수
             * [1] : 연도별 회원 수
             * [2] : 월별 회원 수
             * [3] : 연도별 로그인 수
             * [4] : 월별 로그인 수
             * [5] : 일일 로그인 수
             */
            currentMemberData = data[0];
            annualMemberData = data[1];
            monthlyMemberData = data[2];

            annualLoginData = data[3];
            monthlyLoginData = data[4];
            dailyLoginData = data[5];

            setApiSuccessIcon();

            //데이터가 존재하지 않을 시 API 호출 상태 ICON 업데이트
            if(!validateData()) {
                setApiFailureIcon();
            }

            //createAnnualChart();
            //createMonthlyChart();
            createMembDailyChart(); // 일일 회원 수 차트
            createLoginDailyChart(); // 일일 로그인 수 차트
            createVisitDailyChart(); // 일일 방문자 수 차트

            //회원수 세팅
            $('#currentMembCnt').text(currentMemberData[0].cnt);
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
        const el = document.getElementById('loginCntAnnualChart');
        let data = {
            // page -> 이 값들은 모두 동일할 필요가 있을 것 같음.(여러 시스템의 데이터를 하나의 차트에서 보여준다면)
            categories: [],
            series: [],
        };

        let annual_data = [];

        // 각 서비스의 API 호출 성공 여부에 영향을 미치게 하지 않기 위함
        if( annualLoginData != null && annualLoginData.length > 0 ) {
            //categories 세팅
            for( var i = 0 ; i < annualLoginData.length ; i++ ) {
                /**
                 * categories(예시로는 페이지 별 사용률에서 '페이지')가 서비스마다 동일하게 가져올 수 있다면
                 * 하나의 차트에 여러 서비스를 보여줄 수 있음
                 */
                data.categories.push(annualLoginData[i].year);
                //임시용이기 때문에 만들어진 함수 재사용함. usage -> cnt로 바뀌어야 하는게 맞음
                annual_data.push(annualLoginData[i].usage);
            }

            data.series.push({
                name : 'Count',
                data : annual_data
            })
        }

        const theme = getTheme();

        const options = {
            chart: {title: '연도별 로그인 수', width: 'auto', height: 350},
            legend: {visible: false},
            xAxis: {pointOnColumn: false, title: {text: 'year'}},
            yAxis: {title: 'count'},
            series: { showDot: true, dataLabels: { visible: true, offsetY: -10 }, selectable: true },
            theme
        };

        annualLoginChart = toastui.Chart.columnChart({el, data, options});

        annualLoginChart.on('selectSeries', function(e) {
            let year = e.column[0].data.category;
            annualLoginChartClick(year);
        })
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
            chart: {title: dailyLoginData[0].year + '년 ' + dailyLoginData[0].month + '월 일일 건수 -> line Chart로 신청, 발급, 반려 차트가 하나로 표현될 예정', width: 'auto', height: 350},
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

function annualLoginChartClick(year, month) {
    if(!year) {
        alert("선택한 년도 정보를 가져올 수 없습니다");
        return;
    }

    // month가 빈 값이면 현재 월 세팅
    if(!month || month == '') month = new Date().getMonth() + 1;

    $.ajax({
        url: '/kstup/getMonthlyMembAndLoginCnt?year='+year+"&month="+month,
        type: 'GET',
        dataType: 'json',
        success: function(data) {
            //월별 데이터, 주별 데이터
            if(data == null || data.length !== 2) {
                alert("월별 데이터가 존재하지 않습니다.");
                return;
            }

            monthlyLoginData = data[0];
            dailyLoginData = data[1];

            setApiSuccessIcon();

            //데이터가 존재하지 않을 시 API 호출 상태 ICON 업데이트
            if(!validateData()) {
                setApiFailureIcon();
            }

            // 차트 destroy
            monthlyLoginChart.destroy();
            dailyLoginChart.destroy();

            // 새로운 데이터로 재생성
            createMonthlyChart();
            createDailyChart();
        },
        error: function(error) { // 에러 시 실행
            console.error('Error:', error);
            setApiFailureIcon();
        }
    });
}

function monthlyLoginChartClick(year, month) {
    if(!year) {
        alert("선택한 년도 정보를 가져올 수 없습니다");
        return;
    }

    if(!month) {
        alert("선택한 월의 정보를 가져올 수 없습니다");
        return;
    }

    $.ajax({
        url: '/kstup/getDailyMembAndLoginCnt?year='+ year + '&month='+month,
        type: 'GET',
        dataType: 'json',
        success: function(data) {
            if(data == null || data.length <= 0) {
                alert("일별 데이터가 존재하지 않습니다.");
                return;
            }

            dailyLoginData = data;

            setApiSuccessIcon();

            //데이터가 존재하지 않을 시 API 호출 상태 ICON 업데이트
            if(!validateData()) {
                setApiFailureIcon();
            }

            // 차트 destroy
            dailyLoginChart.destroy();

            // 새로운 데이터로 재생성
            createDailyChart()
        },
        error: function(error) { // 에러 시 실행
            console.error('Error:', error);
            setApiFailureIcon();
        }
    });
}

//현재 회원 수를 제외한 [연도별, 월별] 회원 수 조회
function getMemberCount(year, month) {
    if(!year) {
        alert("선택한 년도 정보를 가져올 수 없습니다");
        return;
    }

    if(!month) {
        alert("선택한 월의 정보를 가져올 수 없습니다");
        return;
    }

    $.ajax({
        url: '/kstup/getMembCnt?year='+ year + '&month='+month,
        type: 'GET',
        dataType: 'json',
        success: function(data) {
            //console.dir(data);
            if(data == null || data.length <= 0) {
                alert("회원 수 정보를 가져올 수 없습니다.");
                return;
            }

            // annualMemberData = data[0];
            // monthlyMemberData = data[1];

            setApiSuccessIcon();

            //데이터가 존재하지 않을 시 API 호출 상태 ICON 업데이트
            if(!validateData()) {
                setApiFailureIcon();
            }
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

function searchMembAndLoginCnt() {
    let year = datepicker.getDate().getFullYear();
    let month = datepicker.getDate().getMonth()+1;

    //차트 세팅
    annualLoginChartClick(year, month);

    //회원수 세팅
    getMemberCount(year, month);
}