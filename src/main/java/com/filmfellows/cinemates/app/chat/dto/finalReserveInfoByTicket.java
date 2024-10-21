package com.filmfellows.cinemates.app.chat.dto;

import lombok.*;

import java.sql.Timestamp;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class finalReserveInfoByTicket {
        // 채팅방의 영화정보
        private Integer movieNo;
        private String title;
        private String posterUrl;
        private Integer cinemaLocationCode;
        private String cinemaAddress;
        private Integer cinemaNo;
        private String cinemaName;

        // 마지막 상영날짜 선택
        private Integer screenNo;
        private Integer showtimeNo;
        private String showtimeTime;
        private String screenName;
        private String reservationDate;


        // screenNo, movieNo, showtimeTime, showTimeNo, title, screenName, cinemaName, cinemaNo, reservationDate(상영날짜), cinemaLocationCode 보내야됨!!!
}
