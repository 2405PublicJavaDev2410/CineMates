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

function registerReply(cp) {
    if(confirm("답변을 등록합니다.")) {
        const formData = new FormData(document.querySelector('#register-reply-form'));
        const data = {
            parentQnaNo: formData.get('parentQnaNo'),
            content: formData.get('content')
        };
        $.ajax({
            url: '/register-reply',
            method: 'POST',
            data: JSON.stringify(data),
            contentType: 'application/json',
            processData: false,
            success: function(data) {
                if(data === 'success') {
                    window.location.href = `/admin/qna-list?cp=${cp}`;
                }else {
                    alert('등록 실패. 다시 시도해 주세요.');
                }
            },
            error: function() {
                alert('서버 통신 에러!');
            }
        });
    }
}

function goToList() {
    history.go(-1);
}