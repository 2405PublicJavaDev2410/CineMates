package com.filmfellows.cinemates.app.chat;

import com.filmfellows.cinemates.domain.chat.model.service.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

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

    @GetMapping("/chat/createForm")
    public String showCreateChatForm(){
        return "pages/chat/createChatForm";
    }

    @PostMapping("/chat/create")
    public String insertChatRoom(){
        return "pages/chat/createChatForm";
    }

}
