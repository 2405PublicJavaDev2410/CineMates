package com.filmfellows.cinemates.domain.productadmin.model.service.impl;


import com.filmfellows.cinemates.domain.productadmin.model.mapper.ProductadminMapper;
import com.filmfellows.cinemates.domain.productadmin.model.service.ProductAdminService;
import com.filmfellows.cinemates.domain.productadmin.model.vo.Product2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductadminServiceImpl implements ProductAdminService {


    private ProductadminMapper mapper;
    @Autowired
    public ProductadminServiceImpl(ProductadminMapper mapper) {
        this.mapper=mapper;
    }
    @Override
    public List<Product2> allproduct() {
        List<Product2> pList = mapper.allproduct();
        return pList;
    }

    @Override
    public Product2 oneproduct(int productNo) {
        Product2 product=mapper.oneproduct(productNo);
        return product;
    }

    @Override
    public int updateproduct(Product2 product) {
        int result=mapper.updateproduct(product);
        return result;
    }

    @Override
    public int insertproduct(Product2 product) {
        int result=mapper.insertproduct(product);
        return result;
    }

    @Override
    public int deleteproduct(int productNo) {
        int result=mapper.deleteproduct(productNo);
        return result;
    }
}
