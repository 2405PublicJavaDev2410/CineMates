// list -> card 전환 function
function switchToCardView() {
    const listItems = document.querySelectorAll('.list-item');
    const cardItems = document.querySelectorAll('.card-item');
    const listContainer = document.querySelector('#toList-container');
    const toListBtn = document.querySelector('#toList-btn');
    const toCardBtn = document.querySelector('#toCard-btn');

    // positionbox data-mode 값 변경 (컨테이너 크기 동적으로 바꾸기 위함)
    let positionBox = document.querySelector('#position-box');
    positionBox.setAttribute('data-mode', 'card');

    // 버튼 활성화 시 클래스 추가 (활성화 스타일 적용)
    toListBtn.classList.remove('active');
    toCardBtn.classList.add('active');


    const url = new URL(window.location);
    url.searchParams.set('viewMode', 'card'); // 쿼리스트링의 viewMode 값 변경
    history.pushState(null, '', url); // 페이지 새로고침 없이 URL 변경

    // 전환되는 동안 버튼 비활성화
    if (!cardItems[0].classList.contains('fade-in')) {
        btnDisabled(listItems, cardItems, "paging");
    }

    // 리스트형 아이템 흐려지게
    listItems.forEach((item, index) => {
        setTimeout(() => {
            if (item.classList.contains('fade-in')) {
                item.classList.add('fade-out');
                item.classList.remove('fade-in');
            }

        }, index * 100); // 100ms 간격으로 흐려짐
    });

    listContainer.style.zIndex = '1';


    // 카드형 아이템 순차적으로 나타나게
    cardItems.forEach((item, index) => {
        setTimeout(() => {
            if (item.classList.contains('fade-out')) {
                item.classList.add('fade-in');
                item.classList.remove('fade-out');
            }
        }, 200 + (index * 150)); // 리스트가 사라진 후 150ms 간격으로 나타남
    });
}

// card -> list 전환 function
function switchToListView() {
    const listItems = document.querySelectorAll('.list-item');
    const cardItems = document.querySelectorAll('.card-item');
    const listContainer = document.querySelector('#toList-container');
    const cardContainer = document.querySelector('#toCard-container');
    const toListBtn = document.querySelector('#toList-btn');
    const toCardBtn = document.querySelector('#toCard-btn');

    // positionbox data-mode 값 변경 (컨테이너 크기 동적으로 바꾸기 위함)
    let positionBox = document.querySelector('#position-box');
    positionBox.setAttribute('data-mode', 'list');

    // 버튼 활성화 시 클래스 추가 (활성화 스타일 적용)
    toCardBtn.classList.remove('active');
    toListBtn.classList.add('active');

    const url = new URL(window.location);
    url.searchParams.set('viewMode', 'list'); // 쿼리스트링의 viewMode 값 변경
    history.pushState(null, '', url); // 페이지 새로고침 없이 URL 변경

    // 전환되는 동안 버튼 비활성화
    if (!listItems[0].classList.contains('fade-in')) {
        btnDisabled(listItems, cardItems, "paging");
    }

    // card형 아이템 흐려지게
    cardItems.forEach((item, index) => {
        setTimeout(() => {
            if (item.classList.contains('fade-in')) {
                item.classList.add('fade-out');
                item.classList.remove('fade-in');
            }
        }, index * 100); // 100ms 간격으로 흐려짐
    });

    cardContainer.style.zIndex = '1';
    listContainer.style.zIndex = '3';





}

// 버튼 비활성화
function btnDisabled(listItems, cardItems, status) {
    const listSwitchBtn = document.querySelectorAll('.listSwitch-btn');
    listSwitchBtn.forEach(item => {
        item.disabled = true;
    });

    // 애니메이션이 끝난 후 버튼 활성화
    let totalDuration = 0;
    if (status === 'load') {
        totalDuration = (cardItems.length * 150);
    } else if (status === 'paging') {
        totalDuration = 500 + (cardItems.length * 150);
    }

    setTimeout(() => {
        listSwitchBtn.forEach(item => {
            item.disabled = false;
        });
    }, totalDuration); // 애니메이션 시간에 맞춰 비활성화 해제
}


//    페이지네이션
function navigatePage(page) {
    let toListBtn = document.querySelector('#toList-btn');
    let toCardBtn = document.querySelector('#toCard-btn');

    if (status === 'my') {
        if (toListBtn.classList.contains('active')) {
            location.href = `/chat/list?cp=${page}&viewMode=list&status=${status}`;
        } else if (toCardBtn.classList.contains('active')) {
            location.href = `/chat/list?cp=${page}&viewMode=card&status=${status}`;
        } else {
            console.log("location 실패")
        }
    } else {
        if (toListBtn.classList.contains('active')) {
            location.href = `/chat/list?cp=${page}&viewMode=list`;
        } else if (toCardBtn.classList.contains('active')) {
            location.href = `/chat/list?cp=${page}&viewMode=card`;
        } else {
            console.log("location 실패")
        }
    }


}

window.addEventListener('load', () => {
    const listContainer = document.querySelector('#toList-container');
    const cardContainer = document.querySelector('#toCard-container');
    let listItems = document.querySelectorAll('.list-item');
    let cardItems = document.querySelectorAll('.card-item');
    let toListBtn = document.querySelector('#toList-btn');
    let toCardBtn = document.querySelector('#toCard-btn');

    // positionbox data-mode 값 변경 (컨테이너 크기 동적으로 바꾸기 위함)
    let positionBox = document.querySelector('#position-box');
    positionBox.setAttribute('data-mode', viewMode); // 페이지 이동시 초기화되므로 viewMode값을 불러와 처리
    // resize
    reSizePositionBox();

    if (viewMode === 'list') {
        cardContainer.style.zIndex = '1';
        listContainer.style.zIndex = '3';
        listItems.forEach((item, index) => {
            setTimeout(() => {
                item.classList.remove('fade-out');
                item.classList.add('fade-in');
            }, 200 + (index * 100))

            if (!toListBtn.classList.contains('active')) {
                toCardBtn.classList.remove('active');
                toListBtn.classList.add('active');
            }


        });

    } else if (viewMode === 'card') {
        listContainer.style.zIndex = '1';
        cardItems.forEach((item, index) => {
            setTimeout(() => {
                item.classList.remove('fade-out');
                item.classList.add('fade-in');
            }, 200 + (index * 100))

            if (!toCardBtn.classList.contains('active')) {
                toCardBtn.classList.add('active');
                toListBtn.classList.remove('active');
            }
        });
    }

    // 로드되는 동안 버튼 비활성화
    btnDisabled(listItems, cardItems, "load");


    // 카드형 리스트 채팅방 정보
    const roomCardInfos = document.querySelectorAll('.toCard-item-info');
    roomCardInfos.forEach((item, index) => {
        item.addEventListener('mouseover', () => {
            let itemCardTitles = document.querySelectorAll('.itemCard-title');
            let itemCardRecentContents = document.querySelectorAll('.itemCard-recentContent');
            // let itemCardRoomDates = document.querySelectorAll('.itemCard-roomDate');
            let itemCardMemberProfile = document.querySelectorAll('.card-member-profile');
            let itemCardRoomWriter = document.querySelectorAll('.info-roomWriter')

            itemCardTitles[index].style.display = "block";
            itemCardRecentContents[index].style.display = "block";
            // itemCardRoomDates[index].style.display="block";
            itemCardMemberProfile[index].style.display = "block";
            itemCardRoomWriter[index].style.display = "block";

            item.addEventListener('mouseout', () => {
                itemCardTitles[index].style.display = "none";
                itemCardRecentContents[index].style.display = "none";
                // itemCardRoomDates[index].style.display="none";
                itemCardMemberProfile[index].style.display = "none";
                itemCardRoomWriter[index].style.display = "none";
            });
        });

    })

    let cardMouseover = document.querySelectorAll('.card-mouseover');
    cardMouseover.forEach(item => {

        item.addEventListener('mouseover', () => {
            item.classList.add('visible');
            item.addEventListener('mouseout', () => {
                item.classList.remove('visible');
                item.classList.remove('visible');
                item.classList.remove('visible');

            });
        })

    })

});


function reSizePositionBox() {
    let positionBox = document.querySelector('#position-box');
    let listBox = document.querySelector('#toList-container');
    let cardBox = document.querySelector('#toCard-container');
    let listBoxHeight = listBox.getBoundingClientRect().height;
    let cardBoxHeight = cardBox.getBoundingClientRect().height;

    let mode = positionBox.getAttribute('data-mode');

    if (mode === "list") {
        console.log("listHeight " + listBoxHeight);
        positionBox.style.height = listBoxHeight + 50 + "px";
    } else if (mode === "card") {
        console.log("cardHeight " + cardBoxHeight);
        positionBox.style.height = cardBoxHeight + 50 + "px";
    }
}
