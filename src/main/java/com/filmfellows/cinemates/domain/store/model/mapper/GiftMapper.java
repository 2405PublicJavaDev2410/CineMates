package com.filmfellows.cinemates.domain.store.model.mapper;

import com.filmfellows.cinemates.domain.member.model.vo.Member;
import com.filmfellows.cinemates.domain.store.model.vo.Gift;
import com.filmfellows.cinemates.domain.store.model.vo.GiftItem;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface GiftMapper {
    int updateGift(Gift gift);

    int insertGift(Gift gift);

    // 선물받는 회원 정보 조회
    Member findMemberByNameAndPhone(@Param("name") String name, @Param("phone") String phone);

    void insertGiftItem(GiftItem giftItem);

    Gift selectGiftByNo(int giftNo);

    List<GiftItem> selectGiftItemsByGiftNo(int giftNo);
}
