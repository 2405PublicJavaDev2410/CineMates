document.addEventListener('DOMContentLoaded', function() {
    updateTotalPrice();

    document.getElementById('selectAll').addEventListener('change', function() {
        let checkboxes = document.getElementsByName('selectedItems');
        for (let checkbox of checkboxes) {
            checkbox.checked = this.checked;
        }
        updateTotalPrice();
    });

    let checkboxes = document.getElementsByName('selectedItems');
    checkboxes.forEach(checkbox => {
        checkbox.addEventListener('change', updateTotalPrice);
    });

    document.querySelectorAll('.cart-quantity-btn').forEach(btn => {
        btn.addEventListener('click', function() {
            let cartNo = this.getAttribute('data-cart-no');
            let productNo = this.getAttribute('data-product-no');
            let change = this.classList.contains('decrease') ? -1 : 1; // decrease는 -1, increase는 +1
            updateQuantity(cartNo, productNo, change);
        });
    });


    document.querySelectorAll('.cart-btn-delete').forEach(btn => {
        btn.addEventListener('click', function(e) {
            let cartNo = this.getAttribute('data-cart-no');
            let productNo = this.getAttribute('data-product-no');
            removeItem(cartNo, productNo);
        });
    });
});

function updateQuantity(cartNo, productNo, change) {
    let quantityElement = document.getElementById(`quantity${cartNo}${productNo}`);
    let currentQuantity = parseInt(quantityElement.textContent);
    let newQuantity = currentQuantity + parseInt(change);

    if (newQuantity < 1) {
        alert('수량은 1 미만이 될 수 없습니다.');
        return;
    }

    fetch(`/store/cart/update`, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify({ cartNo: cartNo, productNo: productNo, quantity: change})
    })
        .then(response => response.json())
        .then(data => {
            if (data.success) {
                quantityElement.textContent = newQuantity;
                updateItemTotal(cartNo, productNo);
                updateTotalPrice();
            } else {
                alert('수량 변경에 실패했습니다.');
            }
        })
        .catch(error => {
            console.error('Error:', error);
            alert('수량 변경 중 오류가 발생했습니다.');
        });
}

function removeItem(cartNo, productNo) {
    if (confirm('이 항목을 삭제하시겠습니까?')) {
        fetch('/store/cart/delete', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({ cartNo: cartNo, productNo: productNo })
        })
            .then(response => response.json())
            .then(data => {
                if (data.success) {
                    let itemElement = document.querySelector(`[data-cart-no="${cartNo}"][data-product-no="${productNo}"]`).closest('.cart-item');
                    itemElement.remove();
                    updateTotalPrice();
                } else {
                    alert('항목 삭제에 실패했습니다.');
                }
            })
            .catch(error => {
                console.error('Error:', error);
                alert('항목 삭제 중 오류가 발생했습니다.');
            });
    }
}

function updateItemTotal(cartNo, productNo) {
    let quantityElement = document.getElementById(`quantity${cartNo}${productNo}`);
    let itemElement = quantityElement.closest('.cart-item');
    let priceElement = itemElement.querySelector('.cart-product-info div span:nth-child(2)');
    let totalElement = itemElement.querySelector('.cart-product-total');

    let quantity = parseInt(quantityElement.textContent);
    let price = parseInt(priceElement.getAttribute('data-price'));

    let total = quantity * price;
    totalElement.textContent = total.toLocaleString() + '원';
}

function updateTotalPrice() {
    let checkboxes = document.getElementsByName('selectedItems');
    let totalAmount = 0;
    let totalDiscount = 0;

    checkboxes.forEach(checkbox => {
        if (checkbox.checked) {
            let itemElement = checkbox.closest('.cart-item');
            let quantityElement = itemElement.querySelector('.cart-product-quantity span');
            let priceElement = itemElement.querySelector('.cart-product-info div span:nth-child(2)');
            let discountElement = itemElement.querySelector('.cart-product-discount');

            let quantity = parseInt(quantityElement.textContent);
            let price = parseInt(priceElement.getAttribute('data-price'));
            let discount = parseInt(discountElement ? discountElement.getAttribute('data-discount') : '0');

            totalAmount += price * quantity;
            totalDiscount += discount * quantity;
        }
    });

    let finalPrice = totalAmount - totalDiscount;

    document.getElementById('totalAmount').textContent = totalAmount.toLocaleString() + '원';
    document.getElementById('totalDiscount').textContent = totalDiscount.toLocaleString() + '원';
    document.getElementById('finalPrice').textContent = finalPrice.toLocaleString() + '원';
}

function orderSelected() {
    let selectedItems = Array.from(document.querySelectorAll('input[name="selectedItems"]:checked'))
        .map(checkbox => {
            let item = checkbox.closest('.cart-item');
            let cartNo = item.getAttribute('data-cart-no');
            let productNo = item.getAttribute('data-product-no');
            let quantityElement = item.querySelector('.cart-product-quantity span');
            let quantity = parseInt(quantityElement.textContent);
            return {
                cartNo: cartNo,
                productNo: productNo,
                quantity: quantity
            };
        });

    if (selectedItems.length === 0) {
        alert('주문할 상품을 선택해주세요.');
        return;
    }

    // 선택한 상품들의 cartNo를 서버로 전송
    fetch('/store/purchase/initiate', {
        method: 'POST',
        headers: {
            'Content-Type' : 'application/json',
        },
        body: JSON.stringify({
            items: selectedItems
        })
    })
        .then(response => response.json())
        .then(data => {
            if (data.success) {
                window.location.href = data.redirectUrl;
            } else {
                alert('주문 처리 중 오류가 발생했습니다.');
            }
        })
        .catch(error => {
            console.error('Error:', error);
            alert('주문 처리 중 오류가 발생했습니다.');
        });
}

function orderAll() {
    fetch('/store/purchase/initiate', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify({
            purchaseAll: true
        })
    })
        .then(response => response.json())
        .then(data => {
            if (data.success) {
                window.location.href = data.redirectUrl;
            } else {
                alert('주문 처리 중 오류가 발생했습니다.');
            }
        })
        .catch(error => {
            console.error('Error:', error);
            alert('주문 처리 중 오류가 발생했습니다.');
        });
}