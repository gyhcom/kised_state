let grid;

document.addEventListener('DOMContentLoaded', function() {
    /* 회원별 통계 상세 모달 */
    const modalEl = document.getElementById('myModal');
    const bootstrapModal = new bootstrap.Modal(modalEl);

    // 버튼 클릭 -> 모달 열고 값 전달
    document.querySelectorAll('.open-modal-btn').forEach((btn) => {
        btn.addEventListener('click', () => {
            const statType = btn.getAttribute('systemNm');

            // 모달에 전달할 userId 저장
            modalEl.setAttribute('systemNm', statType);

            // 모달 열기
            bootstrapModal.show();
        });
    });

    // 모달이 열릴 때 -> 값 꺼내서 데이터 조회
    modalEl.addEventListener('shown.bs.modal', () => {
        const systemNm = modalEl.getAttribute('systemNm');

        // 데이터 조회 -> 그리드 세팅
        fetchUserDetail(systemNm).then(data => {
            // SELECT 박스 세팅
            console.dir(data);

            if(data != null && data.length > 0) {
                const selectBox = document.getElementById('select-statTypes');

                /* 동적 추가된 class 삭제 후 option 추가 */
                const dynamicOptions = document.querySelectorAll('.dynamicOption');
                if(dynamicOptions != null && dynamicOptions.length > 0) {
                    dynamicOptions.forEach(option => option.remove());
                }

                data.forEach(item => {
                    const option = document.createElement('option');
                    option.className = 'dynamicOption';
                    option.value = item.statType;
                    option.textContent = item.typeNm;
                    selectBox.appendChild(option);
                })
            }
        });

        grid.refreshLayout();
    });
});

// 데이터 조회 함수
function fetchUserDetail(systemNm) {
    return new Promise(function (resolve) {
        $.ajax({
            url: '/comm/getStatTypes?systemNm='+systemNm,
            type: 'GET',
            dataType: 'json',
            success: function(data) {
                console.dir(data);
                resolve(data); /* 성공 시 데이터 반환 */
            },
            error: function(error) {
                console.error('Error : ', error);
            }
        });
    });
}

function createModalGrid() {
    grid = new tui.Grid({
        el: document.getElementById('grid'),
        data: [],
        scrollX: false,
        scrollY: false,
        columns: [
            {
                header: '시스템명',
                name: 'sourceSystem',
                width: [250],
                resizable: [true]
            },
            {
                header: '통계 항목',
                name: 'statType',
                width: [200],
                resizable: [true]
            },
            {
                header: '건수',
                name: 'cnt',
                width: [100],
                resizable: [true]
            },
            {
                header: '데이터 기준일자 ',
                name: 'baseDt',
                width: [100],
                resizable: [true]
            },
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
                sourceSystem: {
                    template(summary) {
                        return 'TOTAL : ' + summary.cnt;
                    }
                }
            }
        }
    });
}

function getDetailStatsList() {
    let statType = $('#select-statTypes').val();

    let startTime = "" + picker.getStartDate().getFullYear() + "-" +
        convertDateForm(picker.getStartDate().getMonth()+1) + "-" +
        convertDateForm(picker.getStartDate().getDate());

    let endTime = "" + picker.getEndDate().getFullYear() + "-" +
        convertDateForm(picker.getEndDate().getMonth()+1) + "-" +
        convertDateForm(picker.getEndDate().getDate());

    $.ajax({
        url: '/comm/getDetailStats',
        type: 'GET',
        data: {
            statType : statType,
            startTime : startTime,
            endTime : endTime
        },
        dataType: 'json',
        success: function(data) {
            console.dir(data);

            if( data == null || data.length <= 0 ) {
                alert('데이터가 존재하지 않습니다.');
                return;
            }

            grid.resetData(data);
        },
        error: function(error) {
            console.error('Error : ', error);
        }
    })
}





