// 영화 상태, 정렬에 따른 리스트

document.addEventListener('DOMContentLoaded', function() {
    const movieList = document.querySelector('#movie-list');
    // const loadMoreBtn = document.querySelector('#loadMoreBtn');
    const nowShowingBtn = document.querySelector('#nowShowing');
    const comingSoonBtn = document.querySelector('#commingSoon');
    const sortReservationBtn = document.querySelector('#sort-reservation');
    const sortReviewBtn = document.querySelector('#sort-review');
    const sortReleaseDateBtn = document.querySelector('#sort-releaseDate');

    const loadMoreContainer = document.querySelector('#load-more-container');

    let currentPage = 0;
    const pageSize = 20;
    let currentStatus = 'NOW SHOWING';
    let currentSortBy = 'reservationRate';
    let hasMoreMovies = true;

    function fetchMovies(status, page, sortBy, append = false) {
        $.ajax({
            url: '/movie-list',
            method: 'GET',
            data: {
                status: status,
                page: page,
                size: pageSize,
                sortBy: sortBy
            },
            success: function(data) {
                updateButtonStatus(status, sortBy);
                if (!append) {
                    movieList.innerHTML = '';
                }
                if (data.length > 0) {
                    appendMovies(data);
                    hasMoreMovies = data.length === pageSize;
                    // loadMoreBtn.style.display = hasMoreMovies ? 'block' : 'none';
                    loadMoreContainer.style.display = hasMoreMovies ? 'flex' : 'none';
                } else {
                    hasMoreMovies = false;
                    // loadMoreBtn.style.display = 'none';
                    loadMoreContainer.style.display = 'none';
                }
            },
            error: function(xhr, status, error) {
                console.error('Error:', error);
            }
        });
    }

    function updateButtonStatus(status, sortBy) {
        if (status === 'NOW SHOWING') {
            nowShowingBtn.classList.add('active');
            comingSoonBtn.classList.remove('active');
            sortReleaseDateBtn.classList.add('hide');
            sortReviewBtn.classList.remove('hide');
            sortReservationBtn.classList.remove('hide');
        } else {
            comingSoonBtn.classList.add('active');
            nowShowingBtn.classList.remove('active');
            sortReviewBtn.classList.add('hide');
            sortReleaseDateBtn.classList.remove('hide');
            sortReservationBtn.classList.add('active');
        }

        sortReservationBtn.classList.toggle('active', sortBy === 'reservationRate');
        sortReviewBtn.classList.toggle('active', sortBy === 'reviewCount');
        sortReleaseDateBtn.classList.toggle('active', sortBy === 'releaseDate');
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
                <a href="/movie-detail/${movie.movieNo}">
                    <img src="${movie.posterUrl || '/img/movie/imageNull.png'}" alt="${movie.title}"/>
                </a>
            </div>
            <div class="movie-info">
                <div class="title-container">
                    <img class="age-rating" src="/img/chat/${movie.rating}.jpg" alt="${movie.rating === 'ALL' ? '전체관람가' : movie.rating + '세 이상관람가'}" />
                    <a href="/movie-detail/${movie.movieNo}">
                        <div class="movie-title" >${movie.title}</div>
                    </a>
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
        if (hasMoreMovies) {
            currentPage++;
            fetchMovies(currentStatus, currentPage, currentSortBy, true);
        }
    });

    nowShowingBtn.addEventListener('click', function() {
        currentStatus = 'NOW SHOWING';
        currentPage = 0;
        currentSortBy = 'reservationRate';
        hasMoreMovies = true;
        fetchMovies(currentStatus, currentPage, currentSortBy);
    });

    comingSoonBtn.addEventListener('click', function() {
        currentStatus = 'COMING SOON';
        currentPage = 0;
        currentSortBy = 'releaseDate';
        hasMoreMovies = true;
        fetchMovies(currentStatus, currentPage, currentSortBy);
    });

    sortReservationBtn.addEventListener('click', function() {
        currentSortBy = 'reservationRate';
        currentPage = 0;
        fetchMovies(currentStatus, currentPage, currentSortBy);
    });

    sortReviewBtn.addEventListener('click', function() {
        currentSortBy = 'reviewCount';
        currentPage = 0;
        fetchMovies(currentStatus, currentPage, currentSortBy);
    });

    sortReleaseDateBtn.addEventListener('click', function() {
        currentSortBy = 'releaseDate';
        currentPage = 0;
        fetchMovies(currentStatus, currentPage, currentSortBy);
    });

    // 초기 로드
    fetchMovies(currentStatus, currentPage, currentSortBy);
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


