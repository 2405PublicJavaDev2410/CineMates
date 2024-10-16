package com.filmfellows.cinemates.domain.chat.model.vo;

import lombok.*;

import java.sql.Timestamp;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ChatMessage {
    // 메세지 타입 : 입장, 채팅
    public enum MessageType{
        FIRST, JOIN, TALK, LEAVE
    }

    private MessageType messageType;
    private Integer chatNo;
    private String chatContent;
    private Integer roomNo;
    private String memberId;
    private Timestamp chatDate;
    private String reportStatus;
}
