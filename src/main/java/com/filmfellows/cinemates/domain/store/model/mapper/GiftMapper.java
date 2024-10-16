package com.filmfellows.cinemates.domain.store.model.mapper;

import com.filmfellows.cinemates.domain.member.model.vo.Member;
import com.filmfellows.cinemates.domain.store.model.vo.Gift;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface GiftMapper {
    int updateGift(Gift gift);

    int insertGift(Gift gift);

    int insertGiftProducts(List<Gift.GiftProductInfo> products);

    Gift selectOneByGiftNo(int giftNo);

    // 선물받는 회원 정보 조회
    Member findMemberByNameAndPhone(@Param("name") String name, @Param("phone") String phone);
}
