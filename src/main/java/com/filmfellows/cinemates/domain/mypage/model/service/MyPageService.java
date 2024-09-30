package com.filmfellows.cinemates.domain.mypage.model.service;

public interface MyPageService {
    String selectOrderList();
    String selectAllQna();
    String selectOneQnaByNo();
    int insertQna();
    int deleteQna();
}
