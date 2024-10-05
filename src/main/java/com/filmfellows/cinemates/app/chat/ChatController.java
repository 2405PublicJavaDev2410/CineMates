package com.filmfellows.cinemates.app.chat;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.filmfellows.cinemates.app.chat.dto.ChatRoomMovie;
import com.filmfellows.cinemates.app.chat.dto.CinemaInfoByRegion;
import com.filmfellows.cinemates.app.chat.dto.RegionAndCinemaCount;
import com.filmfellows.cinemates.app.chat.dto.ReservationInfoAndChatInfo;
import com.filmfellows.cinemates.domain.chat.model.service.ChatService;
import com.filmfellows.cinemates.domain.chat.model.vo.ChatRoom;
import com.filmfellows.cinemates.domain.chat.model.vo.ChatTag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
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
    public String showChatRoomList(Model model){
        // 채팅방 리스트 전체 조회 비즈니스 로직
        List<ChatRoom> chatRoomList = cService.selectChatRoomList();

        // 채팅방 태그 조회
        List<ChatRoom> tagList = cService.selectChatTagList();

        model.addAttribute("tagList", tagList);
        model.addAttribute("chatRoomList", chatRoomList);
        return "pages/chat/chatRoomList";
    }

    @GetMapping("/chat/room")
    public String showChatRoom(){
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

    @PostMapping("/chat/create")
    public String insertChatRoom(@ModelAttribute("ReservationInfoAndChatInfo") ReservationInfoAndChatInfo revAndChatInfo){
        System.out.println(revAndChatInfo);
        // DTO -> VO
        ChatRoom chatRoom = new ChatRoom(null,
                revAndChatInfo.getRoomName(),
                null,revAndChatInfo.getRoomCategory(), revAndChatInfo.getMovieNo(),
                revAndChatInfo.getCinemaLocationCode(), revAndChatInfo.getCinemaNo()
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
