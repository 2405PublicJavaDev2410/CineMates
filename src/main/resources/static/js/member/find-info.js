const findIdBtn = document.getElementById('find-id-btn');
const findPwdBtn = document.getElementById('find-pwd-btn');
const findIdForm = document.getElementById('find-id-form');
const findPwdForm = document.getElementById('find-pw-form');

// 버튼 클릭 시 해당 폼만 보이게 하며, 선택된 버튼과 미선택된 버튼 색상으로 구분
findIdBtn.addEventListener('click', selectFindId);
findPwdBtn.addEventListener('click', selectFindPwd);

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