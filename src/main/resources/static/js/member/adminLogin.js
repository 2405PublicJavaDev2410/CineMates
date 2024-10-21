function loginAdmin() {
    const formData = new FormData(document.querySelector('#login-admin-form'));
    $.ajax({
        url: '/admin/login',
        method: 'POST',
        data: JSON.stringify(Object.fromEntries(formData)),
        contentType: 'application/json',
        dataType: 'text',
        success: function(data) {
            if(data === 'success') {
                window.location.href= '/admin/banner';
            }else if(data === 'fail') {
                alert("관리자 계정만 접속할 수 있습니다!");
            }
        },
        error: function() {
            alert("서버 통신 에러!");
        }
    })
}

// 엔터 누르면 로그인 버튼 동작
document.getElementById('login-admin-form').addEventListener("keydown", function (e) {
    if (e.key === 'Enter') {
        loginAdmin();
    }
});