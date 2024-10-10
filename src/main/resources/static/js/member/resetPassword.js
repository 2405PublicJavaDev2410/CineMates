const pwInput = document.querySelector('#pw-input');
const pwCheckInput = document.querySelector('#pw-check-input');
const pwSuccessMsg = document.querySelector('#pw-success-message');
const pwFailureMsg = document.querySelector('#pw-failure-message');
const pwCheckSuccessMsg = document.querySelector('#pw-check-success-message');
const pwCheckFailureMsg = document.querySelector('#pw-check-failure-message');

function resetPassword() {
    if(!pwCheck()) {
        return;
    }
    if(!pwCheckCheck()) {
        return;
    }
    const formData = new FormData(document.querySelector('#reset-member-pw-form'));
    $.ajax({
        url: '/reset-pw',
        method: 'POST',
        data: JSON.stringify(Object.fromEntries(formData)),
        contentType: 'application/json',
        dataType: 'text',
        success: function(data) {
            if(data === 'success') {
                alert('비밀번호 변경이 완료되었습니다. 로그인 화면으로 이동합니다.')
                window.location.href= '/login';
            }else {
                pwCheckFailureMsg.innerHTML =  '올바른 형식의 비밀번호가 아닙니다. 입력한 내용을 확인해주세요.';
                pwCheckFailureMsg.classList.remove('hide');
                pwInput.classList.add('error-border');
                pwCheckInput.classList.add('error-border');
            }
        },
        error: function() {
            alert('서버 통신 에러!');
        }
    })
}

// 비밀번호 정규식
const pwRule = (str) => {
    return /^(?=.*[A-Z])(?=.*[0-9])(?=.*[!@#$%^*+=-])[A-Za-z0-9!@#$%^*+=-]{8,16}$/.test(str);
}
// 비밀번호 & 비밀번호 일치 여부 확인
const isPwMatch = (str1, str2) => {
    return str1 === str2;
}

pwInput.onkeyup = function() {
    pwCheck();
}
pwCheckInput.onkeyup = function() {
    pwCheckCheck();
}

// 비밀번호 유효성 검사
function pwCheck() {
    if(pwInput.value.trim() === '') {
        pwInput.classList.remove('success-border');
        pwFailureMsg.innerHTML = '비밀번호를 입력해주세요.';
        pwSuccessMsg.classList.add('hide');
        pwFailureMsg.classList.remove('hide');
        pwInput.classList.add('error-border');
        return false;
    }
    if(!pwRule(pwInput.value)) {
        pwInput.classList.remove('success-border');
        pwFailureMsg.innerHTML = '대문자, 숫자, 특수문자(!, @, #, $, %, ^, *, =)를 포함한 8~16자리를 입력해주세요';
        pwSuccessMsg.classList.add('hide');
        pwFailureMsg.classList.remove('hide');
        pwInput.classList.add('error-border');
        return false;
    }
    pwFailureMsg.classList.add('hide');
    pwSuccessMsg.classList.remove('hide');
    pwInput.classList.remove('error-border');
    pwSuccessMsg.innerHTML = '사용 가능한 비밀번호입니다.';
    pwInput.classList.add('success-border');
    return true;
}
// 비밀번호 확인 유효성 검사
function pwCheckCheck() {
    if(pwCheckInput.value.trim() === '') {
        pwCheckInput.classList.remove('success-border');
        pwCheckFailureMsg.innerHTML = '비밀번호를 입력해주세요.';
        pwCheckSuccessMsg.classList.add('hide');
        pwCheckFailureMsg.classList.remove('hide');
        pwCheckInput.classList.add('error-border');
        return false;
    }
    if(!isPwMatch(pwInput.value, pwCheckInput.value)) {
        pwCheckInput.classList.remove('success-border');
        pwCheckFailureMsg.innerHTML = '비밀번호와 일치하지 않습니다.';
        pwCheckSuccessMsg.classList.add('hide');
        pwCheckFailureMsg.classList.remove('hide');
        pwCheckInput.classList.add('error-border');
        return false;
    }
    pwCheckFailureMsg.classList.add('hide');
    pwCheckSuccessMsg.classList.remove('hide');
    pwCheckInput.classList.remove('error-border');
    pwCheckSuccessMsg.innerHTML = '입력하신 비밀번호와 일치합니다.';
    pwCheckInput.classList.add('success-border');
    return true;
}

// 입력창을 벗어나면 빨간 테두리 삭제
pwInput.onblur = function() {
    pwInput.classList.remove('error-border');
}
pwCheckInput.onblur = function() {
    pwCheckInput.classList.remove('error-border');
}

// 비밀번호 마스킹 해제 버튼
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

// 비밀번호 확인 마스킹 해제 버튼
const cpwIcon = document.querySelector('.cpwIcon');

cpwIcon.addEventListener('click', function () {
    const type = pwCheckInput.getAttribute('type') === 'password' ? 'text' : 'password';
    pwCheckInput.setAttribute('type', type);

    if (type === 'password') {
        this.classList.remove('fi-rr-eye-crossed');
        this.classList.add('fi-rs-eye');
    } else {
        this.classList.remove('fi-rs-eye');
        this.classList.add('fi-rr-eye-crossed');
    }
});