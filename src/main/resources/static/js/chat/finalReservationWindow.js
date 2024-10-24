let datePicker = document.querySelector('#datePicker');
// 현재 날짜 구하기
const today = new Date();

// 내일 날짜 구하기
const tomorrow = new Date();
tomorrow.setDate(today.getDate() + 1);

// 한달 후 날짜 구하기
const twoWeeksFromNow = new Date();
twoWeeksFromNow.setDate(today.getDate() + 30);

// 날짜를 YYYY-MM-DD 형식으로 변환
const formatDate = (date) => {
    return date.toISOString().split('T')[0];
}

// min 및 max 속성 설정
document.getElementById("datePicker").setAttribute("min", formatDate(tomorrow));
document.getElementById("datePicker").setAttribute("max", formatDate(twoWeeksFromNow));


// 상영관 선택시 부모창에 입력값 전달됨
function selectShowtime(div) {
    // child window document
    let date = document.querySelector('#datePicker');
    let screenNo = div.children[0].value;
    let screenName = div.children[1].value;
    let showtimeNo = div.children[2].value;
    let showtimeTime = div.children[3].value;

    // parent window document
    let reservationDateInput = opener.document.querySelector('input[name=reservationDate]');
    let screenNoInput = opener.document.querySelector('input[name=screenNo]');
    let showtimeNoInput = opener.document.querySelector('input[name=showtimeNo]');
    let showtimeTimeInput = opener.document.querySelector('input[name=showtimeTime]');
    let screenNameInput = opener.document.querySelector('input[name=screenName]');

    let reservationDateText = opener.document.querySelector('#reservationDate');
    let showtimeTimeText = opener.document.querySelector('#showtimeTime');
    let screenNameText = opener.document.querySelector('#screenName');

    if (confirm(`${screenName}의 ${showtimeTime}으로 결정하시겠습니까?`)) {
        reservationDateInput.value = date.value;
        screenNoInput.value = screenNo;
        showtimeNoInput.value = showtimeNo;
        showtimeTimeInput.value = showtimeTime;
        screenNameInput.value = screenName;

        reservationDateText.innerText = date.value;
        showtimeTimeText.innerText = showtimeTime;
        screenNameText.innerText = screenName;


        window.close();
    }
}


let selectedBtn = document.querySelector('#selectedBtn');


selectedBtn.addEventListener('click', () => {
    // child window document

    // parent window document
    let reservationDateInput = opener.document.querySelector('input[name=reservationDate]');
    let screenNoInput = opener.document.querySelector('input[name=screenNo]');
    let showtimeNoInput = opener.document.querySelector('input[name=showtimeNo]');
    let showtimeTimeInput = opener.document.querySelector('input[name=showtimeTime]');
    let screenNameInput = opener.document.querySelector('input[name=screenName]');

    let reservationDateText = opener.document.querySelector('#reservationDate');
    let showtimeTimeText = opener.document.querySelector('#showtimeTime');
    let screenNameText = opener.document.querySelector('#screenName');

    if (confirm("선택한 내용을 초기화하시겠습니까?")) {
        reservationDateInput.value = "";
        screenNoInput.value = "";
        showtimeNoInput.value = "";
        showtimeTimeInput.value = "";
        screenNameInput.value = "";
        opener.document.querySelector('input[name=reservationSeat]').value = "";

        reservationDateText.innerText = "";
        showtimeTimeText.innerText = "";
        screenNameText.innerText = "";


        window.close();
    }

    window.close();
})

datePicker.addEventListener('change', (e) => {
    let selectedDate = e.target.value;



    $.ajax({
        url: "/chat/selectScreenByCinema",
        data: {
            selectedDate: selectedDate,
            movieNo: movieNo,
            cinemaNo: cinemaNo
        },
        type: "post",
        success: function (response) {
            $('#screenList-container').replaceWith(response);
            getShowtimes(selectedDate);
            console.log(response);
        },
        error: function () {

        }
    })
});


function getShowtimes(date) {

    $.ajax({
        url: '/getShowtimes',
        method: 'GET',
        data: {
            reservationDate: date,
            title: title,
            cinemaName: cinemaName
        },
        success: function (response) {
            if (response && response.showInfoList && Array.isArray(response.showInfoList)) {
                // displayShowtimes(response.showInfoList, date);
                // 예약된 좌석 정보를 저장
                opener.document.querySelector('input[name=reservationSeat]').value = JSON.stringify(response.reservationSeat);
                console.log(opener.document.querySelector('input[name=reservationSeat]'))
            }
        },
        error: function () {

        }
    });
}