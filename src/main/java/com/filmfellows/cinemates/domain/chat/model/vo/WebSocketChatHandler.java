package com.filmfellows.cinemates.domain.chat.model.vo;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Slf4j
@Component
@RequiredArgsConstructor
public class WebSocketChatHandler extends TextWebSocketHandler {
    private final ObjectMapper mapper;

    // 소켓 세션을 저장할 Set
    private final Set<WebSocketSession> sessions = new HashSet<>();

    // 채팅방 id와 소켓 세션을 저장할 Map
    private final Map<Integer, Set<WebSocketSession>> chatRoomSessionMap = new HashMap<>();

    // 소켓 연결 확인
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        // TODO Auto-generated method stub
        log.info("{} 연결됨", session.getId());
        sessions.add(session);
        session.sendMessage(new TextMessage("{chatContent: 'WebSocket 연결 완료'}"));
        System.out.println(session.getId());
    }

    // 소켓 메세지 처리
    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String payload = message.getPayload();
        log.info("payload {}", payload);
        System.out.println("session :  "+session);

        // 클라이언트로부터 받은 메세지를 ChatMessage로 변환
        ChatMessage chatMessage = mapper.readValue(payload, ChatMessage.class);
        log.info("session {}", chatMessage.toString());
        System.out.println(chatMessage);

        // 메세지 타입에 따라 분기
        if(chatMessage.getMessageType().equals(ChatMessage.MessageType.FIRST)){
            // 입장 메세지
            chatRoomSessionMap.computeIfAbsent(chatMessage.getRoomNo(), s -> new HashSet<>()).add(session);
            chatMessage.setChatContent("FIRST");

        }else if(chatMessage.getMessageType().equals(ChatMessage.MessageType.JOIN)){
            // 입장 메세지
            chatRoomSessionMap.computeIfAbsent(chatMessage.getRoomNo(), s -> new HashSet<>()).add(session);
            chatMessage.setChatContent("JOIN");
            System.out.println("JOIN!!!!"); // onmessage로 join 확인후 join이면 opacity 1로 수정
        }
        else if(chatMessage.getMessageType().equals(ChatMessage.MessageType.LEAVE)){
            // 퇴장 메세지
//            chatRoomSessionMap.get(chatMessage.getRoomNo()).remove(session);
            chatMessage.setChatContent("LEAVE");
        }else if(chatMessage.getMessageType().equals(ChatMessage.MessageType.WAIT)){
//            chatRoomSessionMap.get(chatMessage.getRoomNo()).remove(session);
            chatMessage.setChatContent("WAIT");
            System.out.println("WAIT!!!!!!!!!!!!");
        }

        // 채팅 메세지 전송
        for(WebSocketSession webSocketSession : chatRoomSessionMap.get(chatMessage.getRoomNo())){
            webSocketSession.sendMessage(new TextMessage(mapper.writeValueAsString(chatMessage)));
        }
    }

    // 소켓 연결 종료
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        // TODO Auto-generated method stub
        log.info("{} 연결 끊김", session.getId());
//        session.sendMessage(new TextMessage("WebSocket 연결 종료"));
        sessions.remove(session);
        System.out.println("sessions.size : "+sessions.size());

    }


}