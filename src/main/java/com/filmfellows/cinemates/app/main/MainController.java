package com.filmfellows.cinemates.app.main;

import com.filmfellows.cinemates.app.main.dto.BannerDTO;
import com.filmfellows.cinemates.app.main.dto.ChatRoomDTO;
import com.filmfellows.cinemates.app.main.dto.boxOfficeDTO;
import com.filmfellows.cinemates.domain.chat.model.service.ChatService;
import com.filmfellows.cinemates.domain.chat.model.vo.ChatTag;
import com.filmfellows.cinemates.domain.main.model.service.MainService;
import com.filmfellows.cinemates.domain.member.model.vo.ProfileImg;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@Slf4j
@RequiredArgsConstructor
public class MainController {

    private final MainService mainService;
    private final ChatService cService;

    @GetMapping("/")
    public String showMain(Model model) {
        List<boxOfficeDTO> bList = mainService.getBoxOfficeList();
        List<BannerDTO> bannerList = mainService.getMainBannerList();
//        List<ChatRoomDTO> cList = mainService.getChatRoomList();



        // 태그 리스트 조회
        List<ChatTag> tagList = cService.selectChatTagList("default");
        // 참여인원별 채팅방 조회 및 상대시간 조회
        Map<String, Object> map = cService.selectChatRoomListByTop("joinCountRank");


        model.addAttribute("tagList", tagList);
        model.addAttribute("chatRoomList", map.get("chatRoomListByTop"));
        model.addAttribute("relativeTimeList", map.get("relativeTimeList"));

        model.addAttribute("bList", bList);
        model.addAttribute("bannerList", bannerList);
//      model.addAttribute("cList", cList);

        return "pages/main/index";
    }

}
