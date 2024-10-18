package com.filmfellows.cinemates.app.mypage.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class myReservationResponse {
    // 영화명
    private String title;
    // 예매번호
    private String reservationNo;
    // 예매일시
    private String paymentDate;
    // 관람일시
    private String reservationDateTime;
    // 관람인원 (성인, 어린이, 경로)
    private String reservationPeople;
    // 극장
    private String cinemaName;
    // 관람좌석
    private String reservationSeat;
    // 영화 포스터
    private String posterUrl;
}
