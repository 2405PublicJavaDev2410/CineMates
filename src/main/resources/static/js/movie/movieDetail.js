// document.addEventListener('DOMContentLoaded', function() {
//     const stillcutContainer = document.querySelector("#stillcutContainer");
//     const loadMoreContainer = document.querySelector('#load-more-container');
//     const loadMoreBtn = document.querySelector('#loadMoreBtn');
//     const movieNoElement = document.querySelector('#movieNo');
//     const currentPageElement = document.querySelector('#currentPage');
//     const movieNo = movieNoElement ? movieNoElement.value : null;
//
//     let currentPage = parseInt(currentPageElement.value);
//     const pageSize = 5;
//
//     function fetchStillcuts() {
//         currentPage++;
//         currentPageElement.value = currentPage;
//
//         $.ajax({
//             url: `/movie-detail/${movieNo}`,
//             method: 'GET',
//             data: {
//                 page: currentPage,
//                 size: pageSize,
//                 isAjax: true
//             },
//             success: function (data) {
//                 const tempContainer = document.createElement('div');
//                 tempContainer.innerHTML = data;
//                 const newStillcuts = tempContainer.querySelector('#stillList').innerHTML;
//
//                 const stillList = document.querySelector('#stillList');
//                 stillList.insertAdjacentHTML('beforeend', newStillcuts);
//
//                 const hasMoreStillcuts = tempContainer.querySelector('#load-more-container') !== null;
//                 loadMoreContainer.style.display = hasMoreStillcuts ? 'flex' : 'none';
//             },
//             error: function (xhr, error) {
//                 console.error('ERROR:', error);
//             }
//         });
//     }
//
//     if (loadMoreBtn) {
//         loadMoreBtn.addEventListener('click', fetchStillcuts);
//     }
// });
//
//
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


// 리뷰 작성창
document.addEventListener('DOMContentLoaded', function() {
    const writeButton = document.querySelector('.review-write-button');
    const reviewForm = document.querySelector('.review-form');

    writeButton.addEventListener('click', function() {
        reviewForm.style.display = reviewForm.style.display === 'none' ? 'block' : 'none';
        writeButton.textContent = reviewForm.style.display === 'none' ? '리뷰 작성' : '작성 취소';
    });
});


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