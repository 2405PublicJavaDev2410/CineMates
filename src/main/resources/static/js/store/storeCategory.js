document.addEventListener('DOMContentLoaded', function() {
    const cartIcons = document.querySelectorAll('.cart-icon');
    cartIcons.forEach(button => {
        button.addEventListener('click', function(e) {
            e.preventDefault();
            const productNo = this.getAttribute('data-product-no');
            addToCart(productNo);
        });
    });
});

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