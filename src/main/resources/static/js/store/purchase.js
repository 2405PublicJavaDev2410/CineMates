// 전역 변수로 선언
var IMP;
var purchaseDetail;

// 문서가 로드된 후 실행
document.addEventListener('DOMContentLoaded', function() {
    // KG이니시스 결제 모듈 초기화
    IMP = window.IMP;
    IMP.init("imp84335481"); // 가맹점 식별코드

    // HTML에서 전달받은 데이터 할당
    purchaseDetail = window.purchaseData;
});

function requestPay() {
    if (!IMP) {
        console.error('IMP is not initialized');
        return;
    }

    /*var payMethod = document.getElementById("payMethod").value;*/
    var payMethod = document.querySelector('input[name="payment"]:checked').value;

    IMP.request_pay({
        pg: "html5_inicis",
        pay_method: payMethod,
        merchant_uid: "ORDER_" + new Date().getTime(),
        name: purchaseDetail.productName,
        amount: purchaseDetail.finalAmount,
        buyer_email: purchaseDetail.memberEmail,
        buyer_name: purchaseDetail.memberName,
        buyer_tel: purchaseDetail.memberPhone,
    }, function (rsp) {
        if (rsp.success) {
            // 결제 성공 시 로직
            $.ajax({
                url: "/store/payment/complete",
                method: "POST",
                data: JSON.stringify({
                    imp_uid: rsp.imp_uid,
                    merchant_uid: rsp.merchant_uid,
                    paid_amount: rsp.paid_amount,
                    apply_num: rsp.apply_num,
                    quantity: purchaseDetail.quantity
                }),
                contentType: "application/json"
            }).done(function (data) {
                alert("결제가 완료되었습니다.");
                window.location.href = "/store/payment/complete/";
            });
        } else {
            // 결제 실패 시 로직
            alert("결제에 실패하였습니다. 에러 내용: " + rsp.error_msg);
        }
    });
}

function goBack() {
    history.back();
}