let serviceGrid;
let datepicker;

let preDetAnnChart;
let preDetMonChart;

let preDetProfAnnChart;
let preDetProfMonChart;

let totDetAnnChart;
let totDetMonChart;

let preDetAnnData;
let preDetMonData;

let preDetProfAnnData;
let preDetProfMonData;

let totDetAnnData;
let totDetMonData;

document.addEventListener("DOMContentLoaded", function () {
    datePickerInit();
    chartInit();
    gridInit();

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

    $.ajax({
        url: '/fds/getAllDetCnt?year='+year,
        type: 'GET',
        dataType: 'json',
        success: function(data) {
            /**
             * [0] : 연도별 사전 탐지 건수
             * [1] : 월별 사전 탐지 건수
             * [2] : 연도별 사전 탐지 증빙 제출 건수
             * [3] : 월별 사전 탐지 증빙 제출 건수
             * [4] : 연도별 총 이상거래 탐지 건수
             * [5] : 월별 총 이상거래 탐지 건수
             */
            preDetAnnData = data[0];
            preDetMonData = data[1];

            preDetProfAnnData = data[2];
            preDetProfMonData = data[3];

            totDetAnnData = data[4];
            totDetMonData = data[5];

            setApiSuccessIcon();

            //데이터가 존재하지 않을 시 API 호출 상태 ICON 업데이트
            if(!validateData()) {
                setApiFailureIcon();
            }

            createPreDetAnnChart();
            createPreDetMonChart();

            createPreDetProfAnnChart();
            createPreDetProfMonChart();

            createTotDetAnnChart();
            createTotDetMonChart();
        },
        error: function(error) {
            console.error('Error:', error);
            setApiFailureIcon();
        }
    });
}

function gridInit(year, month) {
    $.ajax({
        url: '/fds/getDetInfo',
        type: 'GET',
        dataType: 'json',
        data: {
            "year": year,
            "month": month
        },
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
                header: '탐지유형명',
                name: 'detTpNm',
                width: [250],
                resizable: [true]
            },
            {
                header: '탐지유형 설명',
                name: 'detTpSummary',
                width: [800],
                resizable: [true]
            },
            {
                header: '탐지 건수',
                name: 'detCnt',
                width: [100],
                resizable: [true]
            },
            {
                header: '탐지일',
                name: 'ymd',
                resizable: [true]
            }
        ],
        rowHeaders: ['rowNum'],
        pageOptions: {
            useClient: true,
            perPage: 10
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
}

// 연도별 사전 탐지 건수 차트 생성
function createPreDetAnnChart() {
    const el = document.getElementById('preDetCntAnnualChart');
    let data = {
        categories: [],
        series: [],
    };

    let annual_data = [];

    if( preDetAnnData != null && preDetAnnData.length > 0 ) {
        //categories 세팅
        for( var i = 0 ; i < preDetAnnData.length ; i++ ) {
            data.categories.push(preDetAnnData[i].year);
            annual_data.push(preDetAnnData[i].cnt);
        }

        data.series.push({
            name : 'Count',
            data : annual_data
        })
    }

    const theme = getTheme();

    const options = {
        chart: {title: '연도별 사전 탐지 건수', width: 'auto', height: 350},
        legend: {visible: false},
        xAxis: {pointOnColumn: false, title: {text: 'year'}},
        yAxis: {title: 'count'},
        series: { showDot: true, dataLabels: { visible: true, offsetY: -5 }, selectable: true },
        theme
    };

    preDetAnnChart = toastui.Chart.columnChart({el, data, options});

    preDetAnnChart.on('selectSeries', function(e) {
        let year = e.column[0].data.category;
        clickPreDetAnnChart(year);
    })
}

// 월별 사전 탐지 건수 차트 생성
function createPreDetMonChart() {
    const el = document.getElementById('preDetCntMonthlyChart');
    let data = {
        categories: [],
        series: [],
    };

    let monthly_data = [];

    if( preDetMonData != null && preDetMonData.length > 0 ) {
        //categories 세팅
        for( var i = 0 ; i < preDetMonData.length ; i++ ) {
            data.categories.push(preDetMonData[i].month);
            monthly_data.push(preDetMonData[i].cnt);
        }

        data.series.push({
            name : 'Count',
            data : monthly_data
        })
    }

    const theme = getTheme();

    const options = {
        chart: {title: preDetMonData[0].year+'년 월별 사전 탐지 건수', width: 'auto', height: 350},
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

    preDetMonChart = toastui.Chart.columnChart({ el, data, options });
}

// 연도별 사전 탐지 증빙 제출 건수 차트 생성
function createPreDetProfAnnChart() {
    const el = document.getElementById('preDetProfCntAnnualChart');
    let data = {
        categories: [],
        series: [],
    };

    let annual_data = [];

    if( preDetProfAnnData != null && preDetProfAnnData.length > 0 ) {
        //categories 세팅
        for( var i = 0 ; i < preDetProfAnnData.length ; i++ ) {
            data.categories.push(preDetProfAnnData[i].year);
            annual_data.push(preDetProfAnnData[i].cnt);
        }

        data.series.push({
            name : 'Count',
            data : annual_data
        })
    }

    const theme = getTheme();

    const options = {
        chart: {title: '연도별 사전 탐지 증빙 제출 건수', width: 'auto', height: 350},
        legend: {visible: false},
        xAxis: {pointOnColumn: false, title: {text: 'year'}},
        yAxis: {title: 'count'},
        series: { showDot: true, dataLabels: { visible: true, offsetY: -5 }, selectable: true },
        theme
    };

    //차트 색상 변경
    options.theme.series.colors = ['#2bb385'];

    preDetProfAnnChart = toastui.Chart.columnChart({el, data, options});

    preDetProfAnnChart.on('selectSeries', function(e) {
        let year = e.column[0].data.category;
        clickPreDetProfAnnChart(year)
    })
}

// 월별 사전 탐지 증빙 제출 건수 차트 생성
function createPreDetProfMonChart() {
    const el = document.getElementById('preDetProfCntMonthlyChart');
    let data = {
        categories: [],
        series: [],
    };

    let monthly_data = [];

    if( preDetProfMonData != null && preDetProfMonData.length > 0 ) {
        //categories 세팅
        for( var i = 0 ; i < preDetProfMonData.length ; i++ ) {
            data.categories.push(preDetProfMonData[i].month);
            monthly_data.push(preDetProfMonData[i].cnt);
        }

        data.series.push({
            name : 'Count',
            data : monthly_data
        })
    }

    const theme = getTheme();

    const options = {
        chart: {title: preDetProfMonData[0].year+'년 월별 사전 탐지 증빙 제출 건수', width: 'auto', height: 350},
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

    preDetProfMonChart = toastui.Chart.columnChart({ el, data, options });
}

// 연도별 총 이상거래 탐지 건수 차트 생성
function createTotDetAnnChart() {
    const el = document.getElementById('totalDetCntAnnualChart');
    let data = {
        categories: [],
        series: [],
    };

    let annual_data = [];

    if( totDetAnnData != null && totDetAnnData.length > 0 ) {
        //categories 세팅
        for( var i = 0 ; i < totDetAnnData.length ; i++ ) {
            data.categories.push(totDetAnnData[i].year);
            annual_data.push(totDetAnnData[i].cnt);
        }

        data.series.push({
            name : 'Count',
            data : annual_data
        })
    }

    const theme = getTheme();

    const options = {
        chart: {title: '연도별 총 이상거래탐지 건수', width: 'auto', height: 350},
        legend: {visible: false},
        xAxis: {pointOnColumn: false, title: {text: 'year'}},
        yAxis: {title: 'count'},
        series: { showDot: true, dataLabels: { visible: true, offsetY: -5 }, selectable: true },
        theme
    };

    //차트 색상 변경
    options.theme.series.colors = ['#d68e29'];

    totDetAnnChart = toastui.Chart.columnChart({el, data, options});

    totDetAnnChart.on('selectSeries', function(e) {
        let year = e.column[0].data.category;
        clickTotDetAnnChart(year);
    })
}

//월별 총 이상거래 탐지 건수 차트 생성
function createTotDetMonChart() {
    const el = document.getElementById('totalDetCntMonthlyChart');
    let data = {
        categories: [],
        series: [],
    };

    let monthly_data = [];

    if( totDetMonData != null && totDetMonData.length > 0 ) {
        //categories 세팅
        for( var i = 0 ; i < totDetMonData.length ; i++ ) {
            data.categories.push(totDetMonData[i].month);
            monthly_data.push(totDetMonData[i].cnt);
        }

        data.series.push({
            name : 'Count',
            data : monthly_data
        })
    }

    const theme = getTheme();

    const options = {
        chart: {title: totDetMonData[0].year+'년 월별 총 이상거래탐지 건수', width: 'auto', height: 350},
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

    totDetMonChart = toastui.Chart.columnChart({ el, data, options });
}

function datePickerInit() {
    rangeDatePickerInit()
}

function excelDownload() {
    serviceGrid.export('xls', { onlySelected: true });
}

function searchData() {
    var year = datepicker.getDate().getFullYear();
    var month = datepicker.getDate().getMonth()+1;

    //그리드 제거
    serviceGrid.destroy();
    //그리드 재생성
    gridInit(year, month);
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
    if(!preDetAnnData || preDetAnnData.length <= 0) return false;
    if(!preDetMonData || preDetMonData.length <= 0) return false;
    if(!preDetProfAnnData || preDetProfAnnData.length <= 0) return false;

    if(!preDetProfMonData || preDetProfMonData.length <= 0) return false;
    if(!totDetAnnData || totDetAnnData.length <= 0) return false;
    if(!totDetMonData || totDetMonData.length <= 0) return false;

    return true;
}

function clickPreDetAnnChart(year) {
    $.ajax({
        url: '/fds/getDetMonCnt?year='+year,
        type: 'GET',
        dataType: 'json',
        success: function(data) {
            preDetMonData = data;

            setApiSuccessIcon();

            //데이터가 존재하지 않을 시 API 호출 상태 ICON 업데이트
            if(!data || data.length <= 0) {
                setApiFailureIcon();
            }

            preDetMonChart.destroy();
            createPreDetMonChart();
        },
        error: function(error) {
            console.error('Error:', error);
            setApiFailureIcon();
        }
    });
}

function clickPreDetProfAnnChart(year) {
    $.ajax({
        url: '/fds/getDetProfMonCnt?year='+year,
        type: 'GET',
        dataType: 'json',
        success: function(data) {
            preDetProfMonData = data;

            setApiSuccessIcon();

            //데이터가 존재하지 않을 시 API 호출 상태 ICON 업데이트
            if(!data || data.length <= 0) {
                setApiFailureIcon();
            }

            preDetProfMonChart.destroy();
            createPreDetProfMonChart();
        },
        error: function(error) {
            console.error('Error:', error);
            setApiFailureIcon();
        }
    });
}

function clickTotDetAnnChart(year) {
    $.ajax({
        url: '/fds/getTotDetMonCnt?year='+year,
        type: 'GET',
        dataType: 'json',
        success: function(data) {
            totDetMonData = data;

            setApiSuccessIcon();

            //데이터가 존재하지 않을 시 API 호출 상태 ICON 업데이트
            if(!data || data.length <= 0) {
                setApiFailureIcon();
            }

            totDetMonChart.destroy();
            createTotDetMonChart();
        },
        error: function(error) {
            console.error('Error:', error);
            setApiFailureIcon();
        }
    });
}