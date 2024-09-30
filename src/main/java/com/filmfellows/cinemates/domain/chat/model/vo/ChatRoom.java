package com.filmfellows.cinemates.domain.chat.model.vo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.sql.Timestamp;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class ChatRoom {
    private Integer roomNo;
    private String roomName;
    private Timestamp roomDate;
}
