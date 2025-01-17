// 서비스별 html과 자바스크립트를 동적으로 생성
function loadHTMLAndScript(htmlPath) {
    let df = document.getElementById("dynamicFrame");
    if (df) {
        df.remove();
    }

    // 새로운 스크립트 추가
    const frame = document.createElement("iframe");
    frame.src = htmlPath;
    frame.id = "dynamicFrame";
    frame.classList.add('col-md-10');
    frame.classList.add('p-4');
    frame.classList.add('h-100');

    document.getElementById('frameContainer').appendChild(frame);
}