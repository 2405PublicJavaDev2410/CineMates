const pwInput = document.querySelector('#pw-input');
const pwCheckInput = document.querySelector('#pw-check-input');
const phoneInput = document.querySelector('#phone-input');

const pwSuccessMsg = document.querySelector('#pw-success-message');
const pwFailureMsg = document.querySelector('#pw-failure-message');
const pwCheckSuccessMsg = document.querySelector('#pw-check-success-message');
const pwCheckFailureMsg = document.querySelector('#pw-check-failure-message');
const phoneSuccessMsg = document.querySelector('#phone-success-message');
const phoneFailureMsg = document.querySelector('#phone-failure-message');

function modifyMember() {
    if(!pwCheck()) {
        window.scrollTo({
            top: pwInput.getBoundingClientRect().top + window.pageYOffset - 120,
            behavior: 'smooth'
        });
        return;
    }
    if(!pwCheckCheck()) {
        window.scrollTo({
            top: pwCheckInput.getBoundingClientRect().top + window.pageYOffset - 120,
            behavior: 'smooth'
        });
        return;
    }
    if(!phoneCheck()) {
        window.scrollTo({
            top: phoneInput.getBoundingClientRect().top + window.pageYOffset - 120,
            behavior: 'smooth'
        });
        return;
    }
    if(confirm('회원 정보를 수정하시겠습니까?')) {
        const formData = new FormData(document.querySelector('#update-member-form'));
        $.ajax({
            url: '/modify',
            method: 'POST',
            data: formData,
            processData: false,
            contentType: false,
            success: function (data) {
                if (data === 'success') {
                    alert('정보 수정이 완료되었습니다.');
                    window.location.href = '/my-page/update';
                }
            },
            error: function() {
                alert('서버 통신 에러!');
            }
        })
    }
}

document.addEventListener("DOMContentLoaded", function() {
    document.querySelector('#side-menu-4').classList.add('selected');
})

// 비밀번호 정규식
const pwRule = (str) => {
    return /^(?=.*[A-Z])(?=.*[0-9])(?=.*[!@#$%^*+=-])[A-Za-z0-9!@#$%^*+=-]{8,16}$/.test(str);
}
// 비밀번호 & 비밀번호 일치 여부 확인
const isPwMatch = (str1, str2) => {
    return str1 === str2;
}
// 핸드폰 번호 정규식
const phoneRule = (str) => {
    return /^01[016789][0-9]{3,4}[0-9]{4}$/.test(str);
}

pwInput.onkeyup = function() {
    pwCheck();
}
pwCheckInput.onkeyup = function() {
    pwCheckCheck();
}
phoneInput.onkeyup = function() {
    phoneCheck();
}
// 공백 체크 함수
const hasNoInput = (str) => {
    return str.trim().length === 0;
}

// 비밀번호 유효성 검사
function pwCheck() {
    if(hasNoInput(pwInput.value)) {
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
    if(hasNoInput(pwCheckInput.value)) {
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
// 핸드폰 번호 유효성 검사
function phoneCheck() {
    if(hasNoInput(phoneInput.value)) {
        phoneInput.classList.remove('success-border');
        phoneFailureMsg.innerHTML = '핸드폰 번호를 입력해주세요.';
        phoneSuccessMsg.classList.add('hide');
        phoneFailureMsg.classList.remove('hide');
        phoneInput.classList.add('error-border');
        return false;
    }
    if(!phoneRule(phoneInput.value)) {
        phoneInput.classList.remove('success-border');
        phoneFailureMsg.innerHTML = '핸드폰 번호가 유효하지 않습니다.';
        phoneSuccessMsg.classList.add('hide');
        phoneFailureMsg.classList.remove('hide');
        phoneInput.classList.add('error-border');
        return false;
    }
    phoneFailureMsg.classList.add('hide');
    phoneSuccessMsg.classList.remove('hide');
    phoneInput.classList.remove('error-border');
    phoneSuccessMsg.innerHTML = '유효한 핸드폰 번호입니다.';
    phoneInput.classList.add('success-border');
    return true;
}

function goToRegister() {
    location.href="/register";
}

// 파일 업로드
document.getElementById('profileImg').addEventListener('change', function(event) {
    const file = event.target.files[0];
    const thumbnail = document.getElementById('profile-thumbnail');
    const thumbnailContainer = document.getElementById('thumbnail-container');
    const allowedTypes = ['image/jpeg', 'image/png', 'image/gif'];

    if (file) {
        // 허용되지 않은 파일 형식인 경우 경고창 표시
        if (!allowedTypes.includes(file.type)) {
            alert('JPG, PNG, GIF 형식의 이미지만 업로드할 수 있습니다.');
            event.target.value = '';
            thumbnail.src = '';
            thumbnail.style.display = 'none';
            thumbnailContainer.style.display = 'none';
            return;
        }

        const reader = new FileReader();
        // 썸네일 이미지 표시
        reader.onload = function (e) {
            thumbnail.src = e.target.result;
            thumbnail.style.display = 'block';
            thumbnailContainer.style.display = 'block';
        }
        reader.readAsDataURL(file);
    } else {
        // 파일이 없을 경우 썸네일 숨기기
        thumbnail.src = '';
        thumbnail.style.display = 'none';
        thumbnailContainer.style.display = 'none';
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

// 비밀번호 확인 마스킹 해제
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
