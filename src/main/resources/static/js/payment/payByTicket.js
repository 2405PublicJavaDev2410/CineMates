function goPay() {
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
    if (confirm("결제 하시겠습니까?")) {
        $.ajax({
            url: "/payment/ticketFromChat",
            data: {
                memberIds: memberIds
            },
            type: "POST",
            dataType: "json",
            success: function (response) {
                console.log("Updated members:", response);
                location.href='/';
                if (response.length > 0) {

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

                    alert("결제가 완료되었습니다!");
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
                } else {
                    alert("결제는 완료되었지만 티켓 업데이트에 실패했습니다.");
                }
            },
            error: function (xhr, status, error) {
                console.error("Error occurred:", error);
                console.log("Status:", status);
                console.log("Response:", xhr.responseText);
                alert("결제 중 오류가 발생했습니다");
            }
        });
    } else {
        console.log("결제가 취소되었습니다.");
    }
}