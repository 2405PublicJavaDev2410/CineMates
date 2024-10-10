// 영화 리스트
// document.addEventListener('DOMContentLoaded', function() {
//     const nowShowingBtn = document.querySelector('#nowShowing');
//     const comingSoonBtn = document.querySelector('#commingSoon');
//     const movieListForm = document.querySelector('#movie-list'); // 수정: form 요소 선택
//     const sortReservationBtn = document.querySelector('#sort-reservation');
//     const sortReviewBtn = document.querySelector('#sort-review');
//     const sortReleaseDateBtn = document.querySelector('#sort-releaseDate');
//     // 페이지 로드 시 기본적으로 현재 상영작 표시
//     fetchMovies('NOW SHOWING');
//
//     nowShowingBtn.addEventListener('click', function() {
//         fetchMovies('NOW SHOWING');
//     });
//
//     comingSoonBtn.addEventListener('click', function() {
//         fetchMovies('COMING SOON');
//     });
//
//     function fetchMovies(status) {
//         $.ajax({
//             url: '/movie-list',
//             method: 'GET',
//             data: { status: status },
//             headers: {
//                 'X-Requested-With': 'XMLHttpRequest'
//             },
//             success: function(data) {
//                 updateMovieList(data);
//                 updateButtonStatus(status);
//             },
//             error: function(xhr, status, error) {
//                 console.error('Error:', error);
//             }
//         });
//     }
//
//     function updateMovieList(movies) {
//         movieListForm.innerHTML = ''; // form 내용 초기화
//         movies.forEach(movie => {
//             const movieElement = createMovieElement(movie);
//             movieListForm.appendChild(movieElement); // form에 영화 요소 추가
//         });
//     }
//
//     function createMovieElement(movie) {
//         const movieDiv = document.createElement('div');
//         movieDiv.className = 'movie-poster';
//         movieDiv.innerHTML = `
//             <div class="poster-image">
//                 <img src="${movie.posterUrl || '/img/movie/imageNull.png'}" alt="${movie.title}" />
//             </div>
//             <div class="movie-info">
//                 <div class="title-container">
//                     <img class="age-rating" src="/img/chat/${movie.rating}.jpg" alt="${movie.rating === 'ALL' ? '전체관람가' : movie.rating + '세 이상관람가'}" />
//                     <div class="movie-title">${movie.title}</div>
//                 </div>
//                 <div class="movie-details">
//                     <span class="booking-rate">예매율 ${movie.reservationRate}</span>
//                     <span class="bar">|</span>
//                     <span>${movie.releaseDate ? movie.releaseDate + ' 개봉' : '개봉일 미정'}</span>
//                 </div>
//                 <button class="booking-button">예매</button>
//             </div>
//         `;
//         return movieDiv;
//     }
//
//     function updateButtonStatus(status) {
//         if (status === 'NOW SHOWING') {
//             nowShowingBtn.classList.add('active');
//             comingSoonBtn.classList.remove('active');
//             sortReleaseDateBtn.classList.add('hide');
//             sortReviewBtn.classList.remove('hide');
//         } else {
//             comingSoonBtn.classList.add('active');
//             nowShowingBtn.classList.remove('active');
//             sortReviewBtn.classList.add('hide');
//             sortReleaseDateBtn.classList.remove('hide');
//         }
//     }
//
// });


document.addEventListener('DOMContentLoaded', function() {
    const movieList = document.getElementById('movie-list');
    const loadMoreBtn = document.createElement('button');
    loadMoreBtn.textContent = '더보기';
    loadMoreBtn.id = 'loadMoreBtn';
    loadMoreBtn.style.display = 'none'; // 초기에는 숨김
    document.body.appendChild(loadMoreBtn);

    let currentPage = 0;
    const pageSize = 20;
    let currentStatus = 'NOW SHOWING';

    function fetchMovies(status, page) {
        $.ajax({
            url: '/movie-list',
            method: 'GET',
            data: {
                status: status,
                page: page,
                size: pageSize
            },
            success: function(data) {
                if (page === 0) {
                    movieList.innerHTML = ''; // 첫 페이지일 경우 리스트 초기화
                }
                appendMovies(data);
                if (data.length === pageSize) {
                    loadMoreBtn.style.display = 'block'; // 더 볼 데이터가 있으면 버튼 표시
                } else {
                    loadMoreBtn.style.display = 'none'; // 더 이상 데이터가 없으면 버튼 숨김
                }
            },
            error: function(xhr, status, error) {
                console.error('Error:', error);
            }
        });
    }

    function appendMovies(movies) {
        movies.forEach(movie => {
            const movieElement = createMovieElement(movie);
            movieList.appendChild(movieElement);
        });
    }

    function createMovieElement(movie) {
        const movieDiv = document.createElement('div');
        movieDiv.className = 'movie-poster';
        movieDiv.innerHTML = `
            <div class="poster-image">
                <img src="${movie.posterUrl || '/img/movie/imageNull.png'}" alt="${movie.title}" />
            </div>
            <div class="movie-info">
                <div class="title-container">
                    <img class="age-rating" src="/img/chat/${movie.rating}.jpg" alt="${movie.rating === 'ALL' ? '전체관람가' : movie.rating + '세 이상관람가'}" />
                    <div class="movie-title">${movie.title}</div>
                </div>
                <div class="movie-details">
                    <span class="booking-rate">예매율 ${movie.reservationRate}</span>
                    <span class="bar">|</span>
                    <span>${movie.releaseDate ? movie.releaseDate + ' 개봉' : '개봉일 미정'}</span>
                </div>
                <button class="booking-button">예매</button>
            </div>
        `;
        return movieDiv;
    }

    loadMoreBtn.addEventListener('click', function() {
        currentPage++;
        fetchMovies(currentStatus, currentPage);
    });

    // 초기 로드
    fetchMovies(currentStatus, currentPage);

    // 상영 상태 변경 버튼 이벤트 리스너
    document.getElementById('nowShowing').addEventListener('click', function() {
        currentStatus = 'NOW SHOWING';
        currentPage = 0;
        fetchMovies(currentStatus, currentPage);
    });

    document.getElementById('commingSoon').addEventListener('click', function() {
        currentStatus = 'COMING SOON';
        currentPage = 0;
        fetchMovies(currentStatus, currentPage);
    });
});









    // 배너
    let currentBanner = 0;
    const banners = document.querySelectorAll('.banner');
    const prevBtn = document.querySelector('.banner-nav.prev');
    const nextBtn = document.querySelector('.banner-nav.next');
    const dotsContainer = document.querySelector('.banner-dots');

    function showBanner(index) {
    banners[currentBanner].classList.remove('active');
    banners[index].classList.add('active');
    updateDots(index);
    currentBanner = index;
}

    function showNextBanner() {
    showBanner((currentBanner + 1) % banners.length);
}

    function showPrevBanner() {
    showBanner((currentBanner - 1 + banners.length) % banners.length);
}

    function updateDots(index) {
    const dots = document.querySelectorAll('.banner-dot');
    dots.forEach((dot, i) => {
    dot.classList.toggle('active', i === index);
});
}

    // 배너 점 생성
    banners.forEach((_, i) => {
    const dot = document.createElement('div');
    dot.classList.add('banner-dot');
    if (i === 0) dot.classList.add('active');
    dot.addEventListener('click', () => showBanner(i));
    dotsContainer.appendChild(dot);
});
    prevBtn.addEventListener('click', showPrevBanner);
    nextBtn.addEventListener('click', showNextBanner);

    // 5초마다 배너 변경
    setInterval(showNextBanner, 5000);


