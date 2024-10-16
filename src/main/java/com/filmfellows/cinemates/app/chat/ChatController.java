package com.filmfellows.cinemates.app.chat;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.filmfellows.cinemates.app.chat.dto.ChatRoomMovie;
import com.filmfellows.cinemates.app.chat.dto.CinemaInfoByRegion;
import com.filmfellows.cinemates.app.chat.dto.RegionAndCinemaCount;
import com.filmfellows.cinemates.app.chat.dto.ReservationInfoAndChatInfo;
import com.filmfellows.cinemates.domain.chat.model.service.ChatService;
import com.filmfellows.cinemates.domain.chat.model.vo.ChatRoom;
import com.filmfellows.cinemates.domain.chat.model.vo.ChatTag;
import com.filmfellows.cinemates.domain.member.model.vo.ProfileImg;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.Writer;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class ChatController {
    private ChatService cService;

    @Autowired
    public ChatController(ChatService cService) {
        this.cService = cService;
    }


    @GetMapping("/chat/list")
    public String showChatRoomList(Model model, HttpSession session,
                                   @RequestParam(value = "cp", defaultValue = "1") Integer currentPage,
                                   @RequestParam(value="viewMode", defaultValue = "list") String viewMode,
                                   @RequestParam(value="status", required = false, defaultValue = "all") String status
                                   ) {

        // 로그인 확인
        String memberId = (String) session.getAttribute("memberId");
        if(memberId == null) {
            return "redirect:/login";
        }
        Map<String, String> writerInfo = new HashMap<>();
        writerInfo.put("writer", memberId);
        writerInfo.put("status", status);


        // 전체 프로필 정보 조회 -> 채팅방 정보에 출력
        List<ProfileImg> profileList = cService.selectProfileList();
        List<String> profileMemberIdList = new ArrayList<>();
        for(ProfileImg profileItem : profileList){
            profileMemberIdList.add(profileItem.getMemberId());
        }


        // 채팅방 리스트 전체 조회 비즈니스 로직
        // 서비스에서 Pagination 객체, 조회된 cList 객체 매핑해서 반환
        int boardLimit = 9;
        List<String> emptyList = new ArrayList<>();
        Map<String, Object> map = cService.selectChatRoomList(currentPage, boardLimit, null, emptyList, emptyList, emptyList, writerInfo);

        // 채팅방 태그 조회
        List<ChatTag> tagList = cService.selectChatTagList("default");



        model.addAttribute("profileList", profileList);
        model.addAttribute("profileMemberIdList",profileMemberIdList);
        model.addAttribute("tagList", tagList);
        model.addAttribute("chatRoomList", map.get("cList"));
        model.addAttribute("pn", map.get("pn"));
        model.addAttribute("relativeTimeList", map.get("relativeTimeList"));
        model.addAttribute("viewMode", viewMode);
        return "pages/chat/chatRoomList";
    }

    @GetMapping("/chat/search")
    public String showSearchList(Model model, @RequestParam(value = "cp", defaultValue = "1") Integer currentPage){

        List<ChatTag> tagListDistinctList = cService.selectChatTagList("distinct");
        int boardLimit = 5;
        // 페이지 로드 시에 pn을 보내는데 이때 tagName "없음"을 보내어 아무 리스트도 출력이 안되게끔 만들어줌
        Map<String, Object> map = cService.selectChatRoomList(currentPage, boardLimit, "없음", null, null, null, null);
        model.addAttribute("pn", map.get("pn"));
        System.out.println(map.get("pn"));
        model.addAttribute("tagListDistinctList", tagListDistinctList);
        model.addAttribute("tagName", null);
        return "pages/chat/searchChat";
    }

//    ajax 검색 리스트 출력
    @PostMapping("/chat/search")
    public String selectSearchList(Model model, @RequestParam("tagName") String tagName,
                                   @RequestParam(value="keyword", required = false, defaultValue = "") String keyword, // json 형식 string
                                   @RequestParam(value = "cp", defaultValue = "1") Integer currentPage) {

        System.out.println("postMapping-controller : "+tagName + " , "+keyword);

        List<String> keywordArr = new ArrayList<String>();
        if(!keyword.equals("")) {
            tagName = null;
            // keyword (json) -> List화 시키기


            // jackson 객체
            ObjectMapper objectMapper = new ObjectMapper();
            // JSON 문자열을 List<Map<String, String>> 형태로 변환
            List<Map<String, String>> keywordList = null;

            try {
                keywordList = objectMapper.readValue(keyword, new TypeReference<List<Map<String, String>>>(){});
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }


            // value 필드만 추출하여 List로 변환
            keywordArr = keywordList.stream().map(map -> map.get("value"))
                    .toList();

            System.out.println(keywordArr);
        }


        List<String> searchMovieList = new ArrayList<>();
        List<String> searchRoomList = new ArrayList<>();
        List<String> searchRegionList = new ArrayList<>();
        for(String searchKeyword: keywordArr){
            if(searchKeyword.contains("영화:")){
                int index = searchKeyword.indexOf(':'); // 콜론(:)의 인덱스 찾기
                searchMovieList.add(index != -1 ? searchKeyword.substring(index + 1) : "") ;
            }else if(searchKeyword.contains("채팅방이름:")){
                int index = searchKeyword.indexOf(':'); // 콜론(:)의 인덱스 찾기
                searchRoomList.add(index != -1 ? searchKeyword.substring(index + 1) : "") ;
            }else if(searchKeyword.contains("지역:")){
                int index = searchKeyword.indexOf(':'); // 콜론(:)의 인덱스 찾기
                searchRegionList.add(index != -1 ? searchKeyword.substring(index + 1) : "") ;
            }
        }



        // 전체 프로필 정보 조회 -> 채팅방 정보에 출력
        List<ProfileImg> profileList = cService.selectProfileList();

        // 채팅방 리스트 전체 조회 비즈니스 로직
        // 서비스에서 Pagination 객체, 조회된 cList 객체 매핑해서 반환
        int boardLimit = 5;
        Map<String, Object> map = cService.selectChatRoomList(currentPage, boardLimit, tagName, searchMovieList, searchRoomList, searchRegionList, null);


        // 채팅방 태그 조회
        List<ChatTag> tagList = cService.selectChatTagList("default");
        System.out.println(searchRoomList);
        System.out.println(map.get("cList"));

        model.addAttribute("profileList", profileList);
        model.addAttribute("tagList", tagList);
        model.addAttribute("chatRoomList", map.get("cList"));
        model.addAttribute("pn", map.get("pn"));
        model.addAttribute("relativeTimeList", map.get("relativeTimeList"));
//      tagName으로 검색한 이후 페이지네비게이션에서 tagName을 기억해야하기때문에 다시 전달해줌
        model.addAttribute("tagName", tagName);
        return "pages/chat/searchChat::#list-pagination-container";
    }

    @GetMapping("/chat/room")
    public String showChatRoom(Model model, @ModelAttribute("ChatRoom") ChatRoom chatRoom){
        model.addAttribute("chatRoom", chatRoom);
        return "pages/chat/chatRoom";
    }

    /**
     * 담당자 : 이충무
     * 관련기능 : 채팅방 개설 페이지 이동
     * model : roomCategory
     */
    @GetMapping("/chat/createForm")
    public String showCreateChatForm(@RequestParam("roomCategory") String roomCategory, Model model){
            List<ChatRoomMovie> movieList = cService.selectMovieAll();

            model.addAttribute("roomCategory", roomCategory);
            model.addAttribute("movieList", movieList);
            return "pages/chat/createChatForm";
    }

    /**
     * 담당자 : 이충무
     * 관련기능 : 채팅방 개설 기능
     * model : ReservationInfoAndChatInfo
     */
    @PostMapping("/chat/create")
    public String insertChatRoom(@ModelAttribute("ReservationInfoAndChatInfo") ReservationInfoAndChatInfo revAndChatInfo,HttpSession session){
        // 로그인 확인 -> 채팅방 개설 시 작성자로 저장
        String roomWriter = (String) session.getAttribute("memberId");
        
        // DTO -> VO
        ChatRoom chatRoom = new ChatRoom(null, roomWriter,
                revAndChatInfo.getRoomName(),
                null,revAndChatInfo.getRoomCategory(), revAndChatInfo.getMovieNo(), null, null,
                revAndChatInfo.getCinemaLocationCode(), null, revAndChatInfo.getCinemaNo(), null, null, null
        );

        int insertChatRoomResult = cService.insertChatRoom(chatRoom);

        if(insertChatRoomResult > 0){
            System.out.println("채팅방 insert 성공!");
        }

    if(!revAndChatInfo.getRoomTagName().equals("")){
        List<String> tagNameArr = new ArrayList<String>();

        String tagNameJson = revAndChatInfo.getRoomTagName();
        // jackson 객체
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            // JSON 문자열을 List<Map<String, String>> 형태로 변환
            List<Map<String, String>> list = objectMapper.readValue(tagNameJson, new TypeReference<List<Map<String, String>>>(){});

            // value 필드만 추출하여 List로 변환
            tagNameArr = list.stream().map(map -> map.get("value"))
                    .toList();

            // 각 배열의 값을 하나씩 table에 insert 시켜주기
            for(String tagName : tagNameArr) {
                ChatTag chatTag = new ChatTag();
                chatTag.setTagName(tagName);
                chatTag.setRoomNo(chatRoom.getRoomNo());
                int tagResult = cService.insertTag(chatTag);
                if(tagResult > 0) System.out.println("tag등록 성공!!");
            }

        } catch(Exception e){
            e.printStackTrace();
        }
    }

        return "redirect:/chat/createForm?roomCategory="+revAndChatInfo.getRoomCategory();
//        return "pages/chat/chatRoomList";
    }

    @GetMapping("/chat/navigation")
    public String showNavi(){
        return "pages/chat/navigation";
    }


//    ajax - 영화, 지역 조건 들어간 극장 리스트
    @PostMapping("/chat/cinemaList")
    public String showCinemaList(@RequestParam("cinemaLocationCode") Integer cinemaLocationCode,
                                 @RequestParam("movieNo") String movieNo, Model model){
//        // 전달받은 regions '/'로 나누어 List화 시키기
//        String[] regionsArr = regions.split("/");
//        List<String> regionList = new ArrayList<>(Arrays.asList(regionsArr));

        System.out.println("movieNo :   " + movieNo);

//        List<String> cinemaListByOne = new ArrayList<String>();
//
//        List<String> cinemaListByAll = new ArrayList<String>();
//        for(String region : regionList){
//            // 하나의 region에 대한 극장 리스트 조회
//            cinemaListByOne = cService.selectCinemaByRegion(region, movieNo);
//            // 전체의 region에 대한 극장 리스트를 만들기 위해 추가시키기
//            cinemaListByAll.addAll(cinemaListByOne);
//        }
        List<CinemaInfoByRegion> cinemaListByRegion = cService.selectCinemaByRegion(cinemaLocationCode, movieNo);

        model.addAttribute("cinemaListByRegion", cinemaListByRegion);
        return "pages/chat/createChatForm::#cinema-list-container";
    }

    // ajax - 영화 클릭 시 region ( cinemaCount) 형태의 list 출력
    @PostMapping("/chat/regionList")
    public String showMovieList(@RequestParam("movieNo") String movieNo, Model model){
        List<RegionAndCinemaCount> regionAndCinemaCountList = cService.selectCinemaCountByRegionByMovie(movieNo);
        model.addAttribute("regionAndCinemaCountList", regionAndCinemaCountList);
        return "pages/chat/createChatForm::#region-list-container";
    }



}
