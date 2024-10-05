package com.filmfellows.cinemates.domain.chat.model.vo;

import lombok.*;

import java.sql.Timestamp;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ChatRoom {
    // 채팅방 정보
    private Integer roomNo;
    private String roomName;
    private Timestamp roomDate;
    private String roomCategory;

    // 채팅방의 영화정보
    private Integer movieNo;
    private Integer cinemaLocationCode;
    private Integer cinemaNo;

}
