const pwInput = document.querySelector('#pw-input');
const pwFailureMessage = document.querySelector('#pw-failure-message');

const deleteMember = () => {
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
                alert(data);
                window.location.href='/';
            },
            error: function(xhr) {
                // xhr.responseText에서 오류 메시지를 가져옴
                pwFailureMessage.innerHTML = xhr.responseText
                pwFailureMessage.classList.remove('hide');
                pwInput.classList.add('error-border');
            }
        })
    }
}


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

// 비밀번호 정규표현식
function passwordRule(str) {
    const regex = /^(?=.*[a-zA-Z])(?=.*[!@#$%^*+=-])(?=.*[0-9]).{8,16}$/; // 정규 표현식
    return regex.test(str);
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

// 재입력을 위해 입력창을 클릭하면 빨간색 테두리 지워짐
pwInput.onkeyup = function() {
    pwInput.classList.remove('error-border');
}

document.addEventListener("DOMContentLoaded", function() {
    document.querySelector('#side-menu-5').classList.add('selected');
})