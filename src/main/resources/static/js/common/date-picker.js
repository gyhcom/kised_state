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
        //console.log(123);
    })
}