package com.filmfellows.cinemates.app.chat;

import com.filmfellows.cinemates.app.chat.dto.ChatRoomMovie;
import com.filmfellows.cinemates.domain.chat.model.service.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Controller
public class ChatController {
    private ChatService cService;

    @Autowired
    public ChatController(ChatService cService) {
        this.cService = cService;
    }


    @GetMapping("/chat/list")
    public String showChatRoomList(){
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
    public String insertChatRoom(){
        return "pages/chat/createChatForm";
    }

    @GetMapping("/chat/navigation")
    public String showNavi(){
        return "pages/chat/navigation";
    }


//    ajax
    @PostMapping("/chat/cinemaList")
    public String showCinemaList(@RequestParam("regions") String regions, Model model){
        // 전달받은 regions '/'로 나누어 List화 시키기
        String[] regionsArr = regions.split("/");
        List<String> regionList = new ArrayList<>(Arrays.asList(regionsArr));

        System.out.println(regionList);
        List<String> cinemaListByOne = new ArrayList<String>();

        List<String> cinemaListByAll = new ArrayList<String>();
        for(String region : regionList){
            // 하나의 region에 대한 극장 리스트 조회
            cinemaListByOne = cService.selectCinemaByRegion(region);
            // 전체의 region에 대한 극장 리스트를 만들기 위해 추가시키기
            cinemaListByAll.addAll(cinemaListByOne);
        }

        model.addAttribute("cinemaListByAll", cinemaListByAll);
        return "pages/chat/createChatForm::#cinema-list-container";
    }
}
