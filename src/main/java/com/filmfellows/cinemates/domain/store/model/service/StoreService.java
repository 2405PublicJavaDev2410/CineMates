package com.filmfellows.cinemates.domain.store.model.service;

import com.filmfellows.cinemates.domain.member.model.vo.Member;
import com.filmfellows.cinemates.domain.store.model.vo.*;

import java.util.List;
import java.util.Map;

public interface StoreService {

    List<Product> getProductsByCategory(String category, Integer limit);

    List<Product> getProductsByCategory(String category);

    Product getProductDetail(int productNo);

    List<Cart> getCartItems(String memberId);

    boolean insertOrUpdateCartItem(Cart cartItem);

    boolean updateCartItem(Cart cartItem);

    boolean deleteCartItems(Cart cartItem);

    boolean clearCart(String memberId);

    Purchase getPurchaseDetails(int purchaseNo);

    Gift initializeGift(String memberId, List<Map<String, Object>> items, boolean purchaseAll);

    Purchase initializePurchase(String memberId, List<Map<String, Object>> items, boolean purchaseAll);

    void completeGiftPayment(int giftNo);

    void updatePurchase(Purchase purchase);

    Member getMemberById(String memberId);

    boolean verifyAndSetRecipient(Gift gift, String name, String phone);

    Gift getGiftByNo(int giftNo);

    Gift saveGift(Gift gift);

    void processPurchase(Purchase purchase);

    Product getProductByNo(int productNo);
}
