package com.filmfellows.cinemates.domain.productadmin.model.vo;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Product {

    private int productNo;
    private String categoryName;
    private String productName;
    private int price;
    private int discountAmount;
    private int discountedPrice;
    private String imageUrl;
    private String description;

}
