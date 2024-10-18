document.addEventListener('DOMContentLoaded', function() {
    const quantityInput = document.getElementById('quantity');
    const minusBtn = document.querySelector('.quantity-btn.minus');
    const plusBtn = document.querySelector('.quantity-btn.plus');
    const totalPriceElement = document.getElementById('totalPrice');
    const addToCartBtn = document.getElementById('addToCart');
    const giftBtn = document.getElementById('giftButton');
    const buyBtn = document.getElementById('buyButton');

    const basePrice = productPrice; // 이 변수가 서버에서 제공되어야 합니다

    function updateTotalPrice() {
        const quantity = parseInt(quantityInput.value);
        const totalPrice = basePrice * quantity;
        totalPriceElement.textContent = totalPrice.toLocaleString() + '원';
    }

    minusBtn.addEventListener('click', function() {
        if (quantityInput.value > 1) {
            quantityInput.value = parseInt(quantityInput.value) - 1;
            updateTotalPrice();
        }
    });

    plusBtn.addEventListener('click', function() {
        quantityInput.value = parseInt(quantityInput.value) + 1;
        updateTotalPrice();
    });

    quantityInput.addEventListener('change', updateTotalPrice);

    buyBtn.addEventListener('click', function(event) {
        event.preventDefault();
        const productNo = this.getAttribute('data-product-no');
        const quantity = parseInt(quantityInput.value);
        buyNow(productNo, quantity);
    });

    function buyNow(productNo, quantity) {
        fetch('/store/purchase/initiate', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({
                items: [{productNo: productNo, quantity: quantity}]
            })
        })
            .then(response => response.json())
            .then(data => {
                if (data.success) {
                    window.location.href = data.redirectUrl;
                } else {
                    alert(data.message || '구매 처리 중 오류가 발생했습니다.');
                }
            })
            .catch(error => {
                console.error('Error:', error);
                alert('오류가 발생했습니다. 다시 시도해주세요.');
            });
    }

    addToCartBtn.addEventListener('click', function(event) {
        event.preventDefault();
        const productNo = this.getAttribute('data-product-no');
        const quantity = parseInt(quantityInput.value);

        fetch('/store/cart/insert', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({ productNo: productNo, quantity: quantity })
        })
            .then(response => response.json())
            .then(data => {
                if (data.success) {
                    alert('상품이 장바구니에 추가되었습니다.');
                } else {
                    alert(data.message || '장바구니 추가에 실패했습니다. 다시 시도해주세요.');
                }
            })
            .catch(error => {
                console.error('Error:', error);
                alert('오류가 발생했습니다. 다시 시도해주세요.');
            });
    });

    giftBtn.addEventListener('click', function(event) {
        event.preventDefault();
        const productNo = this.getAttribute('data-product-no');
        const quantity = parseInt(quantityInput.value);

        fetch('/store/purchase/initiate', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({
                action: "gift",
                items: [{productNo: productNo, quantity: quantity}]
            })
        })
            .then(response => response.json())
            .then(data => {
                if (data.success) {
                    window.location.href = data.redirectUrl;
                } else {
                    alert(data.message || '선물하기 처리 중 오류가 발생했습니다.');
                }
            })
            .catch(error => {
                console.error('Error:', error);
                alert('오류가 발생했습니다. 다시 시도해주세요.');
            });
    });

    // 초기 총 가격 설정
    updateTotalPrice();
});