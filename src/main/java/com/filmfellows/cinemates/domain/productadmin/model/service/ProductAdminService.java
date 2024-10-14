package com.filmfellows.cinemates.domain.productadmin.model.service;

import com.filmfellows.cinemates.domain.productadmin.model.vo.Product2;

import java.util.List;

public interface ProductAdminService {
    /**
     * 상품전체검색
     * pram
     * return List<Product>

     */
    List<Product2> allproduct();
    /**
     * 상품하나 검색
     * pram productNo
     * return Product

     */
    Product2 oneproduct(int productNo);
    /**
     * 상품수정
     * pram Product
     * return result

     */
    int updateproduct(Product2 product);
    /**
     * 상품추가
     * pram Product
     * return result

     */
    int insertproduct(Product2 product);
    /**
     * 상품삭제
     * pram productNo
     * return result

     */
    int deleteproduct(int productNo);
}
