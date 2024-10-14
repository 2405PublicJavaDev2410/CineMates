package com.filmfellows.cinemates.domain.store.model.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Product {
    private int productNo;
    private String productName;
    private String categoryName;
    private int price;
    private int discountAmount;
    private int discountedPrice;
    private String imageUrl;
    private String description;

    public Product() {}
}
