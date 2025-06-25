// GSAP 슬라이드 애니메이션
let current = 0;

document.addEventListener("DOMContentLoaded", function () {
    const total = document.querySelectorAll('.chart-slide').length;

    /* 이전 버튼 */
    document.getElementById('prevBtn').addEventListener('click', () => {
        current = (current - 1 + total) % total;
        showSlide(current);
    })

    /* 다음 버튼 */
    document.getElementById('nextBtn').addEventListener('click', () => {
        current = (current + 1) % total;
        showSlide(current);
    })

    // 최초 1번 슬라이드 표시
    showSlide(0);

    // 슬라이드 차트가 2개 일 경우 실행
    const total2 = document.querySelectorAll('.chart-slide2').length;
    if( total2 > 0 ) {
        /* 이전 버튼 */
        document.getElementById('prevBtn2').addEventListener('click', () => {
            current = (current - 1 + total2) % total2;
            showSlide2(current);
        })

        /* 다음 버튼 */
        document.getElementById('nextBtn2').addEventListener('click', () => {
            current = (current + 1) % total2;
            showSlide2(current);
        })

        // 최초 1번 슬라이드 표시
        showSlide2(0);
    }
})

function showSlide(index) {
    const slides = document.querySelectorAll('.chart-slide');
    slides.forEach((slide, i) => {
        if(i === index) {
            gsap.to(slide, {duration: 0.5, left: 0, opacity: 1, zIndex: 1});
            slide.classList.add('active');
        } else {
            gsap.to(slide, {duration: 0.5, left: '100%', opacity: 0, zIndex: 0});
            slide.classList.remove('active');
        }
    })
}

function showSlide2(index) {
    const slides = document.querySelectorAll('.chart-slide2');
    slides.forEach((slide, i) => {
        if(i === index) {
            gsap.to(slide, {duration: 0.5, left: 0, opacity: 1, zIndex: 1});
            slide.classList.add('active');
        } else {
            gsap.to(slide, {duration: 0.5, left: '100%', opacity: 0, zIndex: 0});
            slide.classList.remove('active');
        }
    })
}