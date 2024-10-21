document.addEventListener('DOMContentLoaded', function() {
    const messageType = document.getElementById('message-type');
    const giftMessageText = document.getElementById('gift-message-text');
    const recipientName = document.getElementById('recipientName');
    const recipientPhone = document.getElementById('recipientPhone');
    const verifyRecipientBtn = document.getElementById('verify-recipient');
    const backButton = document.getElementById('back-button');
    const nextButton = document.getElementById('next-button');

    document.getElementById('gift').addEventListener('submit', function(e) {
        e.preventDefault();
        if (validateInputs()) {
            const formData = new FormData(this);
            const giftData = Object.fromEntries(formData.entries());

            fetch('/store/purchase/initiate', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify(giftData)
            })
                .then(response => response.json())
                .then(data => {
                    if (data.success) {
                        window.location.href = data.redirectUrl;
                    } else {
                        alert('선물 구매 처리 중 오류가 발생했습니다.');
                    }
                })
                .catch(error => {
                    console.error('Error:', error);
                    alert('선물 구매 처리 중 오류가 발생했습니다.');
                });
        }
    });


    messageType.addEventListener('change', function() {
        giftMessageText.style.display = this.value === 'custom' ? 'block' : 'none';
    });

    giftMessageText.addEventListener('input', function() {
        if (this.value.length > 100) {
            this.value = this.value.substring(0, 100);
        }
    });

    verifyRecipientBtn.addEventListener('click', function() {
        // AJAX를 사용하여 서버에 수신자 확인 요청
        fetch('/store/gift/verify-recipient', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({
                name: recipientName.value,
                phone: recipientPhone.value
            })
        })
            .then(response => response.json())
            .then(data => {
                if (data.valid) {
                    alert('유효한 수신자입니다.');
                } else {
                    alert('수신자를 찾을 수 없습니다.');
                }
            })
            .catch(error => {
                console.error('Error:', error);
                alert('수신자 확인 중 오류가 발생했습니다.');
            });
    });

    backButton.addEventListener('click', function() {
        history.back();
    });

    document.querySelector('form').addEventListener('submit', function(e) {
        if (!validateInputs()) {
            e.preventDefault();
        }
    });

    function validateInputs() {
        if (!recipientName.value.trim()) {
            alert('수신자 이름을 입력해주세요.');
            return false;
        }
        if (!recipientPhone.value.trim() || !/^\d{10,11}$/.test(recipientPhone.value)) {
            alert('올바른 휴대폰 번호를 입력해주세요.');
            return false;
        }
        return true;
    }
});