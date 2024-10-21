const createRoomBtn = document.querySelector('.createRoom-modal-btn');
const modal = document.getElementById("modalWrap");
const closeBtn = document.getElementById("closeBtn");

createRoomBtn.addEventListener('click', ()=>{
    modal.style.display = "block"; // 버튼을 클릭하면 모달을 보이게 함
});

closeBtn.addEventListener('click', ()=>{
    modal.style.display = "none"; // 모달을 닫는 버튼(X)을 클릭하면 모달을 숨김
});