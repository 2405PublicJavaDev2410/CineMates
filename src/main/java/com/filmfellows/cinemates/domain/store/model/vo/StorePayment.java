package com.filmfellows.cinemates.domain.store.model.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Setter
@Getter
@ToString
public class StorePayment {
    private int storePaymentNo;
    private int purchaseNo;
    private String purchaseType;   // GIFT 여부
    private int amount;
    private String paymentMethod; // 카카오페이 or 신용카드
    private String paymentStatus;
    private Date paymentDate;
    private String paymentKey;  // 외부 결제시스템에서 받은 키
}
