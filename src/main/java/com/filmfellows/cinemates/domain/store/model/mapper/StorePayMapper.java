package com.filmfellows.cinemates.domain.store.model.mapper;

import com.filmfellows.cinemates.domain.member.model.vo.Member;
import com.filmfellows.cinemates.domain.store.model.vo.StorePayment;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface StorePayMapper {

    void insertPayment(StorePayment buyerInfo);

    String selectImpUid(String reservationNo);

    void deletePaymentInfo(String impUid);

    Member selectMemberById(String memberId);
}
