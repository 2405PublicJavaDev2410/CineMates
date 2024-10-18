// 영화정보 관람평 이동 탭
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

//트레일러 슬라이드
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

// 리뷰 작성
document.addEventListener('DOMContentLoaded', function() {
    const reviewsTab = document.querySelector('#reviewsTab');

    // 이벤트 위임을 사용하여 동적으로 추가된 요소에도 이벤트 처리
    reviewsTab.addEventListener('click', function(e) {
        if (e.target.classList.contains('review-write-button')) {
            const reviewForm = document.querySelector('#reviewForm');
            reviewForm.style.display = reviewForm.style.display === 'none' ? 'block' : 'none';
            e.target.textContent = reviewForm.style.display === 'none' ? '리뷰 작성' : '작성 취소';
        }
    });

    // 폼 제출 이벤트 리스너
    document.addEventListener('submit', function(e) {
        if (e.target.id === 'reviewForm') {
            e.preventDefault();

            const formData = new FormData(e.target);
            const reviewData = Object.fromEntries(formData.entries());

            fetch('/addReview', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify(reviewData)
            })
                .then(response => response.json())
                .then(data => {
                    if (data.success) {
                        // 리뷰 등록 성공 시 리뷰 탭 내용을 새로고침
                        fetch(window.location.pathname + ' #reviewsTab')
                            .then(response => response.text())
                            .then(html => {
                                const parser = new DOMParser();
                                const doc = parser.parseFromString(html, 'text/html');
                                const newReviewsTab = doc.querySelector('#reviewsTab');
                                reviewsTab.innerHTML = newReviewsTab.innerHTML;
                            })
                            .catch(error => console.error('Error:', error));

                        // 폼 리셋 및 숨기기
                        e.target.reset();
                        e.target.style.display = 'none';
                        document.querySelector('.review-write-button').textContent = '리뷰 작성';

                        alert(data.message);
                    } else {
                        alert(data.message || '리뷰 등록에 실패했습니다. 다시 시도해주세요.');
                    }
                })
                .catch(error => {
                    console.error('Error:', error);
                    alert('리뷰 등록 중 오류가 발생했습니다.');
                });
        }
    });
});





// 리뷰 작성창
// document.addEventListener('DOMContentLoaded', function() {
//     const writeButton = document.querySelector('.review-write-button');
//     const reviewForm = document.querySelector('.review-form');
//
//     writeButton.addEventListener('click', function() {
//         // if (isLoggedIn) {
//             reviewForm.style.display = reviewForm.style.display === 'none' ? 'block' : 'none';
//             writeButton.textContent = reviewForm.style.display === 'none' ? '리뷰 작성' : '작성 취소';
//         // } else {
//         //     alert('리뷰를 작성하려면 로그인이 필요합니다.');
//             // window.location.href = '/login';
//         // }
//     });
// });


// 비디오 모달 팝업
document.addEventListener('DOMContentLoaded', function() {
    const modal = document.getElementById('videoModal');
    const modalVideo = document.getElementById('modalVideo');
    const closeBtn = document.getElementsByClassName('video-close')[0];
    const trailerThumbnails = document.querySelectorAll('.trailer-thumbnail');

    trailerThumbnails.forEach(thumbnail => {
        thumbnail.addEventListener('click', function() {
            const trailerUrl = this.getAttribute('data-trailer-url');
            modalVideo.src = trailerUrl;
            modal.style.display = 'block';
            modalVideo.play();
        });
    });

    closeBtn.onclick = function() {
        modal.style.display = 'none';
        modalVideo.pause();
        modalVideo.currentTime = 0;
    }

    window.onclick = function(event) {
        if (event.target == modal) {
            modal.style.display = 'none';
            modalVideo.pause();
            modalVideo.currentTime = 0;
        }
    }
})

// 포스터 모달
document.addEventListener('DOMContentLoaded', function() {
    const imageModal = document.getElementById('imageModal');
    const modalImage = document.getElementById('modalImage');
    const moviePoster = document.getElementById('moviePoster');
    const closeImageBtn = imageModal.querySelector('.close');

    // 포스터 클릭 시 모달 열기
    moviePoster.onclick = function() {
        imageModal.style.display = 'block';
        modalImage.src = this.src;
        modalImage.alt = this.alt;
    }

    // 닫기 버튼 클릭 시 모달 닫기
    closeImageBtn.onclick = function() {
        imageModal.style.display = 'none';
    }

    // 모달 외부 클릭 시 모달 닫기
    window.onclick = function(event) {
        if (event.target == imageModal) {
            imageModal.style.display = 'none';
        }
    }

    // 이미지 클릭 시 확대/축소 토글
    modalImage.onclick = function() {
        this.classList.toggle('zoomed');
    }
});

function moveToTicketing() { location.href="/Ticketing" }