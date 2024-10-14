package com.filmfellows.cinemates.domain.store.model.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class Cart {
    private int cartNo;
    private String memberId;
    private int productNo;
    private int quantity;
    private Product product;
}
