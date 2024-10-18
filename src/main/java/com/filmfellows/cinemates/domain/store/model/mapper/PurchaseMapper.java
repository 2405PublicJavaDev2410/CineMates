package com.filmfellows.cinemates.domain.store.model.mapper;

import com.filmfellows.cinemates.domain.store.model.vo.Purchase;
import com.filmfellows.cinemates.domain.store.model.vo.PurchaseItem;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface PurchaseMapper {
    Purchase selectPurchaseDetails(int purchaseNo);
    
    void insertPurchase(Purchase purchase);
    
    void updatePurchase(Purchase purchase);

    void insertPurchaseItem(PurchaseItem item);

    int getTicketCount(String memberId);

    void updateTicketCount(String memberId, int newTicketCount);
}
