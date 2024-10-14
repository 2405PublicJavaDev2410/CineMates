// 영화 정보 DOM 구성
const OneResultInfo = (rsv) => {
    const rsvContents = `
        <img id="thumbnail-area" src="${rsv.posterUrl}" alt="thumbnail">
        <table id="info-table">
            <colgroup>
                <col style="width: 80px">
                <col style="width: 180px">
                <col style="width: 100px">
                <col style="width: 200px">
            </colgroup>
            <tbody>
                <tr>
                    <th scope="row">영화명</th>
                    <td colspan="3"><span>${rsv.title}</span></td>
                </tr>
                <tr>
                    <th scope="row">예매번호</th>
                    <td><span>${rsv.reservationNo}</span></td>
                    <th>관람인원</th>
                    <td><span>${rsv.reservationPeople}</span></td>
                </tr>
                <tr>
                    <th scope="row">예매일시</th>
                    <td><span>${rsv.paymentDate}</span></td>
                    <th scope="row">극장/상영관</th>
                    <td><span>${rsv.cinemaName}</span></td>
                </tr>
                <tr>
                    <th scope="row">관람일시</th>
                    <td><span>${rsv.reservationDateTime}</span></td>
                    <th>관람좌석</th>
                    <td><span>${rsv.reservationSeat}</span></td>
                </tr>
            </tbody>
        </table>
    `;
    document.querySelector('#movie-info').innerHTML = rsvContents;
}

// 결과 없음 DOM 구성
const NoResultInfo = () => {
    const rsvContents = `
        <b><p id="error-text">해당 예매번호에 대한 영화 정보를 찾을 수 없습니다.</p></b>
    `;
    document.querySelector('#error-message').innerHTML = rsvContents;
}

// 데이터가 있으면 영화정보 출력, 없으면 오류 메시지 출력
function findReservation() {
    const reservationNo = document.querySelector('#reservation-no').value;
    $.ajax({
        url: `/find-reservation?reservationNo=${reservationNo}`,
        method: 'GET',
        success: function (data) {
            if(data) {
                OneResultInfo(data);
                document.querySelector('#error-message').style.display = 'none';
                document.querySelector('#movie-info').style.display = 'flex';
            }else {
                NoResultInfo();
                console.log('예매 정보 없음');
                document.querySelector('#movie-info').style.display = 'none';
                document.querySelector('#error-message').style.display = 'block';
            }
        },
        error: function () {
            alert('서버 통신 에러!');
        }
    })

}

// 조회 버튼 클릭 시
document.getElementById('reserve-search-btn').addEventListener('click', findReservation);
document.getElementById('reservation-no').addEventListener("keydown", function (e) {
    if (e.key === 'Enter') {
        e.preventDefault();
        findReservation();
    }
});

document.addEventListener("DOMContentLoaded", function() {
    document.querySelector('#movie-info').style.display = 'none';
    document.querySelector('#error-message').style.display = 'none';
    document.querySelector('#side-menu-1').classList.add('selected');
})