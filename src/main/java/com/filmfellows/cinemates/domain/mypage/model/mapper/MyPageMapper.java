package com.filmfellows.cinemates.domain.mypage.model.mapper;

import com.filmfellows.cinemates.domain.mypage.model.vo.Qna;
import com.filmfellows.cinemates.domain.mypage.model.vo.QnaFile;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MyPageMapper {
    String selectOrderList();
    String selectAllQna();
    String selectOneQnaByNo();
    int insertQna(Qna qna);
    int insertQnaFile(QnaFile qnaFile);
    int deleteQna();

}
