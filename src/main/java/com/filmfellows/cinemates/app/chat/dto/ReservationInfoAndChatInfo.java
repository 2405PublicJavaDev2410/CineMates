package com.filmfellows.cinemates.app.chat.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class ReservationInfoAndChatInfo {
    private String roomCategory;
    private Integer movieNo;
    private Integer cinemaLocationCode;
    private Integer cinemaNo;
    private String roomName;
    private String roomTagName;
}
