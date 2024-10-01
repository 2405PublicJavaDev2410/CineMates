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

const pwIcon = document.querySelector('.pwIcon');
const pwInput = document.querySelector('#memberPw');

// 비밀번호 마스킹 해제 버튼
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
const cpwInput = document.querySelector('#passwordCheck');

cpwIcon.addEventListener('click', function () {
    const type = cpwInput.getAttribute('type') === 'password' ? 'text' : 'password';
    cpwInput.setAttribute('type', type);

    if (type === 'password') {
        this.classList.remove('fi-rr-eye-crossed');
        this.classList.add('fi-rs-eye');
    } else {
        this.classList.remove('fi-rs-eye');
        this.classList.add('fi-rr-eye-crossed');
    }
});