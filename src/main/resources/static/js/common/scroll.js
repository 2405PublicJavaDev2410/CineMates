class SmoothScroll {
    constructor(element, options = {}) {
        this.element = element;
        this.options = Object.assign({
            ease: 0.1,  // 부드러움 정도 (0에 가까울수록 더 부드러움)
            wheelMultiplier: 2.5  // 휠 스크롤 속도 조절
        }, options);

        this.scrollTarget = 0;
        this.scrollCurrent = 0;
        this.height = 0;
        this.scrollHeight = 0;

        this.init();
        this.events();
        this.requestId = null;
    }

    init() {
        this.scrollHeight = this.element.scrollHeight;
        this.height = this.element.clientHeight;
        this.render();
    }

    events() {
        window.addEventListener('resize', this.init.bind(this));
        this.element.addEventListener('wheel', this.wheel.bind(this), { passive: false });
    }

    wheel(e) {
        e.preventDefault();
        this.scrollTarget += e.deltaY * this.options.wheelMultiplier;
        this.clamp();
        if (!this.requestId) {
            this.requestId = requestAnimationFrame(this.render.bind(this));
        }
    }

    clamp() {
        this.scrollTarget = Math.max(0, Math.min(this.scrollTarget, this.scrollHeight - this.height));
    }

    render() {
        const diff = this.scrollTarget - this.scrollCurrent;
        const delta = Math.abs(diff) < 0.1 ? 0 : diff * this.options.ease;

        if (delta) {
            this.scrollCurrent += delta;
            this.element.scrollTop = this.scrollCurrent;
            this.requestId = requestAnimationFrame(this.render.bind(this));
        } else {
            this.requestId = null;
        }
    }
}
/* 사용할 div 작성 */
// 좌석/인원
const scrollElement = document.querySelector('.movieDiv_movie');
new SmoothScroll(scrollElement);