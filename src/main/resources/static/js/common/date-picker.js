function rangeDatePickerInit() {
    var today = new Date();
    var picker = tui.DatePicker.createRangePicker({
        startpicker: {
            date: new Date(today.getFullYear()-1, 0, 1),
            input: '#startpicker-input',
            container: '#startpicker-container'
        },
        endpicker: {
            date: today,
            input: '#endpicker-input',
            container: '#endpicker-container'
        },
        selectableRanges: [
            [new Date(2020, 0, 1), new Date(today.getFullYear() + 1, today.getMonth(), today.getDate())],
            [new Date(2020, 0, 1), new Date(today.getFullYear() + 1, today.getMonth(), today.getDate())]
        ],
        format: 'YYYY-MM-dd'
    });

    picker.on('change:end', () => {
    })

    // 기간별 데이터 정의 될 때까지 비활성화
    picker._endpicker.disable();
    picker._startpicker.disable();
}

function singleDatePickerInit() {
    return new tui.DatePicker('#wrapper', {
        language: 'en',
        date: new Date(),
        input: {
            element: '#datepicker-input',
            format: 'yyyy-MM'
        },
        type: 'month',
    });
}