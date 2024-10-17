package com.filmfellows.cinemates.app.chat.dto;

import com.filmfellows.cinemates.domain.chat.model.vo.ChatMessage;
import lombok.*;

import java.sql.Timestamp;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class chatMessageAndProfile {
    // 메세지 타입 : 입장, 채팅
    public enum MessageType{
        FIRST, JOIN, TALK, LEAVE
    }

    private ChatMessage.MessageType messageType;
    private Integer chatNo;
    private String chatContent;
    private Integer roomNo;
    private String memberId;
    private Timestamp chatDate;
    private String reportStatus;

    // 회원정보
    private String filePath;
    private String fileRename;
}
