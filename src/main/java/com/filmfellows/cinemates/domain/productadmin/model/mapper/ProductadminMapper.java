package com.filmfellows.cinemates.domain.productadmin.model.mapper;

import com.filmfellows.cinemates.domain.productadmin.model.vo.Product;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ProductadminMapper {

    List<Product> allproduct();

    Product oneproduct(int productNo);

    int updateproduct(Product product);

    int insertproduct(Product product);

    int deleteproduct(int productNo);
}
