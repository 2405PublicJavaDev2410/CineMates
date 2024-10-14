document.addEventListener('DOMContentLoaded', () => {
    const tabButtons = document.querySelectorAll('.tab-button');
    const tabContents = document.querySelectorAll('.tab-content');

    tabButtons.forEach(button => {
        button.addEventListener('click', () => {
            const tabName = button.getAttribute('data-tab');

            tabContents.forEach(content => {
                content.classList.add('hidden');
            });
            document.getElementById(`${tabName}Tab`).classList.remove('hidden');

            tabButtons.forEach(btn => {
                btn.classList.remove('active');
            });
            button.classList.add('active');
        });
    });
});

document.addEventListener('DOMContentLoaded', () => {
    const trailerList = document.querySelector('.trailer-list');
    const trailerItems = document.querySelectorAll('.trailer-item');
    const prevTrailerBtn = document.getElementById('prevTrailer');
    const nextTrailerBtn = document.getElementById('nextTrailer');
    let currentTrailerIndex = 0;

    function updateTrailerSlide() {
        const itemWidth = trailerItems[0].offsetWidth;
        trailerList.style.transform = `translateX(-${currentTrailerIndex * itemWidth}px)`;

        // 버튼 표시 상태 업데이트
        prevTrailerBtn.style.display = currentTrailerIndex > 0 ? 'block' : 'none';
        nextTrailerBtn.style.display = currentTrailerIndex < trailerItems.length - 3 ? 'block' : 'none';
    }

    prevTrailerBtn.addEventListener('click', () => {
        if (currentTrailerIndex > 0) {
            currentTrailerIndex--;
            updateTrailerSlide();
        }
    });

    nextTrailerBtn.addEventListener('click', () => {
        if (currentTrailerIndex < trailerItems.length - 3) {
            currentTrailerIndex++;
            updateTrailerSlide();
        }
    });

    // 초기 상태 설정
    updateTrailerSlide();
});


// 리뷰 작성창
document.addEventListener('DOMContentLoaded', function() {
    const writeButton = document.querySelector('.review-write-button');
    const reviewForm = document.querySelector('.review-form');

    writeButton.addEventListener('click', function() {
        reviewForm.style.display = reviewForm.style.display === 'none' ? 'block' : 'none';
        writeButton.textContent = reviewForm.style.display === 'none' ? '리뷰 작성' : '작성 취소';
    });
});