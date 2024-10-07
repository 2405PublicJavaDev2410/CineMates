const findIdBtn = document.getElementById('find-id-btn');
const findPwdBtn = document.getElementById('find-pwd-btn');
const findIdForm = document.getElementById('find-id-form');
const findPwdForm = document.getElementById('find-pw-form');

// 아이디 찾기 관련
const findIdNameInput = document.querySelector('#find-id-name-input');
const findIdEmailInput = document.querySelector('#find-id-email-input')
const nameFailureMsg = document.querySelector('#name-failure-message');
const findIdEmailFailureMsg = document.querySelector('#find-id-email-failure-message');
const ViewId = document.querySelector(('.show-find-id'));

// 비밀번호 찾기 관련
const findPwIdInput = document.querySelector('#find-pw-id-input');
const findPwEmailInput = document.querySelector('#find-pw-email-input');
const idFailureMsg = document.querySelector('#id-failure-message');
const findPwEmailFailureMsg = document.querySelector('#find-pw-email-failure-message');
const ViewResetPw = document.querySelector(('.show-find-pw'));

function findMemberPw() {
    if(!idCheck()) {
        return;
    }
    if(!emailCheckForPw()) {
        return;
    }
    const formData = new FormData(document.querySelector('#find-member-pw-form'));
    console.log(Object.fromEntries(formData));
    $.ajax({
        url: '/send-reset-link',
        method: 'post',
        data: JSON.stringify(Object.fromEntries(formData)),
        contentType: 'application/json',
        dataType: 'text',
        success: function(response) {
            if(response === 'success') {
                ViewResetPw.innerHTML = `<span>비밀번호 재설정 링크가 이메일로 전송되었습니다.</span><br><span>이메일의 링크를 클릭하여 비밀번호를 재설정하세요.</span>`
                ViewResetPw.classList.remove('hide');
            }else if(response === 'fail') {
                findPwEmailFailureMsg.innerHTML = '아이디 또는 이메일이 일치하지 않습니다. 입력한 내용을 확인해주세요.';
                findPwEmailFailureMsg.classList.remove('hide');
                findPwIdInput.classList.add('error-border');
                findPwEmailInput.classList.add('error-border');
            }
        },
        error: function() {
            alert('서버 통신 에러!');
        }
    })
}

function findMemberId() {
    if(!nameCheck()) {
        return;
    }
    if(!emailCheckForId()) {
        return;
    }
    const formData = new FormData(document.querySelector('#find-member-id-form'));
    $.ajax({
        url: '/find-id',
        method: 'post',
        data: JSON.stringify(Object.fromEntries(formData)),
        contentType: 'application/json',
        dataType: 'JSON',
        success: function(data) {
            // && 연산자를 써서 data가 있을 경우에만 memberId에 접근. 없으면 else 구문 실행
            if(data && data.memberId) {
                const maskedId = maskMemberId(data.memberId);
                ViewId.innerHTML = `<span>"${data.name}" 님의 아이디입니다.</span><br><br><span><b>${maskedId}</b></span>`
                ViewId.classList.remove('hide');
            }else {
                findIdEmailFailureMsg.innerHTML = '회원 정보가 존재하지 않습니다. 입력한 내용을 확인해주세요.';
                findIdEmailFailureMsg.classList.remove('hide');
                findIdNameInput.classList.add('error-border');
                findIdEmailInput.classList.add('error-border');
            }
        },
        error: function() {
            alert('서버 통신 에러!');
        }
    })
}

// 이름 정규식
const nameRule = (str) => {
    return /^[가-힣]{2,5}$/.test(str);
}
// 아이디 정규식
const idRule = (str) => {
    return /^[A-Za-z0-9]{5,10}$/.test(str);
}
// 이메일 정규식
const emailRule = (str) => {
    return /^[a-zA-Z0-9+-_.]+@[a-zA-Z0-9-]+(\.[a-zA-Z]{2,})+$/.test(str);
}

// 이름 유효성 검사
function nameCheck() {
    if(findIdNameInput.value.trim() === '') {
        nameFailureMsg.innerHTML = '이름을 입력하지 않았습니다.';
        nameFailureMsg.classList.remove('hide');
        findIdNameInput.classList.add('error-border');
        return false;
    }
    if(!nameRule(findIdNameInput.value)) {
        nameFailureMsg.innerHTML = '유효하지 않은 이름입니다. 정확한 이름을 입력해주세요';
        nameFailureMsg.classList.remove('hide');
        findIdNameInput.classList.add('error-border');
        return false;
    }
    nameFailureMsg.classList.add('hide');
    nameFailureMsg.innerHTML = '';
    findIdNameInput.classList.remove('error-border');
    return true;
}
// 아이디 유효성 검사
function idCheck() {
    if(findPwIdInput.value.trim() === '') {
        idFailureMsg.innerHTML = '아이디를 입력하지 않았습니다.';
        idFailureMsg.classList.remove('hide');
        findPwIdInput.classList.add('error-border');
        return false;
    }
    if(!idRule(findPwIdInput.value)) {
        idFailureMsg.innerHTML = '유효하지 않은 아이디입니다. 정확한 아이디를 입력해주세요.';
        idFailureMsg.classList.remove('hide');
        findPwIdInput.classList.add('error-border');
        return false;
    }
    idFailureMsg.classList.add('hide');
    idFailureMsg.innerHTML = '';
    findPwIdInput.classList.remove('error-border');
    return true;
}
// 아이디 찾기 이메일 유효성 검사
function emailCheckForId() {
    if(findIdEmailInput.value.trim() === '') {
        findIdEmailFailureMsg.innerHTML = '이메일 주소를 입력하지 않았습니다.';
        findIdEmailFailureMsg.classList.remove('hide');
        findIdEmailInput.classList.add('error-border');
        return false;
    }
    if(!emailRule(findIdEmailInput.value)) {
        findIdEmailFailureMsg.innerHTML = '유효하지 않은 이메일 주소입니다. 정확한 이메일을 입력해주세요';
        findIdEmailFailureMsg.classList.remove('hide');
        findIdEmailInput.classList.add('error-border');
        return false;
    }
    findIdEmailFailureMsg.classList.add('hide');
    findIdEmailFailureMsg.innerHTML = '';
    findIdEmailInput.classList.remove('error-border');
    return true;
}
// 비밀번호 찾기 이메일 유효성 검사
function emailCheckForPw() {
    if(findPwEmailInput.value.trim() === '') {
        findPwEmailFailureMsg.innerHTML = '이메일 주소를 입력하지 않았습니다.';
        findPwEmailFailureMsg.classList.remove('hide');
        findPwEmailInput.classList.add('error-border');
        return false;
    }
    if(!emailRule(findPwEmailInput.value)) {
        findPwEmailFailureMsg.innerHTML = '유효하지 않은 이메일 주소입니다. 정확한 이메일을 입력해주세요';
        findPwEmailFailureMsg.classList.remove('hide');
        findPwEmailInput.classList.add('error-border');
        return false;
    }
    findPwEmailFailureMsg.classList.add('hide');
    findPwEmailFailureMsg.innerHTML = '';
    findPwEmailInput.classList.remove('error-border');
    return true;
}

// 아이디 마스킹 처리
function maskMemberId(memberId) {
    return memberId.replace(/(?<=^.{1}).(?=.{2})|(?<=^.{2}).(?=.{1})/g, '*');
}

// 입력창을 벗어나면 빨간 테두리 삭제
findIdNameInput.onblur = function() {
    findIdNameInput.classList.remove('error-border');
}
findIdEmailInput.onblur = function() {
    findIdEmailInput.classList.remove('error-border');
}
findPwIdInput.onblur = function() {
    findIdEmailInput.classList.remove('error-border');
}
findPwEmailInput.onblur = function() {
    findIdEmailInput.classList.remove('error-border');
}

// 버튼 클릭 시 해당 폼만 보이게 하며, 선택된 버튼과 미선택된 버튼 색상으로 구분
findIdBtn.addEventListener('click', selectFindId);
findPwdBtn.addEventListener('click', selectFindPwd);

// 아이디 찾기, 비밀번호 찾기 버튼
function selectFindId() {
    findIdForm.style.display = 'block';
    findPwdForm.style.display = 'none';
    findIdBtn.classList.remove('unselected');
    findIdBtn.classList.add('selected');
    findPwdBtn.classList.remove('selected');
    findPwdBtn.classList.add('unselected');
};
function selectFindPwd() {
    findPwdForm.style.display = 'block';
    findIdForm.style.display = 'none';
    findPwdBtn.classList.remove('unselected');
    findPwdBtn.classList.add('selected');
    findIdBtn.classList.remove('selected');
    findIdBtn.classList.add('unselected');
};

// 로그인 페이지로 이동 버튼 클릭 시
function goToLogin() {
    location.href = '/login';
}

// 페이지 로드 시 "아이디 찾기" 버튼 클릭 상태로 설정
window.onload = function() {
    selectFindId();
};