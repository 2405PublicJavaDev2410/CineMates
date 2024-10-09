// 모달 요소 가져오기
const modal = document.getElementById('image-modal');
const modalImage = document.getElementById('modal-image');
const closeModal = document.querySelector('.close');

// 썸네일 클릭 시 모달 띄우기
document.getElementById('qna-file-thumbnail').addEventListener('click', function() {
    modalImage.src = this.src;
    modal.style.display = 'flex';
});

// 모달 닫기
modal.addEventListener('click', function(event) {
    if (event.target === this) {
        this.style.display = 'none';
    }
});

function deleteQna(qnaNo) {
    if(confirm("정말 삭제하시겠습니까?")) {
        location.href = `/qna-delete/${qnaNo}`;
        alert("삭제가 완료되었습니다.");
    }
}

function goToList() { history.go(-1); }

document.addEventListener("DOMContentLoaded", function() {
    document.querySelector('#side-menu-3').classList.add('selected');
})