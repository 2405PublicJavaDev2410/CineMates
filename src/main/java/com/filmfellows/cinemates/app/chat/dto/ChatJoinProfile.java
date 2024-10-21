package com.filmfellows.cinemates.app.chat.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ChatJoinProfile {
    // 채팅방 참여 정보
    private Integer roomNo;
    private String memberId;
    private String status;
    private String acceptStatus;

    // 회원정보
    private String filePath;
    private String fileRename;
}
