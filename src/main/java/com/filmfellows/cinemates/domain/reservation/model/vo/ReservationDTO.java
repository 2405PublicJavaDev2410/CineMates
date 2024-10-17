package com.filmfellows.cinemates.domain.reservation.model.vo;

import jakarta.annotation.Nullable;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Repository;

import java.sql.Date;
@Data
@Getter
@Setter
@Repository
@Nullable
public class ReservationDTO {
    //Reservation - 예약
    private String reservationNo;
    private Integer reservationVisitor;
    private Integer adultReserved;
    private Integer childReserved;
    private Integer seniorReserved;
    private String reservationSeat;
    private String reservationDate;

    //Member - 회원
    private String memberId;

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

    //Payment - 결제
    private String buyer_email;
    private String buyer_name;
    private String buyer_tel;
    private String imp_uid;

    //ShowTime - 상영
    private String showtimeTime;
    private Integer showtimeNo;
}
