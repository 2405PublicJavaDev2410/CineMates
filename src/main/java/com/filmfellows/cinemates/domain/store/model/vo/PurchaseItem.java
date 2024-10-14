package com.filmfellows.cinemates.domain.store.model.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class PurchaseItem {
    private int purchaseItemNo;
    private int purchaseNo;
    private int productNo;
    private int quantity;
    private int originalPrice;
    private int discountAmount;
    private int discountedPrice;
    private Product product;
}
