package com.filmfellows.cinemates.domain.mypage.model.mapper;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MyPageMapper {
    String selectOrderList();
    String selectAllQna();
    String selectOneQnaByNo();
    int insertQna();
    int deleteQna();
}
