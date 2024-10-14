// 각 입력창에 대한 객체 생성
const idInput = document.querySelector('#id-input');
const pwInput = document.querySelector('#pw-input');
const pwCheckInput = document.querySelector('#pw-check-input');
const nameInput = document.querySelector('#name-input');
const birthDateInput = document.querySelector('#birth-date-input');
const emailInput = document.querySelector('#email-input');
const verCodeInput = document.querySelector('#ver-code-input');
const phoneInput = document.querySelector('#phone-input');

// 각 입력창의 메시지에 대한 객체 생성
const idSuccessMsg = document.querySelector('#id-success-message');
const idFailureMsg = document.querySelector('#id-failure-message');
const pwSuccessMsg = document.querySelector('#pw-success-message');
const pwFailureMsg = document.querySelector('#pw-failure-message');
const pwCheckSuccessMsg = document.querySelector('#pw-check-success-message');
const pwCheckFailureMsg = document.querySelector('#pw-check-failure-message');
const nameSuccessMsg = document.querySelector('#name-success-message');
const nameFailureMsg = document.querySelector('#name-failure-message');
const birthDateSuccessMsg = document.querySelector('#birth-date-success-message');
const birthDateFailureMsg = document.querySelector('#birth-date-failure-message');
const emailSuccessMsg = document.querySelector('#email-success-message');
const emailFailureMsg = document.querySelector('#email-failure-message');
const verCodeSuccessMsg = document.querySelector('#ver-code-success-message');
const verCodeFailureMsg = document.querySelector('#ver-code-failure-message');
const phoneSuccessMsg = document.querySelector('#phone-success-message');
const phoneFailureMsg = document.querySelector('#phone-failure-message');

// 인증번호 발송
function sendCode() {
    if(!emailCheck()) {
        return;
    }

    // 이메일 중복 체크
    if (emailInput.classList.contains('error-border')) {
        emailInput.focus(); // 중복된 이메일인 경우 포커스 이동
        alert('중복된 이메일입니다. 다른 이메일을 사용해 주세요.');
        return;
    }

    const email = emailInput.value;
    $.ajax({
        url: '/send-verification-code',
        method: 'POST',
        contentType: 'application/json',
        data: JSON.stringify({ email : email }),
        success: function(data) {
            if(data.success) {
                alert(data.message);
            }else {
                alert(data.message);
            }
        },
        error: function () {
            alert('서버 통신 에러!');
        }
    })
}

// 인증번호 인증 여부를 체크하는 변수
let isCodeVerified = false;
// 인증번호 인증
function verifyCode() {
    const email = emailInput.value;
    const verificationCode = verCodeInput.value;

    $.ajax({
        url: '/verify-code',
        method: 'POST',
        contentType: 'application/json',
        data: JSON.stringify({
            email: email,
            verificationCode: verificationCode
        }),
        success: function(data) {
            if (data.success) {
                isCodeVerified = true;
                verCodeFailureMsg.classList.add('hide');
                verCodeSuccessMsg.classList.remove('hide');
                verCodeInput.classList.remove('error-border');
                verCodeSuccessMsg.innerHTML = '인증이 완료되었습니다.';
                verCodeInput.classList.add('success-border');
            } else {
                verCodeInput.classList.remove('success-border');
                verCodeFailureMsg.innerHTML = '인증번호가 일치하지 않습니다.';
                verCodeSuccessMsg.classList.add('hide');
                verCodeFailureMsg.classList.remove('hide');
                verCodeInput.classList.add('error-border');
            }
        },
        error: function() {
            alert('서버 통신 에러!');
        }
    });
}

// 회원가입
function registerMember() {
    const errorFields = [];

    if (!idCheck()) {
        errorFields.push(idInput);
    }
    if (!pwCheck()) {
        errorFields.push(pwInput);
    }
    if (!pwCheckCheck()) {
        errorFields.push(pwCheckInput);
    }
    if (!nameCheck()) {
        errorFields.push(nameInput);
    }
    if (!birthDateCheck()) {
        errorFields.push(birthDateInput);
    }
    if (!emailCheck()) {
        errorFields.push(emailInput);
    }
    if (!verifyCodeCheck()) {
        errorFields.push(verCodeInput);
    }
    if (!phoneCheck()) {
        errorFields.push(phoneInput);
    }
    checkDuplicateEmail((isAvailable) => {
        if (!isAvailable) {
            if (errorFields.length === 0) {
                errorFields.push(emailInput);
            }
            if (errorFields.length > 0) {
                // 첫 번째 에러가 있는 필드로 스크롤
                errorFields[0].scrollIntoView({ behavior: 'smooth', block: 'start' });
            }
            return; // 회원가입 처리 중단
        }
        if (errorFields.length > 0) {
            errorFields[0].scrollIntoView({ behavior: 'smooth', block: 'start' });
            return;
        }
        const formData = new FormData(document.querySelector('#register-member-form'));
        console.log(formData);
        $.ajax({
            url: '/register',
            method: 'POST',
            data: formData,
            processData: false, // FormData를 문자열로 변환 X
            contentType: false, // 브라우저가 알아서 Content-Type 설정하도록 함
            success: function(data) {
                if (data === 'success') {
                    alert("회원가입이 완료되었습니다.");
                    window.location.href = '/login';
                }
            },
            error: function() {
                alert('서버 통신 에러!');
            }
        })
    });
}

// 아이디 정규식
const idRule = (str) => {
    return /^(?=.*[A-Za-z])(?=.*[0-9])[A-Za-z0-9]{5,10}$/.test(str);
}
// 아이디 길이
const idLengthRule = (str) => {
    return str.length >=5 && str.length <= 10;
}
// 비밀번호 정규식
const pwRule = (str) => {
    return /^(?=.*[A-Z])(?=.*[0-9])(?=.*[!@#$%^*+=-])[A-Za-z0-9!@#$%^*+=-]{8,16}$/.test(str);
}
// 비밀번호 & 비밀번호 일치 여부 확인
const isPwMatch = (str1, str2) => {
    return str1 === str2;
}
// 이름 정규식
const nameRule = (str) => {
    return /^[가-힣]{2,5}$/.test(str);
}
// 생년월일 정규식
const birthDateRule = (str) => {
    return /^(19[0-9][0-9]|20\d{2})(0[0-9]|1[0-2])(0[1-9]|[1-2][0-9]|3[0-1])$/.test(str);
}
// 이메일 정규식
const emailRule = (str) => {
    return /^[a-zA-Z0-9+-_.]+@[a-zA-Z0-9-]+(\.[a-zA-Z]{2,})+$/.test(str);
}
// 핸드폰 번호 정규식
const phoneRule = (str) => {
    return /^01[016789][0-9]{3,4}[0-9]{4}$/.test(str);
}

idInput.onkeyup = function() {
    idCheck();
    checkDuplicateId();
}
pwInput.onkeyup = function() {
    pwCheck();
}
pwCheckInput.onkeyup = function() {
    pwCheckCheck();
}
nameInput.onkeyup = function() {
    nameCheck();
}
birthDateInput.onkeyup = function() {
    birthDateCheck();
}
emailInput.onkeyup = function() {
    emailCheck();
}
phoneInput.onkeyup = function() {
    phoneCheck();
}

// 공백 체크 함수
const hasNoInput = (str) => {
    return str.trim().length === 0;
}

// 아이디 중복 검사
const  checkDuplicateId = () => {
    const id = idInput.value;

    // 아이디가 유효하지 않으면 서버 요청 생략
    if(hasNoInput(id) || !idRule(id)) {
        return;
    }

    $.ajax({
        url: '/checkId',
        method: 'GET',
        data: {memberId : id},
        success: function (response) {
            if(response === 'duplicated') {
                idInput.classList.remove('success-border');
                idFailureMsg.innerHTML = '이미 사용중인 아이디입니다.';
                idSuccessMsg.classList.add('hide');
                idFailureMsg.classList.remove('hide');
                idInput.classList.add('error-border');
            }else if(response === 'available'){
                idFailureMsg.classList.add('hide');
                idSuccessMsg.classList.remove('hide');
                idInput.classList.remove('error-border');
                idSuccessMsg.innerHTML = '사용 가능한 아이디입니다.';
                idInput.classList.add('success-border');
            }
        },
        error: function() {
            alert('서버 통신 에러!');
        }
    });
};

// 이메일 중복 검사
const  checkDuplicateEmail = (callback) => {
    const email = emailInput.value;

    // 아이디가 유효하지 않으면 서버 요청 생략
    if(!emailRule(email)) {
        return callback(false);
    }

    $.ajax({
        url: '/checkEmail',
        method: 'GET',
        data: {email : email},
        success: function (response) {
            if(response === 'duplicated') {
                emailInput.classList.remove('success-border');
                emailFailureMsg.innerHTML = '이미 사용중인 이메일입니다.';
                emailSuccessMsg.classList.add('hide');
                emailFailureMsg.classList.remove('hide');
                emailInput.classList.add('error-border');
                callback(false);
            }else if(response === 'available'){
                emailFailureMsg.classList.add('hide');
                emailSuccessMsg.classList.remove('hide');
                emailInput.classList.remove('error-border');
                emailSuccessMsg.innerHTML = '사용 가능한 이메일입니다.';
                emailInput.classList.add('success-border');
                callback(true);
            }
        },
        error: function() {
            alert('서버 통신 에러!');
            callback(false);
        }
    });
};

// 아이디 유효성 검사
function idCheck() {
    if(hasNoInput(idInput.value)) {
        idInput.classList.remove('success-border');
        idFailureMsg.innerHTML = '아이디를 입력해주세요.';
        idSuccessMsg.classList.add('hide');
        idFailureMsg.classList.remove('hide');
        idInput.classList.add('error-border');
        return false;
    }
    if(!idRule(idInput.value)) {
        idInput.classList.remove('success-border');
        idFailureMsg.innerHTML = '유효하지 않은 아이디입니다.';
        idSuccessMsg.classList.add('hide');
        idFailureMsg.classList.remove('hide');
        idInput.classList.add('error-border');
        return false;
    }else if(!idLengthRule(idInput.value)) {
        idInput.classList.remove('success-border');
        idFailureMsg.innerHTML = '아이디는 최소 5자 이상이어야 합니다.';
        idSuccessMsg.classList.add('hide');
        idFailureMsg.classList.remove('hide');
        idInput.classList.add('error-border');
        return false;
    }
    idFailureMsg.classList.add('hide');
    idSuccessMsg.classList.remove('hide');
    idInput.classList.remove('error-border');
    idSuccessMsg.innerHTML = '사용 가능한 아이디입니다.';
    idInput.classList.add('success-border');
    return true;
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
// 이름 유효성 검사
function nameCheck() {
    if(hasNoInput(nameInput.value)) {
        nameInput.classList.remove('success-border');
        nameFailureMsg.innerHTML = '이름을 입력해주세요.';
        nameSuccessMsg.classList.add('hide');
        nameFailureMsg.classList.remove('hide');
        nameInput.classList.add('error-border');
        return false;
    }
    if(!nameRule(nameInput.value)) {
        nameInput.classList.remove('success-border');
        nameFailureMsg.innerHTML = '이름이 유효하지 않습니다.';
        nameSuccessMsg.classList.add('hide');
        nameFailureMsg.classList.remove('hide');
        nameInput.classList.add('error-border');
        return false;
    }
    nameFailureMsg.classList.add('hide');
    nameSuccessMsg.classList.remove('hide');
    nameInput.classList.remove('error-border');
    nameSuccessMsg.innerHTML = '유효한 이름입니다.';
    nameInput.classList.add('success-border');
    return true;
}
// 생년월일 유효성 검사
function birthDateCheck() {
    if(hasNoInput(birthDateInput.value)) {
        birthDateInput.classList.remove('success-border');
        birthDateFailureMsg.innerHTML = '생년월일 8자리를 입력해주세요.';
        birthDateSuccessMsg.classList.add('hide');
        birthDateFailureMsg.classList.remove('hide');
        birthDateInput.classList.add('error-border');
        return false;
    }
    if(!birthDateRule(birthDateInput.value)) {
        birthDateInput.classList.remove('success-border');
        birthDateFailureMsg.innerHTML = '생년월일이 유효하지 않습니다.';
        birthDateSuccessMsg.classList.add('hide');
        birthDateFailureMsg.classList.remove('hide');
        birthDateInput.classList.add('error-border');
        return false;
    }
    birthDateFailureMsg.classList.add('hide');
    birthDateSuccessMsg.classList.remove('hide');
    birthDateInput.classList.remove('error-border');
    birthDateSuccessMsg.innerHTML = '유효한 생년월일입니다.';
    birthDateInput.classList.add('success-border');
    return true;
}
// 이메일 유효성 검사
function emailCheck() {
    if(hasNoInput(emailInput.value)) {
        emailInput.classList.remove('success-border');
        emailFailureMsg.innerHTML = '이메일을 입력해주세요.';
        emailSuccessMsg.classList.add('hide');
        emailFailureMsg.classList.remove('hide');
        emailInput.classList.add('error-border');
        return false;
    }
    if(!emailRule(emailInput.value)) {
        emailInput.classList.remove('success-border');
        emailFailureMsg.innerHTML = '이메일 주소가 유효하지 않습니다.';
        emailSuccessMsg.classList.add('hide');
        emailFailureMsg.classList.remove('hide');
        emailInput.classList.add('error-border');
        return false;
    }
    emailFailureMsg.classList.add('hide');
    emailSuccessMsg.classList.remove('hide');
    emailInput.classList.remove('error-border');
    emailSuccessMsg.innerHTML = '유효한 이메일 주소입니다.';
    emailInput.classList.add('success-border');
    return true;
}
// 인증번호 유효성 검사
function verifyCodeCheck() {
    if(!isCodeVerified
            || verCodeInput.value.trim() === ''
            || verCodeInput.value.trim().length !== 6) {
        verCodeInput.classList.remove('success-border');
        verCodeFailureMsg.innerHTML = '이메일 인증이 완료되지 않았습니다.';
        verCodeSuccessMsg.classList.add('hide');
        verCodeFailureMsg.classList.remove('hide');
        verCodeInput.classList.add('error-border');
        return false;
    }
    verCodeFailureMsg.classList.add('hide');
    verCodeSuccessMsg.classList.remove('hide');
    verCodeInput.classList.remove('error-border');
    verCodeSuccessMsg.innerHTML = '이메일 인증이 완료되었습니다.';
    verCodeInput.classList.add('success-border');
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

const inputFields = [
    idInput,
    pwInput,
    pwCheckInput,
    nameInput,
    birthDateInput,
    emailInput,
    verCodeInput,
    phoneInput
];

// 입력창 클릭 시 빨간 테두리 지우기
inputFields.forEach(inputField => {
    inputField.addEventListener('keydown', () => removeErrorBorder(inputField));
});

// 재입력을 위해 입력창을 클릭하면 빨간색 테두리 지워짐
function removeErrorBorder(inputElement) {
    inputElement.classList.remove('error-border');
}

// 파일 업로드
document.getElementById('profileImg').addEventListener('change', function(event) {
    const file = event.target.files[0];
    const thumbnail = document.getElementById('profile-thumbnail');
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