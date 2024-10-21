package com.filmfellows.cinemates.app.mypage.dto;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class myOrderRequest {
    private String startDate;
    private String endDate;
    private String memberId;
}
