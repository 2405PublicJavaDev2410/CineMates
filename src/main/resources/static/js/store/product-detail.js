document.addEventListener('DOMContentLoaded', function() {
    const quantityInput = document.getElementById('quantity');
    const minusBtn = document.querySelector('.quantity-btn.minus');
    const plusBtn = document.querySelector('.quantity-btn.plus');
    const totalPriceElement = document.getElementById('totalPrice');
    const addToCartBtn = document.getElementById('addToCart');
    const giftBtn = document.getElementById('giftButton');
    const buyBtn = document.getElementById('buyButton');

    const basePrice = productPrice;

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
        if (quantityInput.value < 10) {
            quantityInput.value = parseInt(quantityInput.value) + 1;
            updateTotalPrice();
        }
    });

    quantityInput.addEventListener('change', updateTotalPrice);

    addToCartBtn.addEventListener('click', function() {
        const quantity = parseInt(quantityInput.value);
        const productNo = this.getAttribute('data-product-no');
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
                    alert('장바구니 추가에 실패했습니다. 다시 시도해주세요.');
                }
            })
            .catch(error => {
                console.error('Error:', error);
                alert('오류가 발생했습니다. 다시 시도해주세요.');
            });
    });

    giftBtn.addEventListener('click', function(e) {
        e.preventDefault();
        const productNo = this.getAttribute('data-product-no');
        const quantity = parseInt(quantityInput.value);
        window.location.href = `/store/gift/${productNo}?quantity=${quantity}`;
    });

    // 초기 총 가격 설정
    updateTotalPrice();
});