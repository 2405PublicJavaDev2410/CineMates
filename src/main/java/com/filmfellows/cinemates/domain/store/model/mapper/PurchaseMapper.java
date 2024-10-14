package com.filmfellows.cinemates.domain.store.model.mapper;

import com.filmfellows.cinemates.domain.member.model.vo.Member;
import com.filmfellows.cinemates.domain.store.model.vo.Purchase;
import com.filmfellows.cinemates.domain.store.model.vo.PurchaseItem;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;

@Mapper
public interface PurchaseMapper {
    Purchase selectPurchaseDetails(int purchaseNo);

    void updatePurchase(Purchase purchase);

    @Options(useGeneratedKeys = true, keyProperty = "purchaseNo", keyColumn = "PURCHASE_NO")
    @Insert("INSERT INTO PURCHASE_TBL (PURCHASE_NO, MEMBER_ID, RECIPIENT_MEMBER_ID, PURCHASE_DATE, STATUS, GIFT_YN, PAYMENT_METHOD, TOTAL_AMOUNT, TOTAL_DISCOUNT_AMOUNT, FINAL_AMOUNT) " +
            "VALUES (SEQ_PURCHASE_NO.NEXTVAL, #{memberId}, #{recipientMemberId}, SYSDATE, #{status}, #{giftYn}, #{paymentMethod}, #{totalAmount}, #{totalDiscountAmount}, #{finalAmount})")
    void insertPurchase(Purchase purchase);

    void insertPurchaseItem(PurchaseItem item);

    // 주문자 정보 조회
    Member selectMemberById(String memberId);
}
