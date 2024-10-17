$(document).ready(function () {
    // 지역에 따른 극장 출력
    $('#region-list').on('click', 'a', function (e) {
        e.preventDefault();
        var Info = $(this).data('cinema-address');
        //선택된 지역에 selected 클래스 추가
        $(this).parent().addClass('selected').siblings().removeClass('selected');

        $.ajax({
            url: '/getCinemas',
            method: 'GET',
            data: {cinemaAddress: Info},
            success: function (cinemas) {
                var cinema = $('#cinema-List');
                cinema.empty();

                $.each(cinemas, function (index, cinemaName) {
                    cinema.append('<li><a href="#" data-cinema-name="' + cinemaName + '">' + cinemaName + '</a></li>');
                });
                console.log("잘 전달 됨");
            },
            error: function (xhr, status, error) {
                console.error("AJAX 요청 실패: ", error);
            }
        });
    });
});

/* 달력 */
const calendarDays = document.getElementById('calendar-days');
const prevWeek = document.getElementById('prevWeek');
const nextWeek = document.getElementById('nextWeek');
const monthDisplay = document.getElementById('month-display');
let currentDate = new Date();

const weekdays = ['월', '화', '수', '목', '금', '토', '일'];
const months = ['1월', '2월', '3월', '4월', '5월', '6월', '7월', '8월', '9월', '10월', '11월', '12월'];

function getMonday(d) {
    d = new Date(d);
    var day = d.getDay(),
        diff = d.getDate() - day + (day == 0 ? -6 : 1); // adjust when day is sunday
    return new Date(d.setDate(diff));
}

function updateCalendar() {
    calendarDays.innerHTML = '';
    const startDate = getMonday(currentDate);

    let weekDates = [];
    for (let i = 0; i < 7; i++) {
        const date = new Date(startDate);
        date.setDate(startDate.getDate() + i);
        weekDates.push(date);
    }

    // 초기 월 표시 (주의 중간 날짜 기준)
    let middleDate = weekDates[3];
    monthDisplay.textContent = months[middleDate.getMonth()];

    weekDates.forEach((date, index) => {
        const dayElement = document.createElement('div');
        dayElement.className = 'day';
        dayElement.setAttribute('data-month', date.getMonth());
        if (date.toDateString() === new Date().toDateString()) {
            dayElement.classList.add('active');
        }

        if (index === 6) {
            dayElement.classList.add('weekend');
        }
        else if(index === 5){
            dayElement.classList.add('saturday')
        }
        const weekdayElement = document.createElement('div');
        weekdayElement.className = 'weekday';
        weekdayElement.textContent = weekdays[index];

        const dateElement = document.createElement('div');
        dateElement.className = 'date';
        dateElement.textContent = date.getDate();

        dayElement.appendChild(weekdayElement);
        dayElement.appendChild(dateElement);
        calendarDays.appendChild(dayElement);

        dayElement.addEventListener('click', function () {
            document.querySelectorAll('.day').forEach(day => day.classList.remove('active'));
            this.classList.add('active');
            monthDisplay.textContent = months[parseInt(this.getAttribute('data-month'))];
        });
    });
}

prevWeek.addEventListener('click', () => {
    currentDate.setDate(currentDate.getDate() - 7);
    updateCalendar();
});

nextWeek.addEventListener('click', () => {
    currentDate.setDate(currentDate.getDate() + 7);
    updateCalendar();
});

updateCalendar();
/* - 달력 END -*/

//영화 상영 가능 시간 출력
$(document).ready(function () {
    var selectedMovieNo = $('#selectedMovieNo').val();
    var selectedMovieTitle = $('#selectedMovieTitle').val();

    // 영화 리스트 페이지에서 선택된 영화 처리
    if (selectedMovieNo && selectedMovieTitle) {
        var $movieLink = $('#movie-List a[data-movie-no="' + selectedMovieNo + '"]');
        if ($movieLink.length) {
            $movieLink.closest('li').addClass('selected').siblings().removeClass('selected');
            $('#title').val(selectedMovieTitle);
            $('#movieNo').val(selectedMovieNo);
            checkAndGetShowtimes();
        }
    }

    // 기존의 영화 선택 이벤트 리스너
    $('#movie-List').on('click', 'a', function (e) {
        e.preventDefault();
        var $this = $(this);
        var title = $this.data('title');
        var movieNo = $this.data('movie-no');

        $this.closest('li').addClass('selected').siblings().removeClass('selected');

        $('#title').val(title);
        $('#movieNo').val(movieNo);
        $('#selectedMovieNo').val(movieNo);
        $('#selectedMovieTitle').val(title);

        checkAndGetShowtimes();
    });

    document.addEventListener('DOMContentLoaded', function () {
        const movieList = document.getElementById('movie-List');
        const selectedMovieNo = '[[${selectedMovieNo}]]'; // Thymeleaf를 사용하여 서버에서 전달된 값

        // 이미 선택된 영화가 있다면 하이라이트
        if (selectedMovieNo) {
            const selectedMovie = movieList.querySelector(`[data-movie-no="${selectedMovieNo}"]`);
            if (selectedMovie) {
                selectedMovie.classList.add('selected');
            }
        }

        // 영화 선택 이벤트 리스너
        movieList.addEventListener('click', function (e) {
            if (e.target.tagName === 'A') {
                e.preventDefault();

                // 이전에 선택된 영화의 하이라이트 제거
                const previousSelected = movieList.querySelector('.selected');
                if (previousSelected) {
                    previousSelected.classList.remove('selected');
                }

                // 새로 선택된 영화 하이라이트
                e.target.classList.add('selected');

                // 선택된 영화 정보 저장 또는 다른 동작 수행
                const movieNo = e.target.getAttribute('data-movie-no');
                const title = e.target.getAttribute('data-title');
                console.log(`선택된 영화: ${title} (번호: ${movieNo})`);

                // 여기에 선택된 영화 정보를 사용하는 추가 로직 구현
                // 예: 다른 페이지로 이동하거나 추가 정보를 불러오는 등
            }
        });
    });
});

// 날짜 선택 이벤트 수정
$(document).on('click', '.day', function () {
    document.querySelectorAll('.day').forEach(day => day.classList.remove('active'));
    this.classList.add('active');

    var year = currentDate.getFullYear();
    var month = parseInt(this.getAttribute('data-month')) + 1; // 0-based to 1-based
    var day = parseInt(this.querySelector('.date').textContent);

    // 날짜 문자열을 직접 생성
    var dateString = `${year}-${String(month).padStart(2, '0')}-${String(day).padStart(2, '0')}`;

    console.log('Selected date string:', dateString);

    $('#selectedDate').val(dateString);
    selectedDate = new Date(year, month - 1, day); // JavaScript 내부 사용을 위한 Date 객체

    monthDisplay.textContent = months[month - 1]; // 월 표시 업데이트

    checkAndGetShowtimes();
});
//상영시간 반영
function checkAndGetShowtimes() {
    if ($('#title').val() && $('#selectedDate').val() && $('#cinemaName').val()) {
        getShowtimes($('#title').val(), $('#selectedDate').val(), $('#cinemaName').val());
    }
}

function getShowtimes(movie, date) {
    $.ajax({
        url: '/getShowtimes',
        method: 'GET',
        data: {
            reservationDate: date,
            title: movie,
            cinemaName: $('#cinema-List .selected a').data('cinema-name')
        },
        success: function (response) {
            if (response && response.showInfoList && Array.isArray(response.showInfoList)) {
                displayShowtimes(response.showInfoList, date);
                $('input[name="screenNo"]').val(response.showInfoList[0].screenNo);
                $('input[name="movieNo"]').val(response.showInfoList[0].movieNo);
                $('input[name="cinemaNo"]').val(response.showInfoList[0].cinemaNo);
                $('input[name="showtimeNo"]').val(response.showInfoList[0].showtimeNo);

                // 예약된 좌석 정보를 저장
                $('input[name="reservationSeat"]').val(JSON.stringify(response.reservationSeat));
            } else {
                console.error("Invalid response format:", response);
                $('#showtimes-container').html('<p>상영 시간 정보를 불러오는데 실패했습니다.</p>');
            }
        },
        error: function (xhr, status, error) {
            console.error("상영 시간 로드 실패:", error);
            $('#showtimes-container').html('<p>상영 시간 정보를 불러오는데 실패했습니다.</p>');
        }
    });
}

function displayShowtimes(showInfoList, selectedDate) {
    if (showInfoList.length === 0) {
        $('#showtimes-container').html('<p>해당 날짜에 상영 정보가 없습니다.</p>');
        return;
    }

    function getCurrentTime() {
        const now = new Date();
        return now.getHours() * 60 + now.getMinutes();
    }

    function convertToMinutes(timeString) {
        const [hours, minutes] = timeString.split(':').map(Number);
        return hours * 60 + minutes;
    }

    const currentDate = new Date().toISOString().split('T')[0];
    const currentTimeInMinutes = getCurrentTime();
    const isToday = selectedDate === currentDate;

    // 상영관별로 상영시간 그룹화
    const groupedShowtimes = showInfoList.reduce((acc, info) => {
        if (!acc[info.screenName]) {
            acc[info.screenName] = [];
        }
        if (!isToday || convertToMinutes(info.showtimeTime) >= currentTimeInMinutes) {
            acc[info.screenName].push(info);
        }
        return acc;
    }, {});

    let showtimesList = $('<div id="showtimes-List"></div>');

    Object.entries(groupedShowtimes).forEach(([screenName, infos]) => {
        if (infos.length > 0) {
            let screenShowtimes = $(`
                <div class="screen-showtimes">
                    <h4>${screenName || '정보 없음'}</h4>
                    <div class="showtimes-row"></div>
                </div>
            `);

            infos.forEach((info) => {
                screenShowtimes.find('.showtimes-row').append(`
                    <button class="showtime-link">
                        <strong>${info.showtimeTime}</strong>
                        <dl>좌석: ${info.availableSeats}/${info.screenSeat || '정보 없음'}</dl>
                    </button>
                `);
            });

            showtimesList.append(screenShowtimes);
        }
    });

    if (showtimesList.children().length === 0) {
        showtimesList.append('<p>해당 날짜에 상영 일정이 없습니다.</p>');
    }

    $('#showtimes-container').empty().append(showtimesList);

    // 상영 시간 선택 이벤트 리스너
    $('.showtime-link').on('click', function (e) {
        e.preventDefault();
        $('.showtime-link').removeClass('selected');
        $(this).addClass('selected');
        $('#showtimeTime').val($(this).find('strong').text());
        $('#screenName').val($(this).closest('.screen-showtimes').find('h4').text());
    });
}

// 날짜 선택 이벤트 리스너
$('#calendar-days').on('click', '.day', function () {
    const selectedDate = $(this).data('date');
    $('#selectedDate').val(selectedDate);
    checkAndGetShowtimes();
});


$('form').on('submit', function (e) {
    if (!$('#cinemaName').val() || !$('#title').val() ||
        !$('#selectedDate').val() || !$('#showtimeTime').val() || !$('#screenName').val()) {
        e.preventDefault();
        alert('모든 항목을 선택해주세요.');
    }
});
//
// 극장에 따른 영화 목록 출력
$('#cinema-List').on('click', 'a', function (e) {
    e.preventDefault();
    var cinemaName = $(this).data('cinema-name');
    $(this).parent().addClass('selected').siblings().removeClass('selected');
    $('#cinemaName').val(cinemaName);
    checkAndGetShowtimes();
});
//영화 제목에 따른 svg 삽입
document.addEventListener('DOMContentLoaded', function () {
    var movieItems = document.querySelectorAll('.movie-item');

    movieItems.forEach(function (item) {
        var titleElement = item.querySelector('#movie-title');
        var ageElement = item.querySelector('.movie-age');
        var title = titleElement.textContent;
        var ageRating = ageRatings[title];

        if (ageRating) {
            var svgPath = getSvgPathForAgeRating(ageRating);
            var img = document.createElement('img');
            img.src = svgPath;
            img.alt = ageRating + " 등급";
            ageElement.appendChild(img);
        }
    });

    function getSvgPathForAgeRating(ageRating) {
        switch (ageRating) {
            case "12":
                return "/img/reservation/only12.svg";
            case "15":
                return "/img/reservation/only15.svg";
            case "19":
                return "/img/reservation/only19.svg";
            default:
                return "/img/reservation/all.svg";
        }
    }
});
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

// 사용 예
const scrollElement = document.querySelector('.movieDiv_movie');
new SmoothScroll(scrollElement);

//
//         $.ajax({
//             url: '/getMovies',
//             method: 'GET',
//             data: {cinemaName: cinemaName},
//             success: function (response) {
//                 var movie = $('#movie-List');
//                 movie.empty();
//
//                 $.each(response, function (index, item) {
//                     movie.append('<li><a href="#none">' + item + '</a></li>');
//                 });
//                 console.log("잘 전달 됨");
//             },
//             error: function (xhr, status, error) {
//                 console.error("AJAX 요청 실패: ", error);
//             }
//         });
//     });
//
//     // 영화 선택 시 selected 클래스 추가
//     $('#movie-List').on('click', 'a', function (e) {
//         e.preventDefault();
//         var title = $(this).text();
//         $(this).parent().addClass('selected').siblings().removeClass('selected');
//         $('#title').val(title);
//         selectedMovie = title;
//
//     });
// });
//
//