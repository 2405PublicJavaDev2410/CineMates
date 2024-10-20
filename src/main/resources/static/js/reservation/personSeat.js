const countInput = document.getElementById('countInput');
const adult = document.getElementById('adultReserved');
const child = document.getElementById('childReserved');
const senior = document.getElementById('seniorReserved');
const selectedSeatsInput = document.getElementById('selectedSeatsInput');
const validMemberIds = Array.isArray(memberIds) ? memberIds : [];
const maxVisitorCount = validMemberIds.length !== 0 ? validMemberIds.length : Infinity;



// 나이 별 인원 선택
function count(value, resultClass) {
    const resultElement = document.querySelector('.' + resultClass);
    let currentCount = parseInt(resultElement.innerText);
    let targetInput;

    // 카테고리에 따른 hidden input 선택
    switch (resultClass) {
        case 'result':
            targetInput = adult;
            break;
        case 'result1':
            targetInput = child;
            break;
        case 'result2':
            targetInput = senior;
            break;
    }

    // 더하기/빼기
    if (value === "plus" && visitorCount < maxVisitorCount) {
        currentCount++;
        visitorCount++;
    } else if (value === "minus" && currentCount > 0) {
        currentCount--;
        visitorCount--;
    }


    // 현재 화면에 표시된 값 업데이트
    resultElement.innerText = currentCount;
    countInput.value = visitorCount;

    // hidden input 값 업데이트
    if (targetInput) {
        targetInput.value = currentCount;
    }
}
function updatePlusButtonStates() {
    const plusButtons = document.querySelectorAll('[onclick*="count(\'plus\'"]');
    plusButtons.forEach(button => {
        if (visitorCount >= maxVisitorCount) {
            button.disabled = true;
            button.style.opacity = '0.5';  // 선택적: 비활성화된 버튼의 스타일 변경
        } else {
            button.disabled = false;
            button.style.opacity = '1';
        }
    });
}


// 페이지 로드 시 초기 상태 설정
document.addEventListener('DOMContentLoaded', function() {
    updatePlusButtonStates();
    initializeSeatMap();
    updateTotalPrice();
});


const reservedSeats = reservedSeatsMap[rDTO.showtimeNo] || [];
console.log(reservedSeats);
const rows = 8; // A부터 H까지 8행
const seatsPerRow = 23; // 1부터 23까지 23열
const seats = Array(rows * seatsPerRow).fill(false);
let selectedSeats = [];

const ADULT_PRICE = 15000;
const CHILD_PRICE = 13000;
const SENIOR_PRICE = 7000;
function initializeSeatMap() {
    const seatMap = document.getElementById('seatMap');
    seatMap.innerHTML = '';

    for (let i = 0; i < rows; i++) {
        const rowLabel = String.fromCharCode(65 + i); // A부터 시작하는 행 라벨

        const rowDiv = document.createElement('div');
        rowDiv.className = 'row';

        const labelSpan = document.createElement('span');
        labelSpan.className = 'row-label';
        labelSpan.innerText = rowLabel;
        rowDiv.appendChild(labelSpan);

        for (let j = 0; j < seatsPerRow; j++) {
            const seatNumber = j + 1;
            const seatId = `${rowLabel}${seatNumber}`;
            const seat = document.createElement('a');
            seat.href = '#';
            seat.className = 'seat';
            seat.innerText = seatNumber;
            seat.setAttribute('data-seat', seatId);

            // 예약 불가능한 좌석 처리
            if ((i < 2 && j < 4) || (i < 9 && j > 17) || (j > 18)) {
                seat.classList.add('unavailable');
            } else if (reservedSeats.includes(seatId)) {
                seat.classList.add('reserved');
            } else {
                seat.onclick = (e) => {
                    e.preventDefault();
                    toggleSeat(i * seatsPerRow + j, seatId);
                };
            }

            rowDiv.appendChild(seat);
        }

        seatMap.appendChild(rowDiv);
    }
}

function toggleSeat(index, seatId) {
    const seatElement = document.querySelector(`[data-seat="${seatId}"]`);

    if (seatElement.classList.contains('reserved')) {
        alert("이미 예약된 좌석입니다.");
        return;
    }

    const totalSelectedCount = getTotalSelectedCount();

    if (selectedSeats.includes(seatId)) {
        selectedSeats = selectedSeats.filter(id => id !== seatId);
        seatElement.classList.remove('selected');
    } else if (selectedSeats.length < totalSelectedCount) {
        selectedSeats.push(seatId);
        seatElement.classList.add('selected');
    } else {
        alert(`선택한 인원 수만큼만 좌석을 선택할 수 있습니다.`);
        return;
    }
    updateSelectedSeatsInput();
    updateTotalPrice();
}

function updateSelectedSeatsInput() {
    const selectedSeatsString = selectedSeats.join(',');
    console.log("선택된 좌석:", selectedSeatsString);

    // hidden input 필드 업데이트
    document.getElementById('selectedSeatsInput').value = selectedSeatsString;

    // rDTO 객체 업데이트 (JavaScript에서 rDTO를 사용한다고 가정)
    if (typeof rDTO !== 'undefined') {
        rDTO.selectSeat = selectedSeatsString;
    }
}

function getTotalSelectedCount() {
    const adultCount = parseInt(document.querySelector('.result').innerHTML) || 0;
    const childCount = parseInt(document.querySelector('.result1').innerHTML) || 0;
    const seniorCount = parseInt(document.querySelector('.result2').innerHTML) || 0;
    return adultCount + childCount + seniorCount;
}

function updateTotalPrice() {
    const adultCount = parseInt(document.querySelector('.result').innerHTML) || 0;
    const childCount = parseInt(document.querySelector('.result1').innerHTML) || 0;
    const seniorCount = parseInt(document.querySelector('.result2').innerHTML) || 0;

    const totalPrice = (ADULT_PRICE * adultCount) + (CHILD_PRICE * childCount) + (SENIOR_PRICE * seniorCount);

    const totalPriceText = document.querySelector('.how-much-Pay Strong');
    totalPriceText.innerHTML = `총금액: ${totalPrice.toLocaleString()}원`;
    document.getElementById('totalPrice').value = totalPrice;

}

// 인원 선택 버튼에 이벤트 리스너 추가
document.querySelectorAll('.plus-button, .minus-button').forEach(button => {
    button.addEventListener('click', function() {
        setTimeout(updateTotalPrice, 0);  // DOM 업데이트 후 실행되도록 setTimeout 사용
    });
});

// 예약하기 버튼 이벤트 리스너
document.getElementById('reserve-button').addEventListener('click', function(e) {
    e.preventDefault();
    const totalSelectedCount = getTotalSelectedCount();
    if (selectedSeats.length !== totalSelectedCount) {
        alert('선택한 인원 수만큼 좌석을 선택해주세요.');
        return;
    }

    // 선택된 좌석 정보를 hidden input에 설정
    document.getElementById('selectedSeatsInput').value = selectedSeats.join(',');

    // 성인, 아동, 노인 예약 수 업데이트
    document.getElementById('adultReserved').value = document.querySelector('.result').innerHTML;
    document.getElementById('childReserved').value = document.querySelector('.result1').innerHTML;
    document.getElementById('seniorReserved').value = document.querySelector('.result2').innerHTML;

    // 총 예약 인원 업데이트
    document.getElementById('countInput').value = totalSelectedCount;

    console.log('예약 처리:', selectedSeats);

    // 폼 제출
    document.querySelector('form').submit();
});

function setMessage(msg) {
    document.getElementById('message').innerText = msg;
}

initializeSeatMap();
