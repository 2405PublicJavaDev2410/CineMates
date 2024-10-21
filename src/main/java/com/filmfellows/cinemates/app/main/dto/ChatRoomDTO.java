package com.filmfellows.cinemates.app.main.dto;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ChatRoomDTO {
    private Integer roomNo;
    private String roomWriter;
    private String roomName;
    private LocalDate roomDate;
    private String roomCategory;

    private Integer movieNo;
    private String title;
    private String posterUrl;

    private String cinemaAddress;
    private Integer cinemaNo;
    private String cinemaName;
}
