var reservationData = {
    reservationNo: document.getElementById('reservationNo').value,
    reservationVisitor: document.getElementById('reservationVisitor').value,
    reservationSeat: document.getElementById('reservationSeat').value,
    memberId: document.getElementById('memberId').value,
    cinemaName: document.getElementById('cinemaName').value,
    screenName: document.getElementById('screenName').value,
    screenNo: document.getElementById('screenNo').value,
    movieNo: document.getElementById('movieNo').value,
    cinemaNo: document.getElementById('cinemaNo').value,
    showtimeTime: document.getElementById('showtimeTime').value,
    title: document.getElementById('title').value,
    reservationDate: document.getElementById('reservationDate').value,
    buyer_tel: document.getElementById('buyer_tel').value,
    buyer_name: document.getElementById('buyer_name').value,
    buyer_email: document.getElementById('buyer_email').value,
    showtimeNo: document.getElementById('showtimeNo').value,
    adultReserved: document.getElementById('adultReserved').value,
    childReserved: document.getElementById('childReserved').value,
    seniorReserved: document.getElementById('seniorReserved').value,
    selectSeat : document.getElementById('selectSeat').value
};
let selectedPaymentMethod = '';
var reservedSeatCount = 0;

// 페이지 로드 시 실행
document.addEventListener('DOMContentLoaded', function() {
    // 예약한 좌석 수 계산
    const seatInfo = document.querySelector('.reserveInfo_2 strong').textContent;
    reservedSeatCount = seatInfo.split(',').length;

    // 총 상품 금액 가져오기
    const formattedPrice = parseInt(totalPrice).toLocaleString('ko-KR');
    document.getElementById('productPrice').textContent = '상품 금액: ' + formattedPrice  + '원';
    // 초기 결제 금액 설정
    updateFinalPrice(0);
});

function PayMethod(method) {
    selectedPaymentMethod = method;

    // 모든 결제 방식 버튼의 스타일 초기화
    document.querySelectorAll('.payment-method-btn').forEach(btn => {
        btn.classList.remove('selected');
    });

    // 선택된 결제 방식 버튼 스타일 변경
    document.querySelector(`#${method}-btn`).classList.add('selected');

    // 관람권 선택 시 input number 표시
    const payment = document.querySelector('.payment');
    const payment1 = document.querySelector('.payment_1');
    const payment2 = document.querySelector('.payment_pay');
    if (method === 'ticket') {
        payment.style.display = 'none';
        payment1.style.display = 'none';
        payment2.style.display = 'none';
    }

    // 결제하기 버튼 상태 업데이트
    updatePayButtonState();
}

function updateFinalPrice() {
    const finalPrice = Math.max(0, totalPrice);
    document.getElementById('finalPrice').textContent = `결제 금액: ${finalPrice.toLocaleString()}원`;
}

function updatePayButtonState() {
    const payButton = document.getElementById('pay-button');

    if (selectedPaymentMethod === 'ticket') {
        payButton.disabled = (Ticket <= 0);
    } else payButton.disabled = selectedPaymentMethod !== 'credit';
}

function processPayment() {
    if (selectedPaymentMethod === 'ticket') {
        goPay();
    } else if (selectedPaymentMethod === 'credit') {
        requestPay();
    } else {
        alert('결제 방식을 선택해주세요.');
    }
}

function goPay() {
    if (confirm("결제 하시겠습니까?")) {
        const ticketCount = reservedSeatCount;
        if (Ticket > 0) {
            $.ajax({
                url: "/payment/ticket",
                data: {
                    memberId: memberId,
                    ticketCount:ticketCount
                },
                type: "POST",
                dataType: "json",
                success: function (response) {
                    console.log("Updated members:", response);
                    alert("결제가 완료되었습니다!");
                    location.href = '/';
                    // 여기서 기존의 결제 로직을 실행합니다.
                    var rsp = {
                        success: true,
                        imp_uid: 'movieTicket_' + new Date().getTime(),
                        merchant_uid: 'merchant_' + new Date().getTime(),
                        name: reservationData.title,
                        amount: 100,
                        buyer_email: reservationData.buyer_email,
                        buyer_name: reservationData.buyer_name,
                        buyer_tel: reservationData.buyer_tel,
                        pay_method: 'movieTicket'
                    };
                    console.log(rsp);


                    var buyerInfo = {
                        "imp_uid": rsp.imp_uid,
                        "merchant_uid": rsp.merchant_uid,
                        "name": rsp.name,
                        "amount": rsp.amount,  // Changed from rsp.paid_amount to rsp.amount
                        "buyer_email": rsp.buyer_email,
                        "buyer_name": rsp.buyer_name,
                        "buyer_tel": rsp.buyer_tel,
                        "screenNo": reservationData.screenNo,
                        "movieNo": reservationData.movieNo,
                        "reservationNo": reservationData.reservationNo,
                        "pay_method": rsp.pay_method
                    };

                    var reserveInfo = {
                        "reservationNo": reservationData.reservationNo,
                        "reservationVisitor": reservationData.reservationVisitor,
                        "reservationSeat": reservationData.selectSeat,
                        "reservationDate": reservationData.reservationDate,
                        "memberId": reservationData.memberId,
                        "cinemaName": reservationData.cinemaName,
                        "screenName": reservationData.screenName,
                        "screenNo": reservationData.screenNo,
                        "movieNo": reservationData.movieNo,
                        "imp_uid": rsp.imp_uid,
                        "cinemaNo": reservationData.cinemaNo,
                        "title": reservationData.title,
                        "showtimeTime": reservationData.showtimeTime,
                        "showtimeNo": reservationData.showtimeNo,
                        "adultReserved": reservationData.adultReserved,
                        "childReserved": reservationData.childReserved,
                        "seniorReserved": reservationData.seniorReserved
                    };

                    $.ajax({
                        url: "/save_buyerInfo",
                        type: "POST",
                        contentType: "application/json",
                        data: JSON.stringify({buyerInfo, reserveInfo}),
                        success: function (response) {
                            console.log("결제정보 저장 완료");
                        },
                        error: function (xhr, status, error) {
                            console.error("결제정보 저장 실패:", error);
                            console.error("응답 내용:", xhr.responseText);
                        }
                    });
                },
                error: function (xhr, status, error) {
                    console.error("Error occurred:", error);
                    console.log("Status:", status);
                    console.log("Response:", xhr.responseText);
                    alert("결제 중 오류가 발생했습니다");
                }
            });
        }
        else{
            alert("관람권이 부족합니다");
        }
    } else {
        console.log("결제가 취소되었습니다.");
    }
}

var IMP = window.IMP;

IMP.init('imp68486865'); // 아임포트 가맹점 식별코드 --> manual 에 있는 url 에서 식별코드.apikeys 의 고객사 식별코드 확인

function requestPay() {
    // 결제 요청 하는 코드 name , amount , buyer 관련만 변경 하면 됨 buyer 없으면 주석 .
    IMP.request_pay({
        pg: 'html5_inicis.INIpayTest', // KG이니시스
        pay_method: 'card',
        merchant_uid: 'merchant_' + new Date().getTime(),
        name: reservationData.title,
        // amount: 15000 * reservationData.adultReserved + 13000 * reservationData.childReserved + 7000 * reservationData.seniorReserved,
        amount: 100,
        buyer_email: reservationData.buyer_email,
        buyer_name: reservationData.buyer_name,
        buyer_tel: reservationData.buyer_tel,
        /* buyer_postcode : '123-456' */
    }, function (rsp) {
        // 정보 맞는지 조회 하기 위한 ajax
        if (rsp.success) {
            $.ajax({
                url: "/validation/" + rsp.imp_uid,
                type: "POST",
                contentType: "application/json",
                data: JSON.stringify({
                    imp_uid: rsp.imp_uid,
                    merchant_uid: rsp.merchant_uid,
                })
            }).done(function (data) {
                alert("결제가 완료되었습니다!");
                location.href = '/';
                console.log(data);
                // buyerInfo만 확인 imp_uid ,merchant_uid , name , amount 까지는 고정 나머지는 없으면 삭제 .
                var buyerInfo = {
                    "imp_uid": rsp.imp_uid,
                    "merchant_uid": rsp.merchant_uid,
                    "name": rsp.name,
                    "amount": parseInt(rsp.paid_amount, 10),
                    "buyer_email": rsp.buyer_email,
                    "buyer_name": rsp.buyer_name,
                    "buyer_tel": rsp.buyer_tel,
                    "screenNo": reservationData.screenNo,
                    "movieNo": reservationData.movieNo,
                    "reservationNo": reservationData.reservationNo,
                    "pay_method": 'card',
                };
                //  작성자 기준 예매 정보
                var reserveInfo = {
                    "reservationNo": reservationData.reservationNo,
                    "reservationVisitor": reservationData.reservationVisitor,
                    "reservationSeat": reservationData.selectSeat,
                    "reservationDate": reservationData.reservationDate,
                    "memberId": reservationData.memberId,
                    "cinemaName": reservationData.cinemaName,
                    "screenName": reservationData.screenName,
                    "screenNo": reservationData.screenNo,
                    "movieNo": reservationData.movieNo,
                    "imp_uid": rsp.imp_uid,
                    "cinemaNo": reservationData.cinemaNo,
                    "title": reservationData.title,
                    "showtimeTime": reservationData.showtimeTime,
                    "showtimeNo": reservationData.showtimeNo,
                    "adultReserved": reservationData.adultReserved,
                    "childReserved": reservationData.childReserved,
                    "seniorReserved": reservationData.seniorReserved

                }
                // 결제 정보 확인하고 결제 완료 후 결제 정보 저장하는 ajax
                $.ajax({
                    url: "/save_buyerInfo",
                    type: "POST",
                    contentType: "application/json",
                    data: JSON.stringify({buyerInfo, reserveInfo}),
                    success: function (response) {
                        console.log("결제정보 저장 완료");
                    },
                    error: function (xhr, status, error) {
                        console.error("결제정보 저장 실패:", error);
                        console.error("응답 내용:", xhr.responseText);
                    }
                });
            });
        } else {
            alert("결제에 실패하였습니다. " + rsp.error_msg);
            console.error("응답 내용:", xhr.responseText);
        }
    });
}

// 결제 취소하는 함수 작성자 기준 예매번호로 imp_uid 조회해서 맞으면 삭제 하는 코드
function cancelPay() {
    const reservationNo = prompt("예약 번호를 입력해주세요").trim();
    if (!reservationNo) {
        alert("올바른 예약 번호를 입력해야 합니다.");
        return;
    }

    // 먼저 서버에서 imp_uid를 가져옵니다.
    $.ajax({
        url: "/getImpUid",
        type: "GET",
        data: {reservationNo: reservationNo}
    })
        .done(function (response) {
            if (response) {
                if (confirm("정말 취소 하시겠습니까?")) {
                    // imp_uid를 받아왔다면, 결제 취소를 진행합니다.
                    $.ajax({
                        url: "/payments/cancel/" + response,
                        type: "GET",
                        contentType: "application/json",
                        // data:
                        // JSON.stringify({
                        // merchant_uid: response.merchant_uid,
                        // cancel_request_amount: response.amount,
                        // reason: "고객 요청",
                        // }),
                        // dataType: "json"
                    })
                        .done(function (data) {
                            alert("취소가 완료되었습니다!");
                            console.log(data);
                        })
                        .fail(function (jqXHR, textStatus) {
                            alert("결제 취소에 실패했습니다: " + textStatus);
                        });
                } else {
                    alert("유효한 예약 번호가 아닙니다.");
                }
            }
        })
        .fail(function (jqXHR, textStatus) {
            alert("예약 정보를 가져오는데 실패했습니다: " + textStatus);
        });
}