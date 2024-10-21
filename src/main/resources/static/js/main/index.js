
/*<![CDATA[*/
document.addEventListener('DOMContentLoaded', function() {
    // var banners = /*[[${bannerList}]]*/ [];
    if (banners.length > 0) {
        var randomBanner = banners[Math.floor(Math.random() * banners.length)];
        var container = document.getElementById('bannerContainer');

        container.innerHTML = `
                <div class="video-container">
                    <video id="randomVideo" autoplay loop playsinline muted controls src="${randomBanner.bannerUrl}">
                    </video>
                    <div class="banner-content">
                        <h1 class="banner-title">${randomBanner.bannerTitle}</h1>
                        <p class="banner-subTitle">${randomBanner.bannerContent}</p>
                        <a href="${randomBanner.linkUrl}"><button class="linkBtn">상세보기<i class="fi fi-rr-angle-right linkR"></i></button></a>
                    </div>
                </div>
            `;
    }
});
/*]]>*/

// 무비차트
document.addEventListener('DOMContentLoaded', function() {
    const movieList = document.querySelector('.movie-list');
    const prevBtn = document.getElementById('prevBtn');
    const nextBtn = document.getElementById('nextBtn');
    const totalMovies = parseInt(movieList.getAttribute('data-total-movies'));
    const moviesPerPage = 5;
    let currentIndex = 0;

    function updateSlider() {
        const translateX = currentIndex * -245; // 220px (영화 너비) + 25px (마진)
        movieList.style.transform = `translateX(${translateX}px)`;
    }

    prevBtn.addEventListener('click', () => {
        if (currentIndex > 0) {
            currentIndex--;
            updateSlider();
        }
    });

    nextBtn.addEventListener('click', () => {
        if (currentIndex < totalMovies - moviesPerPage) {
            currentIndex++;
            updateSlider();
        }
    });
});


document.addEventListener('DOMContentLoaded', function() {
    const wrapper = document.querySelector('.chat-list-card-wrapper');
    const prevButton = document.querySelector('.slider-prev');
    const nextButton = document.querySelector('.slider-next');
    let currentIndex = 0;

    // 전체 카드 수 계산
    const totalCards = wrapper.children.length;
    const cardsPerPage = 6;
    const totalPages = Math.ceil(totalCards / cardsPerPage);

    // 초기 설정: 6개만 보이도록
    function initializeSlider() {
        const cardWidth = wrapper.children[0].offsetWidth;
        const cardHeight = wrapper.children[0].offsetHeight;
        wrapper.style.width = `${cardWidth * 3}px`;
        wrapper.style.height = `${cardHeight * 2}px`;
        wrapper.style.display = 'grid';
        wrapper.style.gridTemplateColumns = 'repeat(3, 1fr)';
        wrapper.style.gridTemplateRows = 'repeat(2, 1fr)';
        updateVisibility();
    }

    // 카드 표시/숨김 업데이트
    function updateVisibility() {
        Array.from(wrapper.children).forEach((card, index) => {
            if (index >= currentIndex * cardsPerPage && index < (currentIndex + 1) * cardsPerPage) {
                card.style.display = 'block';
            } else {
                card.style.display = 'none';
            }
        });
    }

    // 슬라이드 이동 함수
    function moveSlide(direction) {
        if (direction === 'next' && currentIndex < totalPages - 1) {
            currentIndex++;
        } else if (direction === 'prev' && currentIndex > 0) {
            currentIndex--;
        }
        updateVisibility();
        updateButtonState();
    }

    // 버튼 이벤트 리스너
    nextButton.addEventListener('click', () => moveSlide('next'));
    prevButton.addEventListener('click', () => moveSlide('prev'));

    // 버튼 상태 업데이트
    function updateButtonState() {
        prevButton.disabled = currentIndex === 0;
        nextButton.disabled = currentIndex === totalPages - 1;
    }

    initializeSlider();
    updateButtonState();
});

function goMovieList() {
        location.href = "/movie-list"
}
function goChatList() {
        location.href = "/chat/list"
}



