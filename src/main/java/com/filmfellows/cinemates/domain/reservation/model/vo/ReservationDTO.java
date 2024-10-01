package com.filmfellows.cinemates.domain.reservation.model.vo;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Repository;

import java.sql.Date;
@Data
@Getter
@Setter
@Repository
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
    private String titleKor;
    private String titleEng;
    private String filename;
    private String fileRename;

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
}
