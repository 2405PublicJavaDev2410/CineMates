const pwInput = document.querySelector('#pw-input');
const pwFailureMessage = document.querySelector('#pw-failure-message');

function removeMember() {
    if(!pwCheck()) {
        return;
    }
    if(confirm("정말 탈퇴하시겠습니까?")) {
        // form 데이터를 자바스크립트 객체로 변환 후 서버에 전달
        const formData = new FormData(document.querySelector('#remove-member-form'));
        $.ajax({
            url: '/remove',
            method: 'post',
            data: JSON.stringify(Object.fromEntries(formData)), // JSON 문자열로 변환
            contentType: 'application/json', // Content-Type 설정
            dataType: 'text', // 서버로부터의 응답 데이터 타입
            success: function(data) {
                if(data === 'success') {
                    alert("회원 탈퇴가 완료되었습니다.");
                    window.location.href='/';
                }else {
                    pwFailureMessage.innerHTML = "비밀번호가 일치하지 않습니다.";
                    pwFailureMessage.classList.remove('hide');
                    pwInput.classList.add('error-border');
                }
            },
            error: function() {
                alert('서버 통신 에러!');
                location.reload();
            }
        })
    }
}

// 비밀번호 정규식
const passwordRule = (str) => {
    return /^(?=.*[A-Z])(?=.*[0-9])(?=.*[!@#$%^*+=-])[A-Za-z0-9!@#$%^*+=-]{8,16}$/.test(str);
}

function pwCheck() {
    if (pwInput.value.trim() === '') {
        pwFailureMessage.innerHTML = '비밀번호를 입력해주세요.';
        pwFailureMessage.classList.remove('hide');
        pwInput.classList.add('error-border');
        return false;
    }
    if(!passwordRule(pwInput.value)) {
        pwFailureMessage.innerHTML = '비밀번호를 잘못 입력하셨습니다. 비밀번호를 정확히 입력해 주세요.'
        pwFailureMessage.classList.remove('hide');
        pwInput.classList.add('error-border');
        return false;
    }
    pwFailureMessage.classList.add('hide');
    pwFailureMessage.innerHTML = '';
    pwInput.classList.remove('error-border');
    return true;
}

// 엔터키 눌렀을 때 새로고침 방지
pwInput.addEventListener('keydown', function (e) {
    if (e.key === 'Enter') {
        e.preventDefault();
    }
});

// 비밀번호 마스킹 해제
const pwIcon = document.querySelector('.pwIcon');

pwIcon.addEventListener('click', function () {
    const type = pwInput.getAttribute('type') === 'password' ? 'text' : 'password';
    pwInput.setAttribute('type', type);

    if (type === 'password') {
        this.classList.remove('fi-rr-eye-crossed');
        this.classList.add('fi-rs-eye');
    } else {
        this.classList.remove('fi-rs-eye');
        this.classList.add('fi-rr-eye-crossed');
    }
});

document.addEventListener("DOMContentLoaded", function() {
    document.querySelector('#side-menu-5').classList.add('selected');
})