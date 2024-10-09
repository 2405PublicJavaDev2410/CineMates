const qnaTitle = document.querySelector('#qna-title');
const qnaContent = document.querySelector('#qna-content');
const tFailureMsg = document.querySelector('#title-failure-message');
const cFailureMsg = document.querySelector('#content-failure-message');

function qnaRegister(e) {
    e.preventDefault();
    if (qnaTitle.value.trim() === '') {
        tFailureMsg.innerHTML = '제목을 입력해주세요.';
        tFailureMsg.classList.remove('hide');
        qnaTitle.classList.add('error-border');
        return;
    }
    if (qnaContent.value.trim() === '') {
        cFailureMsg.innerHTML = '내용을 입력해주세요.';
        cFailureMsg.classList.remove('hide');
        qnaContent.classList.add('error-border');
        return;
    }
    if(confirm("문의를 등록하시겠습니까? 한번 등록된 문의는 삭제만 가능합니다.")) {
        const formData = new FormData(document.querySelector('#register-qna-form'));
        for (const [key, value] of formData.entries()) {
            console.log(key, value);
        }
        $.ajax({
            url: '/qna-register',
            method: 'POST',
            data: formData,
            processData: false,
            contentType: false,
            success: function(data) {
                if(data === 'success') {
                    alert('등록이 완료되었습니다.');
                    window.location.href = '/my-page/qna-list';
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

document.getElementById('qnaFile').addEventListener('change', function(event) {
    const file = event.target.files[0];
    const thumbnail = document.getElementById('qna-file-thumbnail');
    const allowedTypes = ['image/jpeg', 'image/png', 'image/gif'];

    if (file) {
        // 허용되지 않은 파일 형식인 경우 경고창 표시
        if (!allowedTypes.includes(file.type)) {
            alert('JPG, PNG, GIF 형식의 이미지만 업로드할 수 있습니다.');
            event.target.value = '';
            thumbnail.style.display = 'none';
            return;
        }

        const reader = new FileReader();
        // 썸네일 이미지 표시
        reader.onload = function (e) {
            thumbnail.src = e.target.result;
            thumbnail.style.display = 'block';
        }
        reader.readAsDataURL(file);
    } else {
        // 파일이 없을 경우 썸네일 숨기기
        thumbnail.src = '';
        thumbnail.style.display = 'none';
    }
})

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

document.addEventListener("DOMContentLoaded", function() {
    document.querySelector('#side-menu-3').classList.add('selected');
})