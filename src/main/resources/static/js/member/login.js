const idInput = document.querySelector('#id-input');
const pwInput = document.querySelector('#pw-input');
const failureMessage = document.querySelector('#failure-message');

function loginMember() {
    if(!idCheck()) {
        return;
    }
    const formData = new FormData(document.querySelector('#login-member-form'));
    $.ajax({
        url: '/login',
        method: 'post',
        data: JSON.stringify(Object.fromEntries(formData)),
        contentType: 'application/json',
        dataType: 'text',
        success: function(data) {
            if(data === 'success') {
                window.location.href= '/';
            }else {
                failureMessage.innerHTML = '아이디 또는 비밀번호를 잘못 입력하셨습니다. 아이디와 비밀번호를 정확히 입력해 주세요.';
                failureMessage.classList.remove('hide');
                idInput.classList.add('error-border');
                pwInput.classList.add('error-border');
            }
        },
        error: function() {
            alert('서버 통신 에러!');
        }
    })
}

// 아이디 정규식
const idRule = (str) => {
    return /^[A-Za-z0-9]{5,10}$/.test(str);
}

// 비밀번호 정규식
const passwordRule = (str) => {
    return /^(?=.*[A-Z])(?=.*[0-9])(?=.*[!@#$%^*+=-])[A-Za-z0-9!@#$%^*+=-]{8,16}$/.test(str);
}

function idCheck() {
    if(idInput.value.trim() === '' || pwInput.value.trim() === '') {
        failureMessage.innerHTML = '아이디 또는 비밀번호를 입력하지 않았습니다.';
        failureMessage.classList.remove('hide');
        idInput.classList.add('error-border');
        pwInput.classList.add('error-border');
        return false;
    }
    if(!passwordRule(pwInput.value) || !idRule(idInput.value)) {
        failureMessage.innerHTML = '아이디 또는 비밀번호를 잘못 입력하셨습니다. 아이디와 비밀번호를 정확히 입력해 주세요.';
        failureMessage.classList.remove('hide');
        idInput.classList.add('error-border');
        pwInput.classList.add('error-border');
        return false;
    }
    failureMessage.classList.add('hide');
    failureMessage.innerHTML = '';
    idInput.classList.remove('error-border');
    pwInput.classList.remove('error-border');
    return true;
}

// 입력창을 벗어나면 빨간 테두리 삭제
idInput.onblur = function() {
    idInput.classList.remove('error-border');
}
pwInput.onblur = function() {
    pwInput.classList.remove('error-border');
}

// 엔터 누르면 로그인 버튼 동작
document.getElementById('login-member-form').addEventListener("keydown", function (e) {
    if (e.key === 'Enter') {
        loginMember();
    }
});