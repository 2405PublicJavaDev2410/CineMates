package com.filmfellows.cinemates.domain.store.model.mapper;

import com.filmfellows.cinemates.domain.store.model.vo.Cart;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CartMapper {

    int updateCartItem(Cart cartItem);

    List<Cart> selectCartItems(String memberId);

    boolean deleteCartItems(List<Integer> cartNos);

    boolean clearCart(String memberId);

    List<Cart> selectSelectedCartItems(String memberId, List<Integer> selectedItems);

    int checkCartItemExists(Cart cartItem);

    int insertCartItem(Cart cartItem);
}
