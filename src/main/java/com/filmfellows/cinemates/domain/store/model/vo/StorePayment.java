package com.filmfellows.cinemates.domain.store.model.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class StorePayment {
    private String imp_uid;
    private String merchant_uid;
    private Integer amount;
    private String status;
    private String buyer_email;
    private String buyer_name;
    private String buyer_tel;
    private String name;
    private Integer purchaseNo;
    private String CategoryName;
}
