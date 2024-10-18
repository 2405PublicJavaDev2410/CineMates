function goPay() {
    if (confirm("결제 하시겠습니까?")) {
        $.ajax({
            url: "/payment/ticket",
            data: {
                memberId: memberId
            },
            type: "POST"
        })
        alert("결제 완료 되었습니다");
        location.href = "/";
    }
}