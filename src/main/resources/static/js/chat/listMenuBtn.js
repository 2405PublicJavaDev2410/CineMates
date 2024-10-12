const myBtn = document.querySelector('#myBtn');
const allBtn = document.querySelector('#allBtn');
const pageTitle = document.querySelector('.page-title');
const urlParams = new URLSearchParams(window.location.search); // 쿼리스트링 가져오기


let status = urlParams.get('status');
if(status === "my"){
    myBtn.classList.add("activeBtn");
    allBtn.classList.remove("activeBtn");
    pageTitle.innerText="내 채팅방";
}else if(status === "all"){
    allBtn.classList.add("activeBtn");
    myBtn.classList.remove("activeBtn");
    pageTitle.innerText="전체 채팅방";
}

function showListByMyOrAll(status){
    location.href = `/chat/list?status=${status}`;
}