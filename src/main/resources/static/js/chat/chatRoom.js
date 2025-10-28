// const socketUrl = `ws://localhost:9000/ws/chat`;

let socket = null;

// 새로고침시에 재연결
webSocketConnect();

function webSocketConnect() {
    socket = new WebSocket(userUrl);

    socket.onopen = function () {
        let messageType = firstOrJoin;
        const firstOrJoinMessage = {
            messageType: messageType,
            memberId: memberId,
            roomNo: roomNo,
        };

        if (messageType === 'FIRST') {
            if (socket.readyState === WebSocket.OPEN) {
                socket.send(JSON.stringify(firstOrJoinMessage));
                // db에 저장
                saveMessage(firstOrJoinMessage);
            }
        } else if (messageType === 'JOIN') {
            if (socket.readyState === WebSocket.OPEN) {
                updateOnOffStatus('ON');
                socket.send(JSON.stringify(firstOrJoinMessage));

            }
        }


    }

    socket.onmessage = function (event) {
        const responseJson = (event.data); // 서버로부터 받은 메시지 json
        let message = JSON.parse(responseJson);
        console.log("onmessage : " + event.data)
        console.log(message);

        // WAIT을 보내면 처리
        if (message.messageType === 'WAIT') {
            confirmOnOffStatus();
        } else if (message.messageType === 'JOIN') {
            console.log("onmessage 로 join 받은겨 만겨");
            updateUserList("", message.memberId);
            confirmOnOffStatus();
        } else {
            if (memberId !== message.memberId) {
                displayMessage(message, "other");
            } else {
                displayMessage(message, "my")
            }
        }

        if (message.messageType === 'LEAVE') {
            // db에서 join상태 삭제
            updateUserList("delete", message.memberId);
        }

        if (message.messageType === 'FIRST') {
            // 참여한 유저 리스트 업데이트
            updateUserList("", message.memberId);
        }
    }

    // 들어오면 db에 ON으로 상태변경
    window.addEventListener('load', function () {
        // const waitMessage = {
        //     messageType: 'JOIN',
        //     memberId: memberId,
        //     roomNo: roomNo
        // };
        // if (socket.readyState === WebSocket.OPEN) {
        //     updateOnOffStatus('ON');
        //     socket.send(JSON.stringify(waitMessage));
        //
        // }
        confirmOnOffStatus();
    })

    // 대화방을 잠시 나가면 프로필 이미지 투명하게
    socket.onclose = function (event) {
        console.log("소켓 연결 끊김..")
        // setInterval(function() {
        //     webSocketConnect();  // 2초 후 다시 연결 시도
        // }, 2000);
    }
}

// 메세지 전송
function sendMessage() {
    let messageInput = document.querySelector('.message-input');
    let message = document.querySelector(".message-input").value;

    const talkMessage = {
        messageType: 'TALK',
        memberId: memberId,
        roomNo: roomNo,
        chatContent: message
    };

    if (message.trim() !== '' && socket.readyState === WebSocket.OPEN) {
        // 소켓서버 전달
        socket.send(JSON.stringify(talkMessage));
        // db에 저장
        saveMessage(talkMessage);
        messageInput.value = "";
    }
}

function displayMessage(message, status) {
    let mainScreen = document.querySelector('.main-screen');
    let currentDate = new Date();
    let hours = currentDate.getHours();
    hours = hours - 12 >= 0 ? hours - 12 : hours;
    let minutes = currentDate.getMinutes();
    let ampm = hours >= 12 && hours < 24 ? '오후' : '오전';

    let profileInfo = checkProfile(message);

    if (message.messageType === "TALK") {
        if (status === "my") {
            let myChatBoxDiv = document.createElement('div');
            myChatBoxDiv.setAttribute("class", "my-chat-box");
            myChatBoxDiv.innerHTML = `
                <div class="my-chat-box">
                    <div class="my-chat-date">${ampm} ${hours}:${minutes}</div>
                    <div class="my-chat-message">${message.chatContent}</div>
                </div>
                `;
            mainScreen.appendChild(myChatBoxDiv);
        } else if (status === "other") {
            console.log("other!!")
            let otherChatBoxDiv = document.createElement('div');
            otherChatBoxDiv.setAttribute("class", "other-chat-box");
            otherChatBoxDiv.innerHTML = `
                <div class="other-chat-box">
<!--                profileIno : 함수에서 return 시킨 백틱 태그임.-->
                    <div class="other-chat-profile">
                        ${profileInfo}
                    </div>
                    <div style="max-width: 60%; margin-left: 5px">
                        <div class="other-chat-name">${message.memberId}</div>
                        <div class="other-chat-message">${message.chatContent}</div>
                    </div>
                    <div class="other-chat-date">${ampm} ${hours}:${minutes}</div>
                </div>
                `;
            mainScreen.appendChild(otherChatBoxDiv);
        }
    } else {
        if (message.messageType === "LEAVE") {
            message.chatContent = "님이 대화방을 나갔습니다";
        } else if (message.messageType === "FIRST") {
            message.chatContent = "님이 대화방에 참여했습니다";
        }
        let noticeBoxDiv = document.createElement('div');
        noticeBoxDiv.setAttribute("class", "notice-box");
        noticeBoxDiv.innerHTML = `
                    <div class="notice-box">
                        <div class="notice-message">${message.memberId}${message.chatContent}</div>
                    </div>
                `;
        mainScreen.appendChild(noticeBoxDiv);
    }
    // scroll 하단으로 이동
    scrollBottom();
}


function leaveChatRoom() {
    const leaveMessage = {
        messageType: 'LEAVE',
        memberId: memberId,
        roomNo: roomNo
    };

    if (socket.readyState === WebSocket.OPEN) {
        // 소켓서버 전달
        socket.send(JSON.stringify(leaveMessage));
        // db 저장
        saveMessage(leaveMessage);
    }

    // list로 이동
    location.href = '/chat/list';


}

// 다른 페이지로 이동 시 "WAIT" 메시지 전송
window.addEventListener('beforeunload', function (event) {
    const waitMessage = {
        messageType: 'WAIT',
        memberId: memberId,
        roomNo: roomNo
    };
    if (socket.readyState === WebSocket.OPEN) {
        // 나가면 db에 OFF로 상태변경
        updateOnOffStatus('OFF');
        socket.send(JSON.stringify(waitMessage));

    }
});


function checkProfile(message) {

    let profileCount = 0;
    let profileInfo = null;
    let profileDiv = "";
    joinProfile.forEach(profile => {
        if (profile.memberId === message.memberId) {
            profileCount++;
            profileInfo = profile;
        }
    });
    if (profileCount > 0) {
        profileDiv = `
                <img src="${profileInfo.filePath}${profileInfo.fileRename}" alt="프로필" style="width: 100%; height: 100%; border-radius: 50%;">
                `;
    } else {
        profileDiv =
            `
                        <img alt="프로필" src="/img/default_profile.png" style="width: 100%; height: 100%; border-radius: 50%;">
                        `;
    }

    return profileDiv;
}


// 메세지 저장 로직
function saveMessage(message) {
    // AJAX 요청으로 메시지 저장
    fetch('/chat/insertChat', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(message) // 전송할 데이터를 JSON 형태로 변환
    })
        .then(response => {
            if (!response.ok) {
                throw new Error('메시지 저장 중 에러 발생');
            }
            return response.text(); // 응답을 text 형태로 변환
        })
        .then(data => {
            console.log('메시지가 DB에 저장되었습니다:', data);
        })
        .catch(error => {
            console.error('저장 에러:', error);
        });
}


// 키 다운 이벤트
let messageTextArea = document.querySelector('.message-input');
messageTextArea.addEventListener('keydown', function (event) {
    // Ctrl + Enter를 눌렀을 때 줄바꿈
    if (event.key === 'Enter' && event.ctrlKey) {
        event.preventDefault();
        const cursorPosition = messageTextArea.selectionStart;
        const text = messageTextArea.value;
        messageTextArea.value = text.slice(0, cursorPosition) + '\n' + text.slice(cursorPosition);
        messageTextArea.selectionStart = messageTextArea.selectionEnd = cursorPosition + 1;
    }
    // 그냥 Enter를 눌렀을 때 메시지 전송
    else if (event.key === 'Enter') {
        event.preventDefault();  // 기본 줄바꿈 기능 막기
        sendMessage();  // 메시지 전송 함수 호출
    }
});


// ajax userList 업데이트
function updateUserList(status, memberId) {

    $.ajax({
        url: "/chat/getUserListAndDelete",
        data: {
            roomNo: roomNo,
            memberId: memberId,
            status: status,
            roomWriter: roomWriter
        },

        type: "post",
        success: function (data) {
            console.log(data);
            $('#user-list-container').replaceWith(data);
            confirmOnOffStatus();
        },
        error: function () {
            console.log("error");
        }

    })
}


// scroll 하단으로 이동
scrollBottom();

function scrollBottom() {
    let mainScreen = document.querySelector('.main-screen');
    mainScreen.scrollTop = mainScreen.scrollHeight;
}


function updateOnOffStatus(onOffStatus) {
    $.ajax({
        url: "/chat/updateOnOffStatus",
        data: {
            roomNo: roomNo,
            memberId: memberId,
            onOffStatus: onOffStatus
        },
        type: "post",
        success: function (data) {
            console.log(data);
            console.log("update 성공");
        },
        error: function () {
            console.log("update error");
        }
    })
}

function confirmOnOffStatus() {
    $.ajax({
        url: "/chat/selectOnOffStatus",
        data: {
            roomNo: roomNo,
        },
        dataType: 'json',
        type: "post",
        success: function (response) {
            response.forEach(item => {
                let profileImg = document.querySelector(`#${item.memberId}`);
                if (item.status === 'OFF') {
                    // 프로필 이미지 투명하게 처리 (opacity를 0.3으로 설정)
                    profileImg.style.opacity = '0.3';
                } else {
                    profileImg.style.opacity = '1';
                }
            })

        },
        error: function () {
            console.log("opacity error");
        }
    })
}


// 채팅방 삭제
function deleteChatRoom() {
    if (confirm("정말 삭제하시겠습니까? 삭제 시 나눈 대화 내용은 모두 삭제됩니다.")) {
        location.href = `/chat/deleteRoom?roomNo=${roomNo}`;
    }
}


// 영화 예매 동의 여부 체크 업데이트
function checkAcceptReservation() {
    let acceptCheckBox = document.querySelector("input[name=acceptCheck]");
    console.log(acceptCheckBox.checked);
    let acceptStatus = "";
    if (acceptCheckBox.checked) {
        acceptStatus = "YES";
    } else {
        acceptStatus = "NO";
    }

    $.ajax({
        url: "/chat/updateAcceptStatus",
        data: {
            roomNo: roomNo,
            memberId: memberId,
            acceptStatus: acceptStatus
        },
        type: "post",
        success: function (response) {

        },
        error: function () {
            console.log("accept error");
        }
    })
}

// 최초 입장시 FIRST 전달
// function confirmJoin(){
//     let messageType;
//     $.ajax({
//         url:"/chat/confirmJoin",
//         data:{
//             roomNo:roomNo
//         },
//         type:"post",
//         success:function(data){
//             console.log("data" + data);
//             messageType = data;
//         },
//         error:function(){
//             console.log("first 여부 실패")
//         }
//     })
//
// }

function openReservationWindow(event) {
    event.preventDefault();
    $.ajax({
        url: "/chat/checkAcceptAll",
        data: {
            roomNo: roomNo
        },
        type: "POST",
        dataType: 'json',
        success: function (response) {
            // 상영관 선택 체크
            let screenName = document.querySelector('#screenName').innerText;

            console.log("screenName   " + screenName);
            if (screenName.trim() === "미정") {
                alert("상영관을 먼저 선택해주세요");
                return false;
            }

            // 티켓 유무 확인
            let noTicketMember = "";
            let noTicketCount = 0;
            response.forEach(member => {
                if (member.ticketCount < 1) {
                    noTicketMember += member.memberId + ", ";
                    noTicketCount++;
                }
            })
            if (noTicketCount > 0) {
                noTicketMember = noTicketMember.substring(0, noTicketMember.length - 2);
                alert(noTicketMember + "님이 티켓을 갖고있지 않습니다.");
                return false;
            } else {
                console.log("티켓 모두 갖고있음.")
            }

            let noAcceptMember = "";
            let noAcceptCount = 0;
            console.log("accept 성공!!")
            response.forEach(member => {
                if (member.acceptStatus === "NO") {
                    noAcceptMember += member.memberId + ", ";
                    noAcceptCount++;
                }
            })


            if (noAcceptCount > 0) {
                noAcceptMember = noAcceptMember.substring(0, noAcceptMember.length - 2);
                alert(noAcceptMember + "님이 아직 동의하지 않았습니다.");

                return false;
            } else {
                let acceptMemberIds = "";
                response.forEach(member => {
                    acceptMemberIds += member.memberId + ",";
                })
                acceptMemberIds = acceptMemberIds.substring(0, acceptMemberIds.length - 1);
                let memberIdsInput = document.querySelector('input[name=memberIds]');
                memberIdsInput.value = acceptMemberIds;
                document.querySelector("form").submit();
                console.log("모두 동의하였습니다.")
                return true;
            }

        },
        error: function () {
            console.log("accept 오류")
        }
    })
}

//
function testOpen(roomNo, roomWriter, movieNo, title, posterUrl, cinemaNo, cinemaName, cinemaLocationCode, cinemaAddress, roomCategory) {
    let popupW = 500;
    let popupH = 600;
    let left = Math.ceil((window.screen.width - popupW) / 2);
    let top = Math.ceil((window.screen.height - popupH) / 2);
    if (memberId === roomWriter) {
        // 쿼리스트링 정보 포함하여 팝업창 열기
        window.open(`/chat/finalSelectInfo?roomNo=${roomNo}&roomWriter=${encodeURIComponent(roomWriter)}&movieNo=${movieNo}&title=${encodeURIComponent(title)}&posterUrl=${posterUrl}&cinemaNo=${cinemaNo}&cinemaName=${cinemaName}&cinemaLocationCode=${cinemaLocationCode}&cinemaAddress=${cinemaAddress}&roomCategory=${roomCategory}`
            , "popupWindow", `width=500,height=600,left=${left},top=${top}`);
    } else {
        alert("방장만 선택이 가능합니다.")
    }


}
