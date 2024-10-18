package com.filmfellows.cinemates.domain.reservation.model.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ChatToReserveDTO {
    private String reservationDate;
    //Movie - 영화
    private Integer movieNo;
    private String title;
    private String posterUrl;
    //Cinema - 극장
    private String cinemaName;
    private Integer cinemaNo;
    //screen - 상영관
    private String screenName;
    private Integer screenNo;
    //ShowTime - 상영
    private String showtimeTime;
    private Integer showtimeNo;
}
