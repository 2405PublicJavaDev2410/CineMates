const movieInfoDiv = document.getElementById('movie-info');
const errorMessage = document.getElementById('error-message');

// 페이지가 처음 로드될 때 영화정보, 오류 메시지 숨김
document.addEventListener("DOMContentLoaded", function() {
    movieInfoDiv.style.display= 'none';
    errorMessage.style.display= 'none';
});

// 데이터가 있으면 영화정보 출력, 없으면 오류 메시지 출력
function searchMovie() {
    const reserveNumber = document.getElementById('reserve-number').value;
    // 예시 예매번호에 따른 영화 정보를 저장한 객체 (실제 앱에서는 서버 요청으로 대체됨)
    const reserveData = {
        '12345': {
            reserveNo: '12345',
            title: '인셉션',
            reserveDate: '2024-09-20',
            showTime: '2024-09-25 14:00',
            reserveScreen: '동대문/7관',
            visitor: '성인 2명',
            price: '24,000원',
            seat: 'A12, A13'
        }
    }
    if (reserveData[reserveNumber]) {
        document.getElementById('rsv-no').textContent = reserveData[reserveNumber].reserveNo;
        document.getElementById('rsv-title').textContent = reserveData[reserveNumber].title;
        document.getElementById('rsv-date').textContent = reserveData[reserveNumber].reserveDate;
        document.getElementById('show-date').textContent = reserveData[reserveNumber].showTime;
        document.getElementById('rsv-screen').textContent = reserveData[reserveNumber].reserveScreen;
        document.getElementById('rsv-visitor').textContent = reserveData[reserveNumber].visitor;
        document.getElementById('rsv-amount').textContent = reserveData[reserveNumber].price;
        document.getElementById('rsv-seat').textContent = reserveData[reserveNumber].seat;

        // 영화 정보가 있으 경우, 오류 메시지를 숨기고 영화 정보를 보여줌
        movieInfoDiv.style.display= 'flex';
        errorMessage.style.display= 'none';
    } else {
        // 영화 정보가 없을 경우, 영화 정보를 숨기고 오류 메시지만 보여줌
        movieInfoDiv.style.display= 'none';
        errorMessage.style.display= 'block';
    }
}

// 조회 버튼 클릭 시
document.getElementById('reserve-search-btn').addEventListener('click', searchMovie);
document.getElementById('reserve-number').addEventListener("keydown", function (e) {
    if (e.key === 'Enter') {
        searchMovie();
    }
});