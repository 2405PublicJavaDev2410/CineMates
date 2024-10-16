package com.filmfellows.cinemates.app.mypage.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class myReservationRequest {
    private String memberId;
    private String reservationNo;
}
