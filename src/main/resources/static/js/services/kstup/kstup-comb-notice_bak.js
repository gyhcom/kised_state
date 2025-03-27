let serviceGrid;
let datepicker;

document.addEventListener("DOMContentLoaded", function () {
    datePickerInit();
    gridInit();
});

function gridInit(year, month, searchValue1, searchValue2, searchValue3) {
    $.ajax({
        url: '/kstup/getCombNotiReg',
        type: 'GET',
        dataType: 'json',
        data: {
            "year": year,
            "month": month,
            "searchValue1": searchValue1,
            "searchValue2": searchValue2,
            "searchValue3": searchValue3
        },
        success: function(data) {
            //console.dir(data);
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
                header: '분야 idx',
                name: 'fieldIdx',
                hidden: true
            },
            {
                header: '분야',
                name: 'fieldNm'
            },
            {
                header: '소관부처',
                name: 'cowCrownBuddha'
            },
            {
                header: '사업명',
                name: 'bsnsNm'
            },
            {
                header: '등록일',
                name: 'date'
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
                fieldNm: {
                    template(summary) {
                        return 'TOTAL : ' + summary.cnt;
                    }
                }
            }
        }
    });

    tui.Grid.applyTheme('striped');
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
    var searchVal1 = $('#searchValue1').val(); // 분야별
    var searchVal2 = $('#searchValue2').val(); // 소관부처
    var searchVal3 = $('#searchValue3').val(); // 사업명

    //그리드 제거
    serviceGrid.destroy();
    //그리드 재생성
    gridInit(year, month, searchVal1, searchVal2, searchVal3);
}