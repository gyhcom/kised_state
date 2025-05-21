let batchHisGrid;
let picker;

document.addEventListener("DOMContentLoaded", function () {
    datePickerInit();
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

    // 차트 카드 애니메이션
    gsap.from("#contentDiv", {
        duration: 1,
        scale: 0.9,
        opacity: 0,
        delay: 0.5,
        ease: "power2.out",
    });
});

function gridInit() {
    let stepName = $('#stepName').val();
    let status = $('#status').val();
    let startTime = "" + picker.getStartDate().getFullYear() +
                            convertDateForm(picker.getStartDate().getMonth()+1) +
                            convertDateForm(picker.getStartDate().getDate());

    let endTime = "" + picker.getEndDate().getFullYear() +
                            convertDateForm(picker.getEndDate().getMonth()+1) +
                            convertDateForm(picker.getEndDate().getDate());

    $.ajax({
        url: '/batchHis/getBatchHisList?stepName='+stepName+'&status='+status+"&startTime="+startTime+"&endTime="+endTime,
        type: 'GET',
        dataType: 'json',
        success: function(data) {
            if( batchHisGrid == null ) createGrid(data);
            else resetGridData(data);
        },
        error: function(error) {
            console.error('Error:', error);
        }
    });
}

// 정보공시내역
function createGrid(data) {
    batchHisGrid = new tui.Grid({
        el: document.getElementById('grid'),
        data: data,
        scrollX: false,
        scrollY: false,
        columns: [
            {
                header: 'StepName',
                name: 'stepName'
            },
            {
                header: '상태',
                name: 'status'
            },
            {
                header: '시작 시간',
                name: 'startTime'
            },
            {
                header: '종료 시간',
                name: 'endTime'
            },
            {
                header: '에러 메시지',
                name: 'exitMessage'
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
                stepName: {
                    template(summary) {
                        return 'TOTAL : ' + summary.cnt;
                    }
                }
            }
        }
    });

    tui.Grid.applyTheme('default', getTheme());
}

function resetGridData(data) {
    batchHisGrid.resetData(data);
}

function getTheme() {
    return {
        selection: {
            background: '#4daaf9',
            border: '#004082'
        },
        scrollbar: {
            background: '#f5f5f5',
            thumb: '#d9d9d9',
            active: '#c1c1c1'
        },
        row: {
            even: {
                background: '#dee6fc'
            },
            hover: {
                background: '#ccc'
            }
        },
        cell: {
            normal: {
                background: '#fbfbfb',
                border: '#e0e0e0',
                showVerticalBorder: true
            },
            header: {
                background: '#eee',
                border: '#ccc',
                showVerticalBorder: true
            },
            rowHeader: {
                border: '#ccc',
                showVerticalBorder: true
            },
            editable: {
                background: '#fbfbfb'
            },
            selectedHeader: {
                background: '#d8d8d8'
            },
            focused: {
                border: '#418ed4'
            },
            disabled: {
                text: '#b0b0b0'
            }
        }
    }
}

function datePickerInit() {
    picker = rangeDatePickerInit();
}

//정보공시 내역 엑셀 다운로드
function excelDownload() {
    batchHisGrid.export('xls', { onlySelected: true, fileName: '배치 실행 이력' });
}