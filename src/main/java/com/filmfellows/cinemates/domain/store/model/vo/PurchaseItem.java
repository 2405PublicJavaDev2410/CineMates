package com.filmfellows.cinemates.domain.store.model.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class PurchaseItem {
    private int purchaseNo;
    private int productNo;
    private int quantity;
    private int purchasePrice;
    private int purchaseDiscountAmount;
    private Product product;
}