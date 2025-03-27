export class GSAPInfoBar {
    constructor({
                    link = "https://gsap.com/docs/v3",
                    club = false,
                    container = document.body,
                    position = "bottom",
                    text = "Learn More"
                }) {
        this.link = link;
        this.container = container;
        this.club = club;
        this.position = position;
        this.text = text;
        this.createInfoBar();
    }

    createInfoBar() {
        // Create the infobar container
        const aside = document.createElement("aside");
        aside.className = "gsap-infobar";

        // Apply inline styles that do not rely on CSS variables
        aside.style.position = "fixed";

        if (this.position === "bottom") {
            aside.style.bottom = "0.75rem";
        }
        if (this.position === "top") {
            aside.style.top = "0.75rem";
        }

        const style = document.createElement("style");
        style.textContent = `
       .gsap-infobar {
        position: fixed;
        left: 50%;
        z-index: 99999;
        transform: translateX(-50%);
        padding: 0.75rem 1rem;
        display: flex;
        align-items: center;
        justify-content: space-between;
        gap: 0.5rem;
        font-size: 14px;
        font-family: Mori, sans-serif;
        background-color: #0e100f;
        color: var(--infobar-theme-alt);
        border: solid 2px var(--infobar-theme-highlight);
        border-radius: 99px;
        --infobar-theme: #bbbaa6;
        --infobar-theme-alt: #fffce1;
        --infobar-theme-highlight: #fffce1;
      }
      
      @media (max-height: 600px) {
        .gsap-infobar {
          display: none !important;
        }
      }
    `;
        document.head.appendChild(style);

        // Add SVG and other elements
        aside.innerHTML = `
      <a href="https://gsap.com/docs/v3" style="align-self: stretch; display: flex; align-items: center; justify-content: center">
      <svg class="gsap-infobar__svg" viewBox="0 0 505 636" fill="none" xmlns="http://www.w3.org/2000/svg" style="width: 1rem;">
        <path fill="var(--infobar-theme-alt)" d="M505 296.654C505 296.654 505 296.829 505 296.934L482.197 396.386C480.967 402.012 475.38 406.135 468.915 406.135H441.404C439.366 406.135 437.539 407.498 436.977 409.455C411.573 496.082 377.175 555.628 331.745 591.376C293.095 621.813 245.451 636 181.819 636C124.618 636 86.0739 617.514 53.3623 581.032C10.1451 532.809 -7.73911 453.869 3.08277 358.75C22.6184 180.184 114.745 0.0104212 292.217 0.0104212C346.185 -0.478802 388.559 16.2596 418.039 49.6665C449.204 84.9954 465.05 138.216 465.121 207.86C465.015 214.185 459.78 219.287 453.491 219.287H323.488C318.885 219.287 314.774 215.129 314.844 210.551C313.79 162.397 299.56 138.915 271.311 138.915C221.488 138.915 192.079 206.917 176.479 244.622C154.694 297.283 143.627 354.452 145.805 411.412C146.824 437.935 151.075 475.22 176.127 490.666C198.333 504.364 230.026 495.279 249.21 480.113C268.394 464.947 283.819 438.703 290.319 414.766C291.233 411.447 291.303 408.861 290.425 407.708C289.511 406.52 286.981 406.24 285.049 406.24H251.67C248.086 406.24 244.537 404.598 242.394 401.942C240.602 399.705 239.899 396.945 240.532 394.219L263.37 294.592C264.494 289.491 269.097 285.647 274.754 284.948V284.703H493.827C494.354 284.703 494.881 284.703 495.373 284.808C501.065 285.542 505.07 290.853 504.965 296.654H505Z" />
      </svg>
      </a>
      <span class="divider" style="display: inline-flex; align-self: stretch; width: 2px; background-color: var(--infobar-theme);"></span>
      <a href="${this.link}" target="_blank" style="color: var(--infobar-theme-alt); text-decoration: none;">${this.text}</a>
    `;

        // Append the infobar to the container
        this.container.appendChild(aside);

        this.initEvents(aside);

        // Animate the infobar using GSAP
        this.animateInfoBar(aside);
    }

    initEvents(el) {
        el.addEventListener("mouseenter", () => this.hover(el, true));
        el.addEventListener("mouseleave", () => this.hover(el, false));
    }

    hover(el, isHover) {
        let color = "#abff84";

        gsap.to(el, {
            "--infobar-theme-highlight": isHover ? color : "#fffce1"
        });
    }

    animateInfoBar(el) {
        gsap.from(el, {
            autoAlpha: 0,
            yPercent: this.position === "top" ? -50 : 50,
            ease: "expo.out",
            duration: 1,
            delay: 1
        });
    }
}

new GSAPInfoBar({
  link: "https://gsap.com/docs/v3/Plugins/Draggable/",
  position: "top"
});