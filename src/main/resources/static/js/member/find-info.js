const findIdBtn = document.getElementById('find-id-btn');
const findPwdBtn = document.getElementById('find-pwd-btn');
const findIdForm = document.getElementById('find-id-form');
const findPwdForm = document.getElementById('find-pw-form');

const findIdNameInput = document.querySelector('#find-id-name-input');
const findIdEmailInput = document.querySelector('#find-id-email-input')
const nameFailureMsg = document.querySelector('#name-failure-message');
const emailFailureMsg = document.querySelector('#email-failure-message');
const ViewId = document.querySelector(('.show-find-id'));

function findMemberId() {
    if(!nameCheck()) {
        return;
    }
    if(!emailCheck()) {
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
                ViewId.innerHTML = `<span>"${data.name}" 님의 아이디입니다.<br><br></span><span><b>${maskedId}</b></span>`
                ViewId.classList.remove('hide');
            }else {
                emailFailureMsg.innerHTML = '회원 정보가 존재하지 않습니다. 입력한 내용을 확인해주세요.';
                emailFailureMsg.classList.remove('hide');
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
// 이메일 유효성 검사
function emailCheck() {
    if(findIdEmailInput.value.trim() === '') {
        emailFailureMsg.innerHTML = '이메일 주소를 입력하지 않았습니다.';
        emailFailureMsg.classList.remove('hide');
        findIdEmailInput.classList.add('error-border');
        return false;
    }
    if(!emailRule(findIdEmailInput.value)) {
        emailFailureMsg.innerHTML = '유효하지 않은 이메일 주소입니다. 정확한 이메일을 입력해주세요';
        emailFailureMsg.classList.remove('hide');
        findIdEmailInput.classList.add('error-border');
        return false;
    }
    emailFailureMsg.classList.add('hide');
    emailFailureMsg.innerHTML = '';
    findIdEmailInput.classList.remove('error-border');
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