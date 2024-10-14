document.addEventListener('DOMContentLoaded', function() {
    const backButton = document.getElementById('backButton');
    const paymentButton = document.getElementById('paymentButton');

    backButton.addEventListener('click', function() {
        history.back();
    });

    paymentButton.addEventListener('click', function() {
        if (validatePaymentMethod()) {
            initiatePayment();
        }
    });

    function validatePaymentMethod() {
        const selectedMethod = document.querySelector('input[name="paymentMethod"]:checked');
        if (!selectedMethod) {
            alert('결제 수단을 선택해주세요.');
            return false;
        }
        return true;
    }

    function initiatePayment() {
        const paymentMethod = document.querySelector('input[name="paymentMethod"]:checked').value;

        fetch('/store/initiate-payment', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                'X-CSRF-TOKEN': document.querySelector('meta[name="_csrf"]').getAttribute('content')
            },
            body: JSON.stringify({
                purchaseNo: purchaseNo,
                amount: finalPrice,
                paymentMethod: paymentMethod
            })
        })
            .then(response => response.json())
            .then(data => {
                if (data.success) {
                    // 결제 진행 (예: 카카오페이 또는 신용카드 결제 창 열기)
                    if (paymentMethod === 'kakaopay') {
                        window.location.href = data.paymentUrl;
                    } else if (paymentMethod === 'creditCard') {
                        // 신용카드 결제 창 열기 로직
                        openCreditCardPaymentWindow(data.paymentKey);
                    }
                } else {
                    alert('결제 초기화에 실패했습니다: ' + data.message);
                }
            })
            .catch(error => {
                console.error('Error:', error);
                alert('결제 처리 중 오류가 발생했습니다.');
            });
    }

    function openCreditCardPaymentWindow(paymentKey) {
        // 신용카드 결제 창을 여는 로직
        // 예: 팝업 창 또는 모달 창으로 결제 페이지 열기
        window.open('/store/credit-card-payment?paymentKey=' + paymentKey, 'CreditCardPayment', 'width=500,height=600');
    }
});