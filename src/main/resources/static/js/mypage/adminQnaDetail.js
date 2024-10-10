function registerReply(qnaNo) {
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
                    window.location.href = `/admin/qna-detail/${qnaNo}`;
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