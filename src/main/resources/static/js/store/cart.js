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

    document.querySelectorAll('.quantity-btn').forEach(btn => {
        btn.addEventListener('click', function() {
            let cartNo = this.getAttribute('data-cart-no');
            let change = this.classList.contains('decrease') ? -1 : 1;
            updateQuantity(cartNo, change);
        });
    });
});

function updateQuantity(cartNo, change) {
    let quantityElement = document.getElementById(`quantity-${cartNo}`);
    let currentQuantity = parseInt(quantityElement.textContent);
    let newQuantity = currentQuantity + change;

    if (newQuantity < 1) {
        alert('수량은 1 미만이 될 수 없습니다.');
        return;
    }

    fetch(`/store/cart/update`, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify({ cartNo: cartNo, quantity: newQuantity })
    })
        .then(response => response.json())
        .then(data => {
            if (data.success) {
                quantityElement.textContent = newQuantity;
                updateItemTotal(cartNo);
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

function updateItemTotal(cartNo) {
    let quantityElement = document.getElementById(`quantity-${cartNo}`);
    let priceElement = quantityElement.closest('tr').querySelector('td:nth-child(4)');
    let totalElement = document.getElementById(`total-${cartNo}`);

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
            let row = checkbox.closest('tr');
            let quantityElement = row.querySelector('td:nth-child(3) span');
            let priceElement = row.querySelector('td:nth-child(4)');
            let discountElement = row.querySelector('td:nth-child(5)');

            let quantity = parseInt(quantityElement.textContent);
            let price = parseInt(priceElement.getAttribute('data-price'));
            let discount = parseInt(discountElement.getAttribute('data-discount') || '0');

            totalAmount += price * quantity;
            totalDiscount += discount * quantity;
        }
    });

    let finalPrice = totalAmount - totalDiscount;

    document.getElementById('totalAmount').textContent = totalAmount.toLocaleString() + '원';
    document.getElementById('totalDiscount').textContent = totalDiscount.toLocaleString() + '원';
    document.getElementById('finalPrice').textContent = finalPrice.toLocaleString() + '원';
}

function deleteSelected() {
    let selectedItems = Array.from(document.getElementsByName('selectedItems'))
        .filter(checkbox => checkbox.checked)
        .map(checkbox => checkbox.value);

    if (selectedItems.length === 0) {
        alert('삭제할 상품을 선택해주세요.');
        return;
    }

    if (confirm('선택한 상품을 삭제하시겠습니까?')) {
        fetch('/store/cart/delete', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({ cartNos: selectedItems })
        })
            .then(response => response.json())
            .then(data => {
                if (data.success) {
                    location.reload();
                } else {
                    alert('상품 삭제에 실패했습니다.');
                }
            });
    }
}

function clearCart() {
    if (confirm('장바구니를 비우시겠습니까?')) {
        fetch('/store/cart/clear', {
            method: 'POST'
        })
            .then(response => response.json())
            .then(data => {
                if (data.success) {
                    location.reload();
                } else {
                    alert('장바구니 비우기에 실패했습니다.');
                }
            });
    }
}

function orderSelected() {
    let selectedItems = Array.from(document.getElementsByName('selectedItems'))
        .filter(checkbox => checkbox.checked)
        .map(checkbox => checkbox.value);

    if (selectedItems.length === 0) {
        alert('주문할 상품을 선택해주세요.');
        return;
    }

    // 선택한 상품들의 cartNo를 서버로 전송
    fetch('/store/cart/proceed', {
        method: 'POST',
        headers: {
            'Content-Type' : 'application/json',
        },
        body: JSON.stringify({
            action: 'purchase',
            selectedItems: selectedItems
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
    fetch('/store/cart/proceed', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify({
            action: 'purchase',
            all: true
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