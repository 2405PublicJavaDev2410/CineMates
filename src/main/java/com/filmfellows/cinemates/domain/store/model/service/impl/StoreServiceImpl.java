package com.filmfellows.cinemates.domain.store.model.service.impl;

import com.filmfellows.cinemates.domain.member.model.mapper.MemberMapper;
import com.filmfellows.cinemates.domain.member.model.vo.Member;
import com.filmfellows.cinemates.domain.store.model.mapper.*;
import com.filmfellows.cinemates.domain.store.model.service.StoreService;
import com.filmfellows.cinemates.domain.store.model.vo.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
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
    private final PurchaseMapper purchaseMapper;
    private final MemberMapper mMapper;

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
        String cartNo = cMapper.checkCartItemExists(cartItem);
        if (cartNo != null) {
            cartItem.setCartNo(Integer.parseInt(cartNo));
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
    public boolean deleteCartItems(Cart cartItem) {
        return cMapper.deleteCartItems(cartItem);
    }

    @Override
    public boolean clearCart(String memberId) {
        return cMapper.clearCart(memberId);
    }

    @Override
    public void updatePurchase(Purchase purchase) {
        purchaseMapper.updatePurchase(purchase);
    }

    @Override
    public Member getMemberById(String memberId) {
        return mMapper.selectOneById(memberId);
    }

    @Override
    public boolean verifyAndSetRecipient(Gift gift, String name, String phone) {
        Member recipient = gMapper.findMemberByNameAndPhone(name, phone);
        if (recipient != null) {
            gift.setRecipientId(recipient.getMemberId());
            gift.setRecipientName(recipient.getName());
            gift.setRecipientPhone(recipient.getPhone());
            gMapper.updateGift(gift);
            return true;
        }
        return false;
    }

    @Override
    @Transactional
    public void completeGiftPayment(int giftNo) {
        Gift gift = getGiftByNo(giftNo);
        if (gift == null) {
            throw new RuntimeException("Gift not found");
        }
        gift.setIsTransferred("Y");
        gMapper.updateGift(gift);
    }

    @Override
    public Purchase getPurchaseDetails(int purchaseNo) {
        return purchaseMapper.selectPurchaseDetails(purchaseNo);
    }

    @Override
    @Transactional
    public Purchase initializePurchase(String memberId, List<Map<String, Object>> items, boolean purchaseAll) {
        Purchase purchase = new Purchase();
        purchase.setMemberId(memberId);
        purchase.setStatus("INITIATED");

        List<PurchaseItem> purchaseItems = new ArrayList<>();
        int totalAmount = 0;
        int totalDiscountAmount = 0;

        if (purchaseAll) {
            List<Cart> cartItems = cMapper.getCartItemsByMemberId(memberId);
            for (Cart cart : cartItems) {
                Product product = pMapper.selectProductDetail(cart.getProductNo());
                PurchaseItem item = new PurchaseItem();
                item.setProductNo(product.getProductNo());
                item.setPurchasePrice(product.getPrice());
                item.setPurchaseDiscountAmount(product.getDiscountAmount());
                item.setProduct(product);
                purchaseItems.add(item);

                totalAmount += product.getPrice() * cart.getQuantity();
                totalDiscountAmount += product.getDiscountAmount() * cart.getQuantity();
            }
            cMapper.clearCart(memberId);
        } else {
            for (int i = 0; i < items.size(); i++) {
                int productNo = Integer.parseInt((String)items.get(i).get("productNo"));
                int quantity = (int) items.get(i).get("quantity");
                Product product = pMapper.selectProductDetail(productNo);

                PurchaseItem item = new PurchaseItem();
                item.setProductNo(product.getProductNo());
                item.setQuantity(quantity);
                item.setPurchasePrice(product.getPrice());
                item.setPurchaseDiscountAmount(product.getDiscountAmount());
                item.setProduct(product);
                purchaseItems.add(item);

                totalAmount += product.getPrice() * quantity;
                totalDiscountAmount += product.getDiscountAmount() * quantity;
            }
        }

        purchase.setPurchaseItems(purchaseItems);
        purchase.setTotalAmount(totalAmount);
        purchase.setTotalDiscountAmount(totalDiscountAmount);
        purchase.setFinalAmount(totalAmount - totalDiscountAmount);

        purchaseMapper.insertPurchase(purchase);
        for (PurchaseItem item : purchaseItems) {
            item.setPurchaseNo(purchase.getPurchaseNo());
            purchaseMapper.insertPurchaseItem(item);
        }

        return purchase;
    }

    @Override
    @Transactional
    public Gift initializeGift(String memberId, List<Map<String, Object>> items, boolean purchaseAll) {
        Gift gift = new Gift();
        gift.setSenderId(memberId);

        List<GiftItem> giftItems = new ArrayList<>();
        int totalAmount = 0;
        int totalDiscountAmount = 0;

        if (purchaseAll) {
            List<Cart> cartItems = cMapper.getCartItemsByMemberId(memberId);
            for (Cart cart : cartItems) {
                Product product = pMapper.selectProductDetail(cart.getProductNo());
                GiftItem giftItem = new GiftItem();
                giftItem.setProductNo(product.getProductNo());
                giftItem.setQuantity(cart.getQuantity());
                giftItem.setPrice(product.getPrice());
                giftItem.setDiscountAmount(product.getDiscountAmount());
                giftItems.add(giftItem);

                totalAmount += product.getPrice() * cart.getQuantity();
                totalDiscountAmount += product.getDiscountAmount() * cart.getQuantity();
            }
            cMapper.clearCart(memberId);
        } else {
            for (Map<String, Object> item : items) {
                int productNo = (int) item.get("productNo");
                int quantity = (int) item.get("quantity");
                Product product = pMapper.selectProductDetail(productNo);

                GiftItem giftItem = new GiftItem();
                giftItem.setProductNo(product.getProductNo());
                giftItem.setQuantity(quantity);
                giftItem.setPrice(product.getPrice());
                giftItem.setDiscountAmount(product.getDiscountAmount());
                giftItems.add(giftItem);

                totalAmount += product.getPrice() * quantity;
                totalDiscountAmount += product.getDiscountAmount() * quantity;
            }
        }

        gift.setGiftItems(giftItems);
        gift.setTotalAmount(totalAmount);
        gift.setTotalDiscountAmount(totalDiscountAmount);
        gift.setFinalAmount(totalAmount - totalDiscountAmount);

        gMapper.insertGift(gift);
        for (GiftItem giftItem : giftItems) {
            giftItem.setGiftNo(gift.getGiftNo());
            gMapper.insertGiftItem(giftItem);
        }

        return gift;
    }

    @Override
    @Transactional
    public Gift saveGift(Gift gift) {
        gMapper.insertGift(gift);
        for (GiftItem item : gift.getGiftItems()) {
            item.setGiftNo(gift.getGiftNo());
            gMapper.insertGiftItem(item);
        }
        return gift;
    }

    @Override
    public void processPurchase(Purchase purchase) {
        purchaseMapper.insertPurchase(purchase);

        int totalTickets = 0;

        for (PurchaseItem item : purchase.getPurchaseItems()) {
            purchaseMapper.insertPurchaseItem(item);

            if ("영화관람권".equals(item.getProduct().getCategoryName())) {
                totalTickets += item.getQuantity();
            }
        }

        if (totalTickets > 0) {
            String memberId = purchase.getMemberId();
            int currentTicketCount = purchaseMapper.getTicketCount(memberId);
            int newTicketCount = currentTicketCount + totalTickets;
            purchaseMapper.updateTicketCount(memberId, newTicketCount);
        }
    }

    @Override
    public Product getProductByNo(int productNo) {
        return pMapper.selectProductByNo(productNo);
    }

    @Override
    public Gift getGiftByNo(int giftNo) {
        Gift gift = gMapper.selectGiftByNo(giftNo);
        if (gift != null) {
            List<GiftItem> giftItems = gMapper.selectGiftItemsByGiftNo(giftNo);
            gift.setGiftItems(giftItems);
        }
        return gift;
    }

}

