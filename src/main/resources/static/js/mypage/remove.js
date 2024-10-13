document.addEventListener("DOMContentLoaded", function() {
    const pwInput = document.querySelector('#pw-input');
    const pwIcon = document.querySelector('.pwIcon');
    const pwFailureMessage = document.querySelector('#pw-failure-message');

    // 비밀번호 입력 필드와 아이콘이 존재할 경우
    if (pwInput && pwIcon) {
        // 엔터키 눌렀을 때 새로고침 방지
        pwInput.addEventListener('keydown', function (e) {
            if (e.key === 'Enter') {
                e.preventDefault();
            }
        });

        // 비밀번호 마스킹 해제
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
    }

    document.querySelector('#side-menu-5').classList.add('selected');
});

function removeMember(e) {
    e.preventDefault();
    if (!pwCheck()) {
        return;
    }
    if (confirm("정말 탈퇴하시겠습니까?")) {
        const formData = new FormData(document.querySelector('#remove-member-form'));
        $.ajax({
            url: '/remove',
            method: 'POST',
            data: JSON.stringify(Object.fromEntries(formData)), // JSON 문자열로 변환
            contentType: 'application/json',
            dataType: 'text',
            success: function(data) {
                if (data === 'success') {
                    alert("회원 탈퇴가 완료되었습니다.");
                    window.location.href = '/';
                } else {
                    pwFailureMessage.innerHTML = "비밀번호가 일치하지 않습니다.";
                    pwFailureMessage.classList.remove('hide');
                    pwInput.classList.add('error-border');
                }
            },
            error: function() {
                alert('서버 통신 에러!');
                location.reload();
            }
        });
    }
}

function removeNaverMember() {
    if (confirm("정말 탈퇴하시겠습니까?")) {
        $.ajax({
            url: '/naver/remove',
            type: 'POST',
            success: function(data) {
                if (data === 'success') {
                    alert("회원탈퇴가 완료되었습니다.");
                    window.location.href = '/';
                }
            },
            error: function() {
                alert('서버 통신 에러!');
                location.reload();
            }
        });
    }
}

const passwordRule = (str) => {
    return /^(?=.*[A-Z])(?=.*[0-9])(?=.*[!@#$%^*+=-])[A-Za-z0-9!@#$%^*+=-]{8,16}$/.test(str);
}

function pwCheck() {
    const pwInput = document.querySelector('#pw-input');
    const pwFailureMessage = document.querySelector('#pw-failure-message');

    if (!pwInput) return false;

    if (pwInput.value.trim() === '') {
        pwFailureMessage.innerHTML = '비밀번호를 입력해주세요.';
        pwFailureMessage.classList.remove('hide');
        pwInput.classList.add('error-border');
        return false;
    }
    if (!passwordRule(pwInput.value)) {
        pwFailureMessage.innerHTML = '비밀번호를 잘못 입력하셨습니다. 비밀번호를 정확히 입력해 주세요.';
        pwFailureMessage.classList.remove('hide');
        pwInput.classList.add('error-border');
        return false;
    }
    pwFailureMessage.classList.add('hide');
    pwFailureMessage.innerHTML = '';
    pwInput.classList.remove('error-border');
    return true;
}
