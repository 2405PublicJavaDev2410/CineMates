package com.filmfellows.cinemates.domain.store.model.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;
import java.util.List;

@Setter
@Getter
@ToString
public class Purchase {
    private int purchaseNo;
    private String memberId;
    private String recipientMemberId;
    private Date purchaseDate;
    private String status;
    private String giftYn;
    private String paymentMethod;
    private int totalAmount;
    private int totalDiscountAmount;
    private int finalAmount;
    private List<PurchaseItem> purchaseItems;
}
