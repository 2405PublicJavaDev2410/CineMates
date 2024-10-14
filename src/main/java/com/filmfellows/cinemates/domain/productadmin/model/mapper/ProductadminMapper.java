package com.filmfellows.cinemates.domain.productadmin.model.mapper;

import com.filmfellows.cinemates.domain.productadmin.model.vo.Product2;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ProductadminMapper {

    List<Product2> allproduct();

    Product2 oneproduct(int productNo);

    int updateproduct(Product2 product);

    int insertproduct(Product2 product);

    int deleteproduct(int productNo);
}
