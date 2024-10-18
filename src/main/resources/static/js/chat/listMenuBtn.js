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

// 채팅방 입장
function joinChatRoom(roomNo, movieNo , title, posterUrl, cinemaNo, cinemaName, cinemaLocationCode, cinemaAddress, roomCategory){
    if(confirm(`채팅방에 입장하시겠습니까?`)){
        location.href=`
    /chat/room?roomNo=${roomNo}
    &movieNo=${movieNo}
    &title=${encodeURIComponent(title)}
    &posterUrl=${posterUrl}
    &cinemaNo=${cinemaNo}
    &cinemaName=${cinemaName}
    &cinemaLocationCode=${cinemaLocationCode}
    &cinemaAddress=${cinemaAddress}
    &roomCategory=${roomCategory}
    `;
    }
}