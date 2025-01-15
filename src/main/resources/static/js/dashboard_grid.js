/*https://github.com/nhn/tui.grid/blob/master/packages/toast-ui.grid/examples/example23-client-pagination.html*/
function gridInit(url) {
    if(!url) url = '/service1';

    $.ajax({
        url: url + '/getGridData',
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