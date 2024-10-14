package com.filmfellows.cinemates.domain.store.model.service;

import com.filmfellows.cinemates.common.exception.PaymentException;
import com.filmfellows.cinemates.domain.member.model.vo.Member;
import com.filmfellows.cinemates.domain.store.model.vo.*;

import java.util.List;

public interface StoreService {

    List<Product> getProductsByCategory(String category, Integer limit);

    List<Product> getProductsByCategory(String category);

    Product getProductDetail(int productNo);

    List<Cart> getCartItems(String memberId);

    boolean insertOrUpdateCartItem(Cart cartItem);

    boolean updateCartItem(Cart cartItem);

    boolean deleteCartItems(List<Integer> cartNos);

    boolean clearCart(String memberId);

    List<Cart> getSelectedCartItems(String memberId, List<Integer> selectedItems);

    Purchase getPurchaseDetails(int purchaseNo);

    void processGiftSend(Gift gift);

    void updateStorePayment(StorePayment storePayment);

    Gift initializeGift(String memberId, int productNo, int quantity);

    Purchase initializePurchase(String memberId, int productNo);

    Member getMemberById(String memberId);

    Gift initializeGiftFromCart(String memberId, List<Cart> selectedCarts);

    boolean verifyAndSerRecipient(Gift gift, String name, String phone);

    void completeGiftPayment(int giftNo);

    Purchase initializePurchaseFromCart(String memberId, List<Cart> selectedCarts);

}
