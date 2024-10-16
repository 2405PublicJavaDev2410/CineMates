package com.filmfellows.cinemates.domain.store.model.service.impl;

import com.filmfellows.cinemates.domain.member.model.mapper.MemberMapper;
import com.filmfellows.cinemates.domain.member.model.vo.Member;
import com.filmfellows.cinemates.domain.store.model.mapper.*;
import com.filmfellows.cinemates.domain.store.model.service.StoreService;
import com.filmfellows.cinemates.domain.store.model.vo.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StoreServiceImpl implements StoreService {

    private final ProductMapper pMapper;
//    public StoreServiceImpl(ProductMapper pMapper) {
//        this.pMapper = pMapper;
//    }
    private final CartMapper cMapper;
    private final GiftMapper gMapper;
    /*private final StorePaymentMapper sMapper;*/
    private final PurchaseMapper purchaseMapper;

    @Override
    public List<Product> getProductsByCategory(String category, Integer limit) {
        return pMapper.selectProductsByCategory(category, limit);
    }

    @Override
    public List<Product> getProductsByCategory(String category) {
        return pMapper.selectProductsByCategory(category, null);
    }

    @Override
    public Product getProductDetail(int productNo) {
        return pMapper.selectProductDetail(productNo);
    }

    @Override
    public List<Cart> getCartItems(String memberId) {
        return cMapper.selectCartItems(memberId);
    }

    @Override
    @Transactional
    public boolean insertOrUpdateCartItem(Cart cartItem) {
        int exists = cMapper.checkCartItemExists(cartItem);
        if (exists > 0) {
            return cMapper.updateCartItem(cartItem) > 0;
        } else {
            return cMapper.insertCartItem(cartItem) > 0;
        }
    }

    @Override
    public boolean updateCartItem(Cart cartItem) {
        return cMapper.updateCartItem(cartItem) > 0;
    }

    @Override
    public boolean deleteCartItems(List<Integer> cartNos) {
        return cMapper.deleteCartItems(cartNos);
    }

    @Override
    public boolean clearCart(String memberId) {
        return cMapper.clearCart(memberId);
    }

    @Override
    public List<Cart> getSelectedCartItems(String memberId, List<Integer> selectedItems) {
        return cMapper.selectSelectedCartItems(memberId, selectedItems);
    }

    @Override
    @Transactional
    public Gift initializeGift(String memberId, int productNo, int quantity) {
        Product product = pMapper.selectProductDetail(productNo);
        if (product == null) {
            throw new RuntimeException("Product not found");
        }

        Gift gift = new Gift();
        gift.setSenderId(memberId);
        gift.setTotalAmount(product.getPrice() * quantity);
        gift.setTotalDiscountAmount(product.getDiscountAmount() * quantity);
        gift.setFinalAmount(gift.getTotalAmount() - gift.getTotalDiscountAmount());

        gMapper.insertGift(gift);

        Gift.GiftProductInfo productInfo = new Gift.GiftProductInfo();
        productInfo.setGiftNo(gift.getGiftNo());
        productInfo.setProductNo(productNo);
        productInfo.setQuantity(quantity);
        productInfo.setPrice(product.getPrice());
        productInfo.setDiscountAmount(product.getDiscountAmount());
        productInfo.setProduct(product);

        gift.addProduct(productInfo);

        gMapper.insertGiftProducts(gift.getProducts());

        return gift;
    }

    @Override
    public Gift initializeGiftFromCart(String memberId, List<Cart> selectedCarts) {
        Gift gift = new Gift();
        gift.setSenderId(memberId);

        for(Cart cart : selectedCarts) {
            Product product = pMapper.selectProductDetail(cart.getProductNo());
            Gift.GiftProductInfo productInfo = new Gift.GiftProductInfo();
            productInfo.setProductNo(cart.getProductNo());
            productInfo.setQuantity(cart.getQuantity());
            productInfo.setPrice(product.getPrice());
            productInfo.setDiscountAmount(product.getDiscountAmount());
            productInfo.setProduct(product);
            gift.getProducts().add(productInfo);
        }

        calculateGiftAmount(gift);

        int giftInsertResult = gMapper.insertGift(gift);
        if (giftInsertResult == 0) {
            throw new RuntimeException("Insert gift failed");
        }
        int productsInsertResult = gMapper.insertGiftProducts(gift.getProducts());
        if (productsInsertResult == 0) {
            throw new RuntimeException("Insert gift products failed");
        }

        return gift;
    }

    @Override
    public Purchase initializePurchaseFromCart(String memberId, List<Cart> selectedCarts) {
        Purchase purchase = new Purchase();
        purchase.setMemberId(memberId);
        purchase.setStatus("INITIATED");

        List<PurchaseItem> items = selectedCarts.stream()
                .map(cart -> {
                    PurchaseItem item = new PurchaseItem();
                    item.setProductNo(cart.getProductNo());
                    item.setQuantity(cart.getQuantity());
                    item.setProduct(cart.getProduct());
                    return item;
                })
                .collect(Collectors.toList());

            purchase.setItems(items);

            // 총액, 할인액 계산
            int totalAmount = items.stream()
                    .mapToInt(item -> item.getProduct().getPrice() * item.getQuantity())
                    .sum();
            int totalDiscountAmount = items.stream()
                    .mapToInt(item -> item.getProduct().getDiscountAmount() * item.getQuantity())
                    .sum();
            int finalAmount = totalAmount - totalDiscountAmount;

            purchase.setTotalAmount(totalAmount);
            purchase.setTotalDiscountAmount(totalDiscountAmount);
            purchase.setFinalAmount(finalAmount);

            purchaseMapper.insertPurchase(purchase);
            for (PurchaseItem item : items) {
                item.setPurchaseNo(purchase.getPurchaseNo());
                purchaseMapper.insertPurchaseItem(item);
            }

            return purchase;
    }

    @Override
    public boolean verifyAndSerRecipient(Gift gift, String name, String phone) {
        Member recipient = gMapper.findMemberByNameAndPhone(name, phone);
        if (recipient != null) {
            gift.setRecipientId(recipient.getMemberId());
            gift.setRecipientName(recipient.getName());
            gift.setRecipientPhone(recipient.getPhone());

            int updateResult = gMapper.updateGift(gift);
            if (updateResult == 0) {
                throw new RuntimeException("Update gift failed");
            }
            return true;
        }
        return false;
    }

    @Override
    @Transactional
    public void completeGiftPayment(int giftNo) {
        Gift gift = gMapper.selectOneByGiftNo(giftNo);
        if (gift == null) {
            throw new RuntimeException("Gift not found");
        }
        gift.setIsTransferred("Y");
        int updateResult = gMapper.updateGift(gift);
        if(updateResult == 0) {
            throw new RuntimeException("Update gift failed");
        }
    }

    private void calculateGiftAmount(Gift gift) {
        int totalAmount = 0;
        int totalDiscountAmount = 0;

        for(Gift.GiftProductInfo productInfo : gift.getProducts()) {
            totalAmount += productInfo.getPrice() * productInfo.getQuantity();
            totalDiscountAmount += productInfo.getDiscountAmount() * productInfo.getQuantity();
        }

        gift.setTotalAmount(totalAmount);
        gift.setTotalDiscountAmount(totalDiscountAmount);
        gift.setFinalAmount(totalAmount - totalDiscountAmount);
    }

    /*@Override
    public KakaoPayReady prepareGiftPayment(Gift gift) {
        KakaoPayReadyRequest request = new KakaoPayReadyRequest();
        return kClient.readyForPayment(request);
    }

    @Override
    public KakaoPayReady preparePurchasePayment(Purchase purchase) {
        KakaoPayReadyRequest request = new KakaoPayReadyRequest();
        return kClient.readyForPayment(request);
    }

    @Override
    @Transactional
    public KakaoPayApproval completeGiftPayment(Gift gift, String pgToken, String paymentKey) {
        KakaoPayApprovalRequest request = new KakaoPayApprovalRequest();
        KakaoPayApproval approval = kClient.approvePayment(request);

        gift.setPaymentMethod("KAKAO_PAY");
        gMapper.updateGift(gift);

        return approval;
    }

    @Override
    @Transactional
    public KakaoPayApproval completePurchasePayment(Purchase purchase, String pgToken, String paymentKey) {
        KakaoPayApprovalRequest request = new KakaoPayApprovalRequest();
        KakaoPayApproval approval = kClient.approvePayment(request);

        purchase.setPaymentMethod("KAKAO_PAY");
        purchaseMapper.updatePurchase(purchase);

        return approval;
    }*/

    @Override
    @Transactional
    public void processGiftSend(Gift gift) {

    }

    @Override
    public void updateStorePayment(StorePayment storePayment) {
        /*sMapper.updateStorePayment(storePayment);*/
    }



    @Override
    @Transactional
    public Purchase initializePurchase(String memberId, int productNo) {
        Product product = pMapper.selectProductDetail(productNo);
        if (product == null) {
            throw new RuntimeException("Product not found");
        }

        Purchase purchase = new Purchase();
        purchase.setMemberId(memberId);
        purchase.setStatus("INITIATED");
        purchase.setGiftYn("N");

        int totalAmount = product.getPrice();
        int totalDiscountAmount = product.getDiscountAmount();
        int finalAmount = totalAmount - totalDiscountAmount;

        purchase.setTotalAmount(totalAmount);
        purchase.setTotalDiscountAmount(totalDiscountAmount);
        purchase.setFinalAmount(finalAmount);

       purchaseMapper.insertPurchase(purchase);

        PurchaseItem item = new PurchaseItem();
        item.setPurchaseNo(purchase.getPurchaseNo());
        item.setProductNo(productNo);
        item.setQuantity(1);
        item.setOriginalPrice(product.getPrice());
        item.setDiscountAmount(product.getDiscountAmount());
        item.setDiscountedPrice(product.getPrice() - product.getDiscountAmount());

        purchaseMapper.insertPurchaseItem(item);

        List<PurchaseItem> items = new ArrayList<>();
        item.setProduct(product);
        items.add(item);
        purchase.setItems(items);

        return purchase;
    }

    @Override
    public Purchase getPurchaseDetails(int purchaseNo) {
        return purchaseMapper.selectPurchaseDetails(purchaseNo);
    }

    @Override
    public Member getMemberById(String memberId) {
        return purchaseMapper.selectMemberById(memberId);
    }
}
