package com.filmfellows.cinemates.app.chat.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.sql.Timestamp;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class ChatRoomMovie {
    private Integer movieNo;
    private String title;
    private String posterUrl;
    private Timestamp releaseDate;
    private Integer runningTime;
    private String rating;
}
