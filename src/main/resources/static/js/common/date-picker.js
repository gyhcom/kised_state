function rangeDatePickerInit() {
    let today = new Date();
    let picker = tui.DatePicker.createRangePicker({
        startpicker: {
            date: new Date(today.getFullYear(), 0, 1),
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

    // 다른 js에서 import 없이 date-picker 값을 사용하기 위함
    return picker;
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

/*
* month, date 형식 한자리 일 경우 앞자리에 "0" 추가
* ex) 202536 -> 20250306
*/
function convertDateForm(date) {
    if(date < 10) {
        date = "0" + date;
    }

    return date;
}