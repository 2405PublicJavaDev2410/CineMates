// 샘플 데이터
const purchaseData = [
    { date: '2024-04-30', imageUrl: '이미지 공간', product: 'CineMates 영화관람권', price: '12,000원' },
    { date: '2024-07-10', imageUrl: '이미지 공간', product: '트랜스포머 원 인형', price: '14,000원' },
    { date: '2024-09-20', imageUrl: '이미지 공간', product: '팝콘 세트', price: '7,000원' }
];

document.addEventListener('DOMContentLoaded', function() {
    // 페이지 로드 시 1개월 기간으로 기본 설정
    // toISOSTring은 Date객체를 ISO 8601(YYYY-MM-DDTHH:MM:SS.sssZ) 형식의 문자열로 변환
    // T를 기준으로 split하여 배열로 저장된 0번째 인덱스 값(YYYY-MM-DD)을 가져옴
    const today = new Date();
    const endDate = today.toISOString().split('T')[0]; // 오늘 날짜
    today.setMonth(today.getMonth() - 1); // 1개월 전 날짜
    const startDate = today.toISOString().split('T')[0];

    document.getElementById('start-date').value = startDate;
    document.getElementById('end-date').value = endDate;

    // 1개월 버튼 활성화
    document.getElementById('one-month-btn').classList.add('active');
});

// 기간 버튼 클릭 시 날짜 자동 설정
function setPeriod(months, e) {
    const today = new Date();
    const endDate = today.toISOString().split('T')[0]; // 오늘 날짜
    today.setMonth(today.getMonth() - months);
    const startDate = today.toISOString().split('T')[0]; // months 전 날짜

    document.getElementById('start-date').value = startDate;
    document.getElementById('end-date').value = endDate;

    // 조회기간 버튼 색상 활성화
    clearActiveClass();
    e.classList.add('active');
}

// 직접 입력 버튼 클릭 시 날짜 필드 활성화
function customPeriod(e) {
    clearActiveClass();
    e.classList.add('active');
}
// 활성화된 버튼 초기화
function clearActiveClass() {
    const buttons = document.querySelectorAll('.period-buttons button');
    buttons.forEach(button => button.classList.remove('active'));
}

// 조회 버튼 클릭 이벤트 핸들러
document.getElementById('period-search-btn').addEventListener('click', function() {
    const startDate = document.getElementById('start-date').value;
    const endDate = document.getElementById('end-date').value;

    const resultTable = document.getElementById('result-table');
    const resultBody = document.getElementById('result-body');
    const noResult = document.getElementById('period-no-result');

    // 기존 결과 초기화
    resultBody.innerHTML = '';
    resultTable.style.display = 'none';
    noResult.style.display = 'none';

    // 해당 기간에 맞는 구매내역 필터링
    // 조회시작일과 종료일 사이에 해당하는 데이터를 담는 item 객체
    const filteredData = purchaseData.filter(item => item.date >= startDate && item.date <= endDate);

    if (filteredData.length > 0) {
        // 결과가 있을 경우 테이블에 데이터 추가
        filteredData.forEach(item => {
            const row = document.createElement('tr');
            row.innerHTML = `
                <td>${item.date}</td>
                <td>
                    <a href="#" class="product-link">
                        <img src="${item.imageUrl}" alt="product image" class="product-image">
                        <span class="product-name">${item.product}</span>
                    </a>
                </td>
                <td>${item.price}</td>
            `;
            resultBody.appendChild(row);
        });
        resultTable.style.display = 'table';
    } else {
        // 결과가 없을 경우 메시지 출력
        noResult.style.display = 'block';
    }
});