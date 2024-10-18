package com.filmfellows.cinemates.domain.store.model.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class GiftItem {
    private int giftItemNo;
    private int giftNo;
    private int productNo;
    private int quantity;
    private int price;
    private int discountAmount;
    private Product product;
}
