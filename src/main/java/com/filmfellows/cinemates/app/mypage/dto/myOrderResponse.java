package com.filmfellows.cinemates.app.mypage.dto;

import lombok.*;

import java.sql.Timestamp;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class myOrderResponse {
    private Integer productNo;
    private Timestamp purchaseDate;
    private String imageUrl;
    private String productName;
    private Integer finalAmount;
}
