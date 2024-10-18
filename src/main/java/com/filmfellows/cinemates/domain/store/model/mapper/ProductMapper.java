package com.filmfellows.cinemates.domain.store.model.mapper;


import com.filmfellows.cinemates.domain.store.model.vo.Product;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Optional;

@Mapper
public interface ProductMapper {

    Product selectProductDetail(int productNo);

    List<Product> selectProductsByCategory(String category, Integer limit);

    Product selectProductByNo(@Param("productNo") int productNo);
}
