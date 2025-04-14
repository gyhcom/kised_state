let infoPubNotiAnnChart;
let infoPubNotiMonChart;
let rveExptrAnnChart;
let rveExptrMonChart;
let fnlsttAnnChart;
let fnlsttMonChart;
let excutAnnChart;
let excutMonChart;
let dtlBsnsInfoAnnChart;
let dtlBsnsInfoMonChart;

let infoPubNotiAnnData;
let infoPubNotiMonData;
let rveExptrAnnData;
let rveExptrMonData;
let fnlsttAnnData;
let fnlsttMonData;
let excutAnnData;
let excutMonData;
let dtlBsnsInfoAnnData;
let dtlBsnsInfoMonData;

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

    $.ajax({
        url: '/gslsEsb/getAllGslsEsbCnt?year='+year,
        type: 'GET',
        dataType: 'json',
        success: function(data) {
            /**
             * [0] : 연도별 정보공시내역 건수
             * [1] : 월별 정보공시내역 건수
             * [2] : 연도별 세입세출내역 건수
             * [3] : 월별 세입세출내역 건수
             * [4] : 연도별 재무제표결산 내역 건수
             * [5] : 월별 재무제표결산 내역 건수
             * [6] : 연도별 수급자집행정보 건수
             * [7] : 월별 수급자집행정보 건수
             * [8] : 연도별 상세내역사업정보 건수
             * [9] : 월별 상세내역사업정보 건수
             */
            infoPubNotiAnnData = data[0];
            infoPubNotiMonData = data[1];
            rveExptrAnnData = data[2];
            rveExptrMonData = data[3];
            fnlsttAnnData = data[4];
            fnlsttMonData = data[5];
            excutAnnData = data[6];
            excutMonData = data[7];
            dtlBsnsInfoAnnData = data[8];
            dtlBsnsInfoMonData = data[9];

            setApiSuccessIcon();

            //데이터가 존재하지 않을 시 API 호출 상태 ICON 업데이트
            if(!validateData()) {
                setApiFailureIcon();
            }

            createInfoPubNotiAnnChart();
            createInfoPubNotiMonChart();

            createRveExptrAnnChart();
            createRveExptrMonChart();

            createFnlsttAnnChart();
            createFnlsttMonChart();

            createExcutAnnChart();
            createExcutMonChart();

            createDtlBsnsInfoAnnChart();
            createDtlBsnsInfoMonChart();
        },
        error: function(error) {
            console.error('Error:', error);
            setApiFailureIcon();
        }
    });
}

// 연도별 정보공시내역 건수
function createInfoPubNotiAnnChart() {
    const el = document.getElementById('infoPubNotiAnnChart');
    let data = {
        categories: [],
        series: [],
    };

    let annual_data = [];

    if( infoPubNotiAnnData != null && infoPubNotiAnnData.length > 0 ) {
        //categories 세팅
        for( var i = 0 ; i < infoPubNotiAnnData.length ; i++ ) {
            data.categories.push(infoPubNotiAnnData[i].year);
            annual_data.push(infoPubNotiAnnData[i].cnt);
        }

        data.series.push({
            name : 'Count',
            data : annual_data
        })
    }

    const theme = getTheme();

    const options = {
        chart: {title: '연도별 정보공시내역 건수', width: 'auto', height: 350},
        legend: {visible: false},
        xAxis: {pointOnColumn: false, title: {text: 'year'}},
        yAxis: {title: 'count'},
        series: { showDot: true, dataLabels: { visible: true, offsetY: -5 }, selectable: true },
        theme
    };

    infoPubNotiAnnChart = toastui.Chart.columnChart({el, data, options});

    infoPubNotiAnnChart.on('selectSeries', function(e) {
        let year = e.column[0].data.category;
        clickInfoPubNotiAnnChart(year);
    })
}

// 월별 정보공시내역 건수
function createInfoPubNotiMonChart() {
    const el = document.getElementById('infoPubNotiMonChart');
    let data = {
        categories: [],
        series: [],
    };

    let monthly_data = [];

    if( infoPubNotiMonData != null && infoPubNotiMonData.length > 0 ) {
        //categories 세팅
        for( var i = 0 ; i < infoPubNotiMonData.length ; i++ ) {
            data.categories.push(infoPubNotiMonData[i].month);
            monthly_data.push(infoPubNotiMonData[i].cnt);
        }

        data.series.push({
            name : 'Count',
            data : monthly_data
        })
    }

    const theme = getTheme();

    const options = {
        chart: {title: infoPubNotiMonData[0].year+'년 월별 정보공시내역 건수', width: 'auto', height: 350},
        legend: {visible: false},
        xAxis: {pointOnColumn: false, title: {text: 'month'}},
        yAxis: {title: 'count'},
        series: {
            showDot: true, dataLabels: { visible: true, offsetY: -5 }, selectable: true
        },
        theme
    };
    //차트 색상 변경
    options.theme.series.colors = ['#49c9ed'];

    infoPubNotiMonChart = toastui.Chart.columnChart({ el, data, options });
}

// 연도별 세입세출내역 건수
function createRveExptrAnnChart() {
    const el = document.getElementById('rveExptrAnnChart');
    let data = {
        categories: [],
        series: [],
    };

    let annual_data = [];

    if( rveExptrAnnData != null && rveExptrAnnData.length > 0 ) {
        //categories 세팅
        for( var i = 0 ; i < rveExptrAnnData.length ; i++ ) {
            data.categories.push(rveExptrAnnData[i].year);
            annual_data.push(rveExptrAnnData[i].cnt);
        }

        data.series.push({
            name : 'Count',
            data : annual_data
        })
    }

    const theme = getTheme();

    const options = {
        chart: {title: '연도별 세입세출내역 건수', width: 'auto', height: 350},
        legend: {visible: false},
        xAxis: {pointOnColumn: false, title: {text: 'year'}},
        yAxis: {title: 'count'},
        series: { showDot: true, dataLabels: { visible: true, offsetY: -5 }, selectable: true },
        theme
    };

    //차트 색상 변경
    options.theme.series.colors = ['#2bb385'];

    rveExptrAnnChart = toastui.Chart.columnChart({el, data, options});

    rveExptrAnnChart.on('selectSeries', function(e) {
        let year = e.column[0].data.category;
        clickRveExptrAnnChart(year)
    })
}

// 월별 세입세출내역 건수
function createRveExptrMonChart() {
    const el = document.getElementById('rveExptrMonChart');
    let data = {
        categories: [],
        series: [],
    };

    let monthly_data = [];

    if( rveExptrMonData != null && rveExptrMonData.length > 0 ) {
        //categories 세팅
        for( var i = 0 ; i < rveExptrMonData.length ; i++ ) {
            data.categories.push(rveExptrMonData[i].month);
            monthly_data.push(rveExptrMonData[i].cnt);
        }

        data.series.push({
            name : 'Count',
            data : monthly_data
        })
    }

    const theme = getTheme();

    const options = {
        chart: {title: rveExptrMonData[0].year+'년 월별 세입세출내역 건수', width: 'auto', height: 350},
        legend: {visible: false},
        xAxis: {pointOnColumn: false, title: {text: 'month'}},
        yAxis: {title: 'count'},
        series: {
            showDot: true, dataLabels: { visible: true, offsetY: -5 }, selectable: true
        },
        theme
    };
    //차트 색상 변경
    options.theme.series.colors = ['#50e6b3'];

    rveExptrMonChart = toastui.Chart.columnChart({ el, data, options });
}

// 연도별 재무제표결산 내역 건수
function createFnlsttAnnChart() {
    const el = document.getElementById('fnlsttAnnChart');
    let data = {
        categories: [],
        series: [],
    };

    let annual_data = [];

    if( fnlsttAnnData != null && fnlsttAnnData.length > 0 ) {
        //categories 세팅
        for( var i = 0 ; i < fnlsttAnnData.length ; i++ ) {
            data.categories.push(fnlsttAnnData[i].year);
            annual_data.push(fnlsttAnnData[i].cnt);
        }

        data.series.push({
            name : 'Count',
            data : annual_data
        })
    }

    const theme = getTheme();

    const options = {
        chart: {title: '연도별 재무제표결산 내역 건수', width: 'auto', height: 350},
        legend: {visible: false},
        xAxis: {pointOnColumn: false, title: {text: 'year'}},
        yAxis: {title: 'count'},
        series: { showDot: true, dataLabels: { visible: true, offsetY: -5 }, selectable: true },
        theme
    };

    //차트 색상 변경
    options.theme.series.colors = ['#d68e29'];

    fnlsttAnnChart = toastui.Chart.columnChart({el, data, options});

    fnlsttAnnChart.on('selectSeries', function(e) {
        let year = e.column[0].data.category;
        clickFnlsttAnnChart(year);
    })
}

//월별 재무제표결산 내역 건수
function createFnlsttMonChart() {
    const el = document.getElementById('fnlsttMonChart');
    let data = {
        categories: [],
        series: [],
    };

    let monthly_data = [];

    if( fnlsttMonData != null && fnlsttMonData.length > 0 ) {
        //categories 세팅
        for( var i = 0 ; i < fnlsttMonData.length ; i++ ) {
            data.categories.push(fnlsttMonData[i].month);
            monthly_data.push(fnlsttMonData[i].cnt);
        }

        data.series.push({
            name : 'Count',
            data : monthly_data
        })
    }

    const theme = getTheme();

    const options = {
        chart: {title: fnlsttMonData[0].year+'년 월별 재무제표결산 내역 건수', width: 'auto', height: 350},
        legend: {visible: false},
        xAxis: {pointOnColumn: false, title: {text: 'month'}},
        yAxis: {title: 'count'},
        series: {
            showDot: true, dataLabels: { visible: true, offsetY: -5 }, selectable: true
        },
        theme
    };
    //차트 색상 변경
    options.theme.series.colors = ['#ebb66c'];

    fnlsttMonChart = toastui.Chart.columnChart({ el, data, options });
}

// 연도별 수급자집행정보 건수
function createExcutAnnChart() {
    const el = document.getElementById('excutAnnChart');
    let data = {
        categories: [],
        series: [],
    };

    let annual_data = [];

    if( excutAnnData != null && excutAnnData.length > 0 ) {
        //categories 세팅
        for( var i = 0 ; i < excutAnnData.length ; i++ ) {
            data.categories.push(excutAnnData[i].year);
            annual_data.push(excutAnnData[i].cnt);
        }

        data.series.push({
            name : 'Count',
            data : annual_data
        })
    }

    const theme = getTheme();

    const options = {
        chart: {title: '연도별 수급자 집행정보 건수', width: 'auto', height: 350},
        legend: {visible: false},
        xAxis: {pointOnColumn: false, title: {text: 'year'}},
        yAxis: {title: 'count'},
        series: { showDot: true, dataLabels: { visible: true, offsetY: -5 }, selectable: true },
        theme
    };

    //차트 색상 변경
    options.theme.series.colors = ['#ed5a55'];

    excutAnnChart = toastui.Chart.columnChart({el, data, options});

    excutAnnChart.on('selectSeries', function(e) {
        let year = e.column[0].data.category;
        clickExcutAnnChart(year);
    })
}

//월별 수급자집행정보 건수
function createExcutMonChart() {
    const el = document.getElementById('excutMonChart');
    let data = {
        categories: [],
        series: [],
    };

    let monthly_data = [];

    if( excutMonData != null && excutMonData.length > 0 ) {
        //categories 세팅
        for( var i = 0 ; i < excutMonData.length ; i++ ) {
            data.categories.push(excutMonData[i].month);
            monthly_data.push(excutMonData[i].cnt);
        }

        data.series.push({
            name : 'Count',
            data : monthly_data
        })
    }

    const theme = getTheme();

    const options = {
        chart: {title: excutMonData[0].year+'년 월별 수급자 집행정보 건수', width: 'auto', height: 350},
        legend: {visible: false},
        xAxis: {pointOnColumn: false, title: {text: 'month'}},
        yAxis: {title: 'count'},
        series: {
            showDot: true, dataLabels: { visible: true, offsetY: -5 }, selectable: true
        },
        theme
    };
    //차트 색상 변경
    options.theme.series.colors = ['#f29794'];

    excutMonChart = toastui.Chart.columnChart({ el, data, options });
}

// 연도별 상세내역 사업정보 건수
function createDtlBsnsInfoAnnChart() {
    const el = document.getElementById('dtlBsnsInfoAnnChart');
    let data = {
        categories: [],
        series: [],
    };

    let annual_data = [];

    if( dtlBsnsInfoAnnData != null && dtlBsnsInfoAnnData.length > 0 ) {
        //categories 세팅
        for( var i = 0 ; i < dtlBsnsInfoAnnData.length ; i++ ) {
            data.categories.push(dtlBsnsInfoAnnData[i].year);
            annual_data.push(dtlBsnsInfoAnnData[i].cnt);
        }

        data.series.push({
            name : 'Count',
            data : annual_data
        })
    }

    const theme = getTheme();

    const options = {
        chart: {title: '연도별 상세내역사업정보 건수', width: 'auto', height: 350},
        legend: {visible: false},
        xAxis: {pointOnColumn: false, title: {text: 'year'}},
        yAxis: {title: 'count'},
        series: { showDot: true, dataLabels: { visible: true, offsetY: -5 }, selectable: true },
        theme
    };

    //차트 색상 변경
    options.theme.series.colors = ['#de3bed'];

    dtlBsnsInfoAnnChart = toastui.Chart.columnChart({el, data, options});

    dtlBsnsInfoAnnChart.on('selectSeries', function(e) {
        let year = e.column[0].data.category;
        clickDtlBsnsInfoAnnChart(year);
    })
}

//월별 상세내역 사업정보 건수
function createDtlBsnsInfoMonChart() {
    const el = document.getElementById('dtlBsnsInfoMonChart');
    let data = {
        categories: [],
        series: [],
    };

    let monthly_data = [];

    if( dtlBsnsInfoMonData != null && dtlBsnsInfoMonData.length > 0 ) {
        //categories 세팅
        for( var i = 0 ; i < dtlBsnsInfoMonData.length ; i++ ) {
            data.categories.push(dtlBsnsInfoMonData[i].month);
            monthly_data.push(dtlBsnsInfoMonData[i].cnt);
        }

        data.series.push({
            name : 'Count',
            data : monthly_data
        })
    }

    const theme = getTheme();

    const options = {
        chart: {title: dtlBsnsInfoMonData[0].year+'년 월별 상세내역사업정보 건수', width: 'auto', height: 350},
        legend: {visible: false},
        xAxis: {pointOnColumn: false, title: {text: 'month'}},
        yAxis: {title: 'count'},
        series: {
            showDot: true, dataLabels: { visible: true, offsetY: -5 }, selectable: true
        },
        theme
    };
    //차트 색상 변경
    options.theme.series.colors = ['#e890f0'];

    dtlBsnsInfoMonChart = toastui.Chart.columnChart({ el, data, options });
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
    if(!infoPubNotiAnnData || infoPubNotiAnnData.length <= 0) return false;
    if(!infoPubNotiMonData || infoPubNotiMonData.length <= 0) return false;

    if(!rveExptrAnnData || rveExptrAnnData.length <= 0) return false;
    if(!rveExptrMonData || rveExptrMonData.length <= 0) return false;

    if(!fnlsttAnnData || fnlsttAnnData.length <= 0) return false;
    if(!fnlsttMonData || fnlsttMonData.length <= 0) return false;

    if(!excutAnnData || excutAnnData.length <= 0) return false;
    if(!excutMonData || excutMonData.length <= 0) return false;

    if(!dtlBsnsInfoAnnData || dtlBsnsInfoAnnData.length <= 0) return false;
    if(!dtlBsnsInfoMonData || dtlBsnsInfoMonData.length <= 0) return false;

    return true;
}

function clickInfoPubNotiAnnChart(year) {
    $.ajax({
        url: '/gslsEsb/getInfoPubNotiMonCnt?year='+year,
        type: 'GET',
        dataType: 'json',
        success: function(data) {
            infoPubNotiMonData = data;

            setApiSuccessIcon();

            //데이터가 존재하지 않을 시 API 호출 상태 ICON 업데이트
            if(!data || data.length <= 0) {
                setApiFailureIcon();
            }

            infoPubNotiMonChart.destroy();
            createInfoPubNotiMonChart();
        },
        error: function(error) {
            console.error('Error:', error);
            setApiFailureIcon();
        }
    });
}

function clickRveExptrAnnChart(year) {
    $.ajax({
        url: '/gslsEsb/getRveExptrMonCnt?year='+year,
        type: 'GET',
        dataType: 'json',
        success: function(data) {
            rveExptrMonData = data;

            setApiSuccessIcon();

            //데이터가 존재하지 않을 시 API 호출 상태 ICON 업데이트
            if(!data || data.length <= 0) {
                setApiFailureIcon();
            }

            rveExptrMonChart.destroy();
            createRveExptrMonChart();
        },
        error: function(error) {
            console.error('Error:', error);
            setApiFailureIcon();
        }
    });
}

function clickFnlsttAnnChart(year) {
    $.ajax({
        url: '/gslsEsb/getFnlsttMonCnt?year='+year,
        type: 'GET',
        dataType: 'json',
        success: function(data) {
            fnlsttMonData = data;

            setApiSuccessIcon();

            //데이터가 존재하지 않을 시 API 호출 상태 ICON 업데이트
            if(!data || data.length <= 0) {
                setApiFailureIcon();
            }

            fnlsttMonChart.destroy();
            createFnlsttMonChart();
        },
        error: function(error) {
            console.error('Error:', error);
            setApiFailureIcon();
        }
    });
}

function clickExcutAnnChart(year) {
    $.ajax({
        url: '/gslsEsb/getExcutMonCnt?year='+year,
        type: 'GET',
        dataType: 'json',
        success: function(data) {
            excutMonData = data;

            setApiSuccessIcon();

            //데이터가 존재하지 않을 시 API 호출 상태 ICON 업데이트
            if(!data || data.length <= 0) {
                setApiFailureIcon();
            }

            excutMonChart.destroy();
            createExcutMonChart();
        },
        error: function(error) {
            console.error('Error:', error);
            setApiFailureIcon();
        }
    });
}

function clickDtlBsnsInfoAnnChart(year) {
    $.ajax({
        url: '/gslsEsb/getDtlBsnsInfoMonCnt?year='+year,
        type: 'GET',
        dataType: 'json',
        success: function(data) {
            dtlBsnsInfoMonData = data;

            setApiSuccessIcon();

            //데이터가 존재하지 않을 시 API 호출 상태 ICON 업데이트
            if(!data || data.length <= 0) {
                setApiFailureIcon();
            }

            dtlBsnsInfoMonChart.destroy();
            createDtlBsnsInfoMonChart();
        },
        error: function(error) {
            console.error('Error:', error);
            setApiFailureIcon();
        }
    });
}

function datePickerInit() {
    rangeDatePickerInit();
}