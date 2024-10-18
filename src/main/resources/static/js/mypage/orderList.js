const resultTable = document.getElementById('result-table');
const resultBody = document.getElementById('result-body');
const noResult = document.getElementById('period-no-result');

// 구매 정보 DOM 구성
const ResultInfo = (data) => {
    if (data && data.length > 0) {
        // 데이터 있으면 테이블에 행 추가
        data.forEach(item => {
            const row = document.createElement('tr');
            row.innerHTML = `
                    <td>${item.purchaseDate}</td>
                    <td>
                        <a href="#" class="product-link">
                            <img src="${item.imageUrl}" alt="product image" class="product-image">
                            <span class="product-name">${item.productName}</span>
                        </a>
                    </td>
                    <td>${item.price}</td>
                `;
            resultBody.appendChild(row);
        });
        // 결과 테이블 보이기
        resultTable.style.display = 'table';
    }
}

// 결과 없음 DOM 구성
const NoResultInfo = () => {
    const errorContents = `<b><p id="error-text">구매 내역이 없습니다.</p></b>`;
    document.querySelector('#error-message').innerHTML = errorContents;
}

// 조회
function findOrder() {
    // 조회 시작일과 종료일
    const startDate = document.getElementById('start-date').value;
    const endDate = document.getElementById('end-date').value;

    // 기존 결과 초기화
    resultBody.innerHTML = '';
    resultTable.style.display = 'none';
    noResult.style.display = 'none';

    $.ajax({
        url: `/find-order?startDate=${startDate}&endDate=${endDate}`,
        method: 'GET',
        success: function (data) {
            if (data && data.length > 0) {
                ResultInfo(data);
            } else {
                NoResultInfo();
            }
        },
        error: function () {
            alert("서버 통신 에러!");
        }
    });
}

// 페이지 로드 시 1개월 기간으로 기본 설정
document.addEventListener('DOMContentLoaded', function() {
    const today = new Date();
    const endDate = today.toISOString().split('T')[0]; // 오늘 날짜
    const startDate = new Date(today);
    startDate.setMonth(today.getMonth() - 1); // 1개월 전 날짜

    // 이전 달 마지막 날로 설정
    // ex) 3월 31일에서 1달 전일 때 2월 31일이 없을 경우
    if (today.getDate() !== startDate.getDate()) {
        startDate.setDate(0); // 해당 달의 마지막 날로 설정
    }

    document.getElementById('start-date').value = startDate.toISOString().split('T')[0];
    document.getElementById('end-date').value = endDate;

    // 1개월 버튼 활성화
    document.getElementById('one-month-btn').classList.add('active');
    // 상품 구매내역 카테고리 활성화
    document.querySelector('#side-menu-2').classList.add('selected');
});

// 기간 버튼 클릭 시 날짜 자동 설정
function setPeriod(months, e) {
    const today = new Date();
    const endDate = today.toISOString().split('T')[0]; // 오늘 날짜
    const startDate = new Date(today); // today 객체를 복사하여 새로운 Date 객체 생성
    startDate.setMonth(today.getMonth() - months);

    if (today.getDate() !== startDate.getDate()) {
        startDate.setDate(0);
    }

    document.getElementById('start-date').value = startDate.toISOString().split('T')[0];
    document.getElementById('end-date').value = endDate;

    // 조회기간 버튼 색상 활성화
    clearActiveButton();
    e.classList.add('active');
}

// 직접 입력 버튼 클릭 시 버튼 활성화
function customPeriod(e) {
    clearActiveButton();
    e.classList.add('active');
}

// 활성화된 버튼 초기화
function clearActiveButton() {
    const buttons = document.querySelectorAll('.period-buttons button');
    buttons.forEach(button => button.classList.remove('active'));
}

// 조회 버튼 클릭 시
document.getElementById('period-search-btn').addEventListener('click', findOrder);