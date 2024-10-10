package com.filmfellows.cinemates.domain.productadmin.model.service;

import com.filmfellows.cinemates.domain.productadmin.model.vo.Product;

import java.util.List;

public interface ProductAdminService {
    /**
     * 상품전체검색
     * pram
     * return List<Product>

     */
    List<Product> allproduct();
    /**
     * 상품하나 검색
     * pram productNo
     * return Product

     */
    Product oneproduct(int productNo);
    /**
     * 상품수정
     * pram Product
     * return result

     */
    int updateproduct(Product product);
    /**
     * 상품추가
     * pram Product
     * return result

     */
    int insertproduct(Product product);
    /**
     * 상품삭제
     * pram productNo
     * return result

     */
    int deleteproduct(int productNo);
}
