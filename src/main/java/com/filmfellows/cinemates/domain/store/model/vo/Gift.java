package com.filmfellows.cinemates.domain.store.model.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@ToString
public class Gift {
    private int giftNo;
    private String senderId;
    private String recipientId;
    private String recipientName;
    private String recipientPhone;
    private String message;
    private String isTransferred ="N";
    private int totalAmount;
    private int totalDiscountAmount;
    private int finalAmount;
    private String paymentMethod;

    private List<GiftProductInfo> products = new ArrayList<>();

    @Setter
    @Getter
    @ToString
    public static class GiftProductInfo {
        private int giftNo;
        private int productNo;
        private int quantity;
        private int price;
        private int discountAmount;
        private Product product;
    }

    public void addProduct(GiftProductInfo product) {
        if (this.products == null) {
            this.products = new ArrayList<>();
        }
        this.products.add(product);
    }

}
