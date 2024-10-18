document.addEventListener('DOMContentLoaded', function() {
    const cartIcons = document.querySelectorAll('.cart-icon');
    cartIcons.forEach(button => {
        button.addEventListener('click', function(e) {
            e.preventDefault();
            const productNo = this.getAttribute('data-product-no');
            addToCart(productNo);
        });
    });
     const buyNowButtons = document.querySelectorAll('.buy-now');
     buyNowButtons.forEach(button => {
         button.addEventListener('click', function(e) {
             e.preventDefault();
             const productNo = this.getAttribute('data-product-no');
             buyNow(productNo);
         })
     })
});

function buyNow(productNo) {
    fetch('/store/purchase/initiate', {
        method: 'POST',
        headers: {
            'Content-Type' : 'application/json'
        },
        body: JSON.stringify({
            items: [{productNo: productNo, quantity: 1}]
        })
    })
        .then(response => response.json())
        .then(data => {
            if (data.success) {
                window.location.href = data.redirectUrl;
            } else {
                alert('구매 처리 중 오류가 발생했습니다.');
            }
        })
        .catch(error => {
            console.error('Error:', error);
            alert('오류가 발생했습니다. 다시 시도해주세요.');
        });
}

function addToCart(productNo) {
    fetch('/store/cart/insert', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({
            productNo: productNo,
            quantity: 1
        })
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
}