// 영화 리스트 선택
// 전체 체크 해제
function unSelectedAll() {
    let movieSelectedItem = document.querySelectorAll('.movie-item-content');
    let checkedIcon = document.querySelectorAll('.check-icon');

    movieSelectedItem.forEach((item) => {
        item.classList.remove("selectedMovie");
    });
    checkedIcon.forEach((item) => {
        item.classList.remove("checked");
    });
}

// onclick function 체크되면 체크박스 생성
function selectMovie(div) {
    if (div.classList.contains("selectedMovie")) {
        unSelectedAll(); // 전체 체크 해제 - 영화
        unSelectedRegion(); // 전체 체크 해제 - 지역
        unSelectedCinema(); // 전체 체크 해제 - 극장
        div.classList.remove("selectedMovie");
        div.children[2].classList.remove('checked');
        movieClick(div);
    } else {
        unSelectedAll(); // 전체 체크 해제 - 영화
        unSelectedRegion(); // 전체 체크 해제 - 지역
        unSelectedCinema(); // 전체 체크 해제 - 극장
        div.classList.add("selectedMovie");
        div.children[2].classList.add('checked');
        movieClick(div);
    }
}


// 지역 선택
// 전체 체크 해제 - 지역
function unSelectedRegion() {
    let regionBoxAll = document.querySelectorAll('.regionBox');
    let regionCheckedIcon = document.querySelectorAll('.region-check-icon');

    regionBoxAll.forEach((item) => {
        item.classList.remove("checkedRegion");
    });
    regionCheckedIcon.forEach((item) => {
        item.classList.remove("checked");
    });
}

// onclick function 체크되면 체크박스 생성 - 지역
function selectRegion(div) {
    let movieAll = document.querySelectorAll('.movie-item-content');
    let count = 0;
    movieAll.forEach(item => {
        if (item.classList.contains('selectedMovie')) {
            count++;
        }
    })
    if (count <= 0) {
        alert('영화를 먼저 선택해주세요!');
    } else {
        if (div.classList.contains("checkedRegion")) {
            unSelectedRegion(); // 전체 체크 해제 - 지역
            unSelectedCinema(); // 전체 체크 해제 - 극장
            div.classList.remove("checkedRegion");
            div.children[1].classList.remove('checked');
            regionClick(div);
        } else {
            unSelectedRegion(); // 전체 체크 해제 - 지역
            unSelectedCinema(); // 전체 체크 해제 - 극장
            div.classList.add("checkedRegion");
            div.children[1].classList.add('checked');
            regionClick(div);
        }
    }

}

// 전체 체크 해제 - 극장
function unSelectedCinema() {
    let cinemaBoxAll = document.querySelectorAll('.cinemaBox');
    let cinemaCheckedIcon = document.querySelectorAll('.cinema-check-icon');

    cinemaBoxAll.forEach((item) => {
        item.classList.remove("checkedCinema");
    });

    cinemaCheckedIcon.forEach((item) => {
        item.classList.remove("checked");
    });
}

// onclick function 체크되면 체크박스 생성 - 극장
function selectCinema(div) {
    let regionAll = document.querySelectorAll('.regionBox');
    let count = 0;
    regionAll.forEach(item => {
        if (item.classList.contains('checkedRegion')) {
            count++;
        }
    })
    if (count <= 0) {
        alert('지역을 먼저 선택해주세요!');
    } else {
        if (div.classList.contains("checkedCinema")) {
            unSelectedCinema(); // 전체 체크 해제
            div.classList.remove("checkedCinema");
            div.children[1].classList.remove('checked');
        } else {
            unSelectedCinema(); // 전체 체크 해제
            div.classList.add("checkedCinema");
            div.children[1].classList.add('checked');
        }
    }
}

// 채팅방 정보
function changeLengthCount() {
    let nameLength = document.querySelector('input[name=roomName]').value.length;
    if (nameLength <= 30) {
        document.querySelector('.roomName-length').innerHTML = nameLength;
    }
}


// 로드 후 실행
window.addEventListener('load', () => {
    /////////태그 js
    const input = document.querySelector('input[name=roomTagName]');
    let tagify = new Tagify(input); // Tagify 초기화

    // 태그가 추가되면 이벤트 발생
    tagify.on('add', function () {
        console.log(tagify.value); // 입력된 태그 정보 객체(배열)
        console.log(tagify.value[0].value) // 태그 배열 0번째 객체의 value값 -> 입력한 태그
    })
});

// 지역 클릭 시 ajax 처리
let regionClick = (div) => {

    let cinemaLocationCode = 0;
    let movieNo = 0;
    if (div.classList.contains("checkedRegion")) {
        cinemaLocationCode = div.id;
        movieNo = document.querySelector('.selectedMovie').id
    }

    $.ajax({
        url: "/chat/cinemaList",
        data: {
            "cinemaLocationCode": cinemaLocationCode,
            "movieNo": movieNo
        },
        type: "POST",
        success: function (data) { // 서버로부터 return된 값은 직렬화된 json data로 들어옴
            console.log('성공 : regionClick');
            console.log("data :" + data);
            setTimeout(function () {
                // 값이 표시될 HTML 요소에 데이터를 출력

                $('#cinema-list-container').replaceWith(data);
            }, 500);
            console.log("replaceWith 까지 성공???")
        },
        error: function () {
            console.log("서버 전송 실패");
        },

        beforeSend: function () {
            let width = 0;
            let height = 0;
            let left = 0;
            let top = 0;
            width = 50;
            height = 50;
            top = ($(window).height() - height) / 2 + $(window).scrollTop();
            left = ($(window).width() - width) / 2 + $(window).scrollLeft();

            if ($("#div_ajax_load_image").length != 0) {
                $("#div_ajax_load_image").css({"top": top + "px", "left": left + "px"});
                $("#div_ajax_load_image").show();
                $("#div_overlay").show(); // 기존 배경 어둡게 처리
                // $("#div_overlay").fadeIn(); // 부드럽게
                // $("#div_ajax_load_image").fadeIn(); // 부드럽게
            } else {
                $('.createChat-content').append('<div id="div_overlay" style="position:absolute; top:0; left:0; width:100%; height:100%; z-index:990; background-color:rgba(0, 0, 0, 0.7); "></div>');
                $('body').append('<div id="div_ajax_load_image" style="position:absolute; top:' + top + 'px; left:' + left + 'px; width:' + width + 'px; height:' + height + 'px; z-index:991;  filter:alpha(opacity=50); opacity:alpha*0.5; margin:auto; padding:0; "><img src="/img/chat/loading.gif" style="width:50px; height:50px;"></div>');
            }
        },
        complete: function () {
            setTimeout(function () {
                // 1초 뒤 로딩 이미지 숨김
                $("#div_ajax_load_image").hide();
                $("#div_overlay").hide();
            }, 500);

        }

    });
}


// 영화 클릭 시 ajax 처리
let movieClick = (div) => {
    let movieNo = 0;
    console.log(movieNo);
    if (div.classList.contains("selectedMovie")) {
        movieNo = div.id;
    }
    $.ajax({
        url: "/chat/regionList",
        data: {
            "movieNo": movieNo
        },
        type: "POST",
        success: function (data) { // 서버로부터 return된 값은 직렬화된 json data로 들어옴
            console.log(movieNo);
            setTimeout(function () {
                // 값이 표시될 HTML 요소에 데이터를 출력
                $('#region-list-container').replaceWith(data);
                // 극장 리스트 초기화
                $('#cinema-list-container').empty();
            }, 500);
            console.log("replaceWith 까지 성공???")
        },
        error: function () {
            console.log("서버 전송 실패");
        },

        beforeSend: function () {
            let width = 0;
            let height = 0;
            let left = 0;
            let top = 0;
            width = 50;
            height = 50;
            top = ($(window).height() - height) / 2 + $(window).scrollTop();
            left = ($(window).width() - width) / 2 + $(window).scrollLeft();

            if ($("#div_ajax_load_image").length != 0) {
                $("#div_ajax_load_image").css({"top": top + "px", "left": left + "px"});
                $("#div_ajax_load_image").show();
                $("#div_overlay").show(); // 기존 배경 어둡게 처리
                // $("#div_overlay").fadeIn(); // 부드럽게
                // $("#div_ajax_load_image").fadeIn(); // 부드럽게
            } else {
                $('.createChat-content').append('<div id="div_overlay" style="position:absolute; top:0; left:0; width:100%; height:100%; z-index:990; background-color:rgba(0, 0, 0, 0.7); "></div>');
                $('body').append('<div id="div_ajax_load_image" style="position:absolute; top:' + top + 'px; left:' + left + 'px; width:' + width + 'px; height:' + height + 'px; z-index:991;  filter:alpha(opacity=50); opacity:alpha*0.5; margin:auto; padding:0; "><img src="/img/chat/loading.gif" style="width:50px; height:50px;"></div>');
            }
        },
        complete: function () {
            setTimeout(function () {
                // 0.5초 뒤 로딩 이미지 숨김
                $("#div_ajax_load_image").hide();
                $("#div_overlay").hide();
            }, 500);

        }

    });
}

// input hidden value에 ajax로 가져온 값 동적으로 저장하기
function hiddenValueInitialAndSubmit() {
    let movieNoInput = document.querySelector('input[name=movieNo]');
    let cinemaLocationCodeInput = document.querySelector('input[name=cinemaLocationCode]');
    let cinemaNoInput = document.querySelector('input[name=cinemaNo]');

    let movieItemAll = document.querySelectorAll('.movie-item-content');
    let regionItemAll = document.querySelectorAll('.regionBox');
    let cinemaItemAll = document.querySelectorAll('.cinemaBox');

    let movieCheckStatus = 0;
    let regionCheckStatus = 0;
    let cinemaCheckStatus = 0;
    movieItemAll.forEach(item => {
        if (item.classList.contains('selectedMovie')) {
            movieNoInput.value = item.id;
            movieCheckStatus++;
        }
    });

    regionItemAll.forEach(item => {
        if (item.classList.contains('checkedRegion')) {
            cinemaLocationCodeInput.value = item.id;
            regionCheckStatus++;
        }
    });

    cinemaItemAll.forEach(item => {
        if (item.classList.contains('checkedCinema')) {
            cinemaNoInput.value = item.id;
            cinemaCheckStatus++;
        }
    });
    if (movieCheckStatus <= 0) {
        alert("영화를 선택해주세요");
        return false;
    } else if (regionCheckStatus <= 0) {
        alert("지역을 선택해주세요");
        return false;
    } else if (cinemaCheckStatus <= 0) {
        alert("극장을 선택해주세요");
        return false;
    }

    return true;
}