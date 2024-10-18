console.log(memberIds);

function goPay() {
    if (confirm("결제 하시겠습니까?")) {
        $.ajax({
            url: "/payment/ticket",
            data: {
                memberId: memberIds
            },
            type: "POST",
            success: function (response) {
                console.log("Updated members:", response);
                alert("결제 완료 되었습니다");
                location.href = "/";
            },
            error: function (xhr, status, error) {
                console.error("Error occurred:", error);
                alert("결제 중 오류가 발생했습니다");
            }
        });
    }
}