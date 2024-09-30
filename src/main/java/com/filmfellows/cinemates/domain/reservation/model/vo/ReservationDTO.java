package com.filmfellows.cinemates.domain.reservation.model.vo;

import java.sql.Date;

public class ReservationDTO {
    private String reservationNo;
    private Integer reservationVisitor;
    private String reservationSeat;
    private String paymentMethod;
    private Date reservationDate;

    //Member - 회원
    private String memberId;

    //Movie - 영화
    private Integer movieNo;
    private String titleKorean;
    private String titleEnglish;

    //Cinema - 극장
    private String cinemaName;
    private Integer cinemaNo;

    //screen - 상영관
    private String screenName;
    private Integer screenNo;

}
