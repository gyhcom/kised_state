let infoPubNotiGrid;
let rveExptrGrid;
let fnlsttGrid;
let excutGrid;
let dtlBsnsInfoGrid;

let infoPubNotiData;
let rveExptrData;
let fnlsttData;
let excutData;
let dtlBsnsInfoData;

let datepicker;

document.addEventListener("DOMContentLoaded", function () {
    datePickerInit();
    gridInit();
});

function gridInit(year, month, searchValue) {
    //최초 조회 시 현재 년도 가져오기
    if(!year || year == '') year = new Date().getFullYear();
    if(!month || month == '') month = new Date().getMonth() + 1;
    if(searchValue && searchValue.length > 0) searchValue = searchValue.trim();

    $.ajax({
        url: '/gslsEsb/getAllGslsEsbDtlInfo?year='+year+"&month="+month+"&searchValue="+searchValue,
        type: 'GET',
        dataType: 'json',
        success: function(data) {
            /**
             * [0] : 정보공시내역
             * [1] : 세입세출내역
             * [2] : 재무제표결산 내역
             * [3] : 수급자집행정보
             * [4] : 상세내역사업정보
             */
            infoPubNotiData = data[0];
            rveExptrData = data[1];
            fnlsttData = data[2];
            excutData = data[3];
            dtlBsnsInfoData = data[4];

            setApiSuccessIcon();

            //데이터가 존재하지 않을 시 API 호출 상태 ICON 업데이트
            if(!validateData()) {
                setApiFailureIcon();
            }

            createInfoPubNotiGrid();

            createRveExptrGrid();

            createFnlsttGrid();

            createExcutGrid();

            createDtlBsnsInfoGrid();
        },
        error: function(error) {
            console.error('Error:', error);
            setApiFailureIcon();
        }
    });
}

// 정보공시내역
function createInfoPubNotiGrid() {
    infoPubNotiGrid = new tui.Grid({
        el: document.getElementById('infoPubNotoGrid'),
        data: infoPubNotiData,
        scrollX: false,
        scrollY: false,
        columns: [
            {
                header: '사업코드',
                name: 'bsnsId'
            },
            {
                header: '사업명',
                name: 'bsnsNm'
            },
            {
                header: '정보공시내역 건수',
                name: 'cnt'
            },
            {
                header: '연계일시',
                name: 'cntcCreatDt'
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
                bsnsId: {
                    template(summary) {
                        return 'TOTAL : ' + summary.cnt;
                    }
                }
            }
        }
    });
}

// 연도별 세입세출내역 건수
function createRveExptrGrid() {
    rveExptrGrid = new tui.Grid({
        el: document.getElementById('rveExptrGrid'),
        data: rveExptrData,
        scrollX: false,
        scrollY: false,
        columns: [
            {
                header: '사업코드',
                name: 'bsnsId'
            },
            {
                header: '사업명',
                name: 'bsnsNm'
            },
            {
                header: '정보공시내역 건수',
                name: 'cnt'
            },
            {
                header: '연계일시',
                name: 'cntcCreatDt'
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
                bsnsId: {
                    template(summary) {
                        return 'TOTAL : ' + summary.cnt;
                    }
                }
            }
        }
    });
}

// 연도별 재무제표결산 내역 건수
function createFnlsttGrid() {
    fnlsttGrid = new tui.Grid({
        el: document.getElementById('fnlsttGrid'),
        data: fnlsttData,
        scrollX: false,
        scrollY: false,
        columns: [
            {
                header: '사업코드',
                name: 'bsnsId'
            },
            {
                header: '사업명',
                name: 'bsnsNm'
            },
            {
                header: '정보공시내역 건수',
                name: 'cnt'
            },
            {
                header: '연계일시',
                name: 'cntcCreatDt'
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
                bsnsId: {
                    template(summary) {
                        return 'TOTAL : ' + summary.cnt;
                    }
                }
            }
        }
    });
}

// 연도별 수급자집행정보 건수
function createExcutGrid() {
    excutGrid = new tui.Grid({
        el: document.getElementById('excutGrid'),
        data: excutData,
        scrollX: false,
        scrollY: false,
        columns: [
            {
                header: '사업코드',
                name: 'bsnsId'
            },
            {
                header: '사업명',
                name: 'bsnsNm'
            },
            {
                header: '정보공시내역 건수',
                name: 'cnt'
            },
            {
                header: '연계일시',
                name: 'cntcCreatDt'
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
                bsnsId: {
                    template(summary) {
                        return 'TOTAL : ' + summary.cnt;
                    }
                }
            }
        }
    });
}

// 연도별 상세내역 사업정보 건수
function createDtlBsnsInfoGrid() {
    dtlBsnsInfoGrid = new tui.Grid({
        el: document.getElementById('dtlBsnsInfoGrid'),
        data: dtlBsnsInfoData,
        scrollX: false,
        scrollY: false,
        columns: [
            {
                header: '사업코드',
                name: 'bsnsId'
            },
            {
                header: '사업명',
                name: 'bsnsNm'
            },
            {
                header: '정보공시내역 건수',
                name: 'cnt'
            },
            {
                header: '연계일시',
                name: 'cntcCreatDt'
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
                bsnsId: {
                    template(summary) {
                        return 'TOTAL : ' + summary.cnt;
                    }
                }
            }
        }
    });
}

function validateData() {
    if(!infoPubNotiData || infoPubNotiData.length <= 0) return false;

    if(!rveExptrData || rveExptrData.length <= 0) return false;

    if(!fnlsttData || fnlsttData.length <= 0) return false;

    if(!excutData || excutData.length <= 0) return false;

    if(!dtlBsnsInfoData || dtlBsnsInfoData.length <= 0) return false;

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

function searchData() {
    infoPubNotiGrid.destroy();
    rveExptrGrid.destroy();
    fnlsttGrid.destroy();
    excutGrid.destroy();
    dtlBsnsInfoGrid.destroy();
    
    var year = datepicker.getDate().getFullYear();
    var month = datepicker.getDate().getMonth()+1;
    var searchValue = $('#searchValue3').val();

    gridInit(year, month, searchValue);
}

// let infoPubNotiGrid;
// let rveExptrGrid;
// let fnlsttGrid;
// let excutGrid;
// let dtlBsnsInfoGrid;

//정보공시 내역 엑셀 다운로드
function infoPubNotiExDwnld() {
    infoPubNotiGrid.export('xls', { onlySelected: true, fileName: '정보공시 내역' });
}

//세입세출 내역 엑셀 다운로드
function rveExptrExDwnld() {
    rveExptrGrid.export('xls', { onlySelected: true, fileName: '세입세출 내역' });
}

//재무제표 결산서 내역 엑셀 다운로드
function fnlsttExDwnld() {
    fnlsttGrid.export('xls', { onlySelected: true, fileName: '재무제표 결산서 내역' });
}

//수급자 집행정보 엑셀 다운로드
function excutExDwnld() {
    excutGrid.export('xls', { onlySelected: true, fileName: '수급자 집행정보' });
}

//상세내역 사업정보 엑셀 다운로드
function dtlBsnsInfoExDwnld() {
    dtlBsnsInfoGrid.export('xls', { onlySelected: true, fileName: '상세내역 사업정보' });
}