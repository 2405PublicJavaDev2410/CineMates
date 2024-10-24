let tagify;

// resultBox 애니메이션
// function resultBoxAnimation() {
//     const resultBox = document.querySelector('#search-result-box');
//     if (!resultBox.classList.contains('visible')) {
//         setTimeout(() => {
//             resultBox.classList.add('visible');
//         }, 1000);
//
//     } else {
//             resultBox.classList.remove('visible');
//         setTimeout(() => {
//             resultBox.classList.add('visible');
//
//         }, 1000);
//
//
//     }
// }

// tag click 검색
// tagName -> tag검색 시 사용
// page -> 페이징 네비에서 전달

// 키워드 검색 시 사용
function searchByType(tagName, page) {
    let searchBar = document.querySelector('input[name=searchKeyword]');

    let keyword = searchBar.value;

    if (tagName == null) {
        // 키워드 검색
        console.log("키워드검색완료");
        searchTagByOne(keyword, tagName, page);
    } else {
        // 태그 검색
        console.log("태그검색완료");
        searchTagByOne("", tagName, page);
    }

}

// 태그 검색 시 사용
function searchTagByOne(keyword, tagName, page) {
    // resultBoxAnimation();
    if (tagName == null && keyword === "") {
        alert("검색어를 입력해주세요 또는 태그를 눌러 검색하세요")
        return;
    }
    if (tagName != null) {
        let searchBar = document.querySelector('input[name=searchKeyword]');
        searchBar.value = "";
    }
    console.log(keyword);
    $.ajax({
        url: "/chat/search",
        data: {
            "tagName": tagName,
            "cp": page,
            "keyword": keyword,
        },
        type: "POST",
        success: function (data) { // 서버로부터 return된 값은 직렬화된 json data로 들어옴
            $('#list-pagination-container').replaceWith(data);
            console.log(data);
            let resultHistory = document.querySelector('.result-history');
            if (tagName != null) {
                resultHistory.innerText = `#${tagName}으로 검색한 결과에요`;
            } else {
                let newStr = "";
                tagify.value.forEach((item) => {
                    newStr += item.value + " ";
                });
                document.querySelector('.result-history').innerText = `${newStr}으로 검색한 결과에요`;
            }

            let listItems = document.querySelectorAll('.list-item');

            listItems.forEach((item, index) => {
                setTimeout(() => {
                    item.classList.remove('fade-out');
                    item.classList.add('fade-in');
                }, 400 + (index * 100));
            });


        },
        error: function () {
            console.log("서버 전송 실패");
        }
    });
}

// 페이지 로드 이후 실행
window.addEventListener('load', () => {
    let input = document.querySelector('input[name=searchKeyword]');

    tagify = new Tagify(input, {
        // 'title:', 'movie:', 'cinema:'로 시작하는 패턴만 태그로 등록 가능
        pattern: /^(채팅방이름:|영화:|지역:).+$/,
        // 'title:', 'movie:', 'cinema:' 드롭다운 메뉴로 제공
        whitelist: ['채팅방이름:', '영화:', '지역:'],
        enforceWhitelist: false,  // 필터가 아닌 검색어도 입력 가능
        dropdown: {
            enabled: 1,           // 입력창을 클릭할 때 자동으로 드롭다운 열림
            position: 'text',     // 입력된 텍스트의 위치에 드롭다운 표시
            highlightFirst: true, // 첫 번째 아이템 자동으로 하이라이트
            maxItems: 5,          // 최대 표시할 아이템 수
        },
        editTags: true,         // 선택 후 태그 수정 비활성화 (텍스트 입력만 가능)
    });

    // input에 포커스될 때 드롭다운 메뉴 표시
    tagify.on('focus', function (e) {
        // 텍스트 입력이 없을 때만 전체 드롭다운을 표시
        if (!input.value.trim()) {
            tagify.dropdown.show.call(tagify);  // 전체 드롭다운 메뉴 보여주기
        }


    });

    // 태그 선택 시 바로 등록되지 않고, 선택한 텍스트만 입력창에 추가됨
    tagify.on('dropdown:select', function (e) {
        let selectedValue = e.detail.data.value;

        // 선택된 값만 입력창에 추가
        input.value = selectedValue;  // 선택한 필터 뒤에 공백 추가
        tagify.dropdown.hide();  // 드롭다운 숨기기


    });

    // 입력 이벤트 처리 (자동완성 기능 포함)
    input.addEventListener('input', function (e) {
        // 태그 자동완성 기능 활성화
        let searchQuery = e.target.value;
        if (searchQuery.length > 0) {
            tagify.dropdown.show(searchQuery); // 드롭다운에 필터링된 결과 표시
        }
    });


})