function goPay() {
    if (confirm("결제 하시겠습니까?")) {
        $.ajax({
            url: "/payment/ticket",
            data: {
                memberIds: memberIds
            },
            type: "POST",
            dataType: "json",
            success: function (response) {
                console.log("Updated members:", response);
                if (response.length > 0) {
                    alert("결제 완료 되었습니다. 업데이트된 멤버: " + response.join(', '));
                } else {
                    alert("결제는 완료되었지만 티켓 업데이트에 실패했습니다.");
                }
                location.href = "/";
            },
            error: function (xhr, status, error) {
                console.error("Error occurred:", error);
                console.log("Status:", status);
                console.log("Response:", xhr.responseText);
                alert("결제 중 오류가 발생했습니다");
            }
        });
    } else {
        console.log("결제가 취소되었습니다.");
    }
}