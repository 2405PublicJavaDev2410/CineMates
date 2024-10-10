package com.filmfellows.cinemates.domain.productadmin.model.service.impl;


import com.filmfellows.cinemates.domain.cinema.model.mapper.CinemaMapper;
import com.filmfellows.cinemates.domain.productadmin.model.mapper.ProductadminMapper;
import com.filmfellows.cinemates.domain.productadmin.model.service.ProductAdminService;
import com.filmfellows.cinemates.domain.productadmin.model.vo.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductadminServiceImpl implements ProductAdminService {


    private ProductadminMapper mapper;
    @Autowired
    public ProductadminServiceImpl(ProductadminMapper mapper) {
        this.mapper=mapper;
    }
    @Override
    public List<Product> allproduct() {
        List<Product> pList = mapper.allproduct();
        return pList;
    }

    @Override
    public Product oneproduct(int productNo) {
        Product product=mapper.oneproduct(productNo);
        return product;
    }

    @Override
    public int updateproduct(Product product) {
        int result=mapper.updateproduct(product);
        return result;
    }

    @Override
    public int insertproduct(Product product) {
        int result=mapper.insertproduct(product);
        return result;
    }

    @Override
    public int deleteproduct(int productNo) {
        int result=mapper.deleteproduct(productNo);
        return result;
    }
}
