package com.filmfellows.cinemates.domain.mypage.model.mapper;

import com.filmfellows.cinemates.domain.mypage.model.vo.Qna;
import com.filmfellows.cinemates.domain.mypage.model.vo.QnaFile;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MyPageMapper {
    String selectOrderList();
    List<Qna> selectAllQnaById(String memberId);
    Qna selectOneQnaByNo(int qnaNo);
    QnaFile selectQnaFileByNo(int qnaNo);
    int insertQna(Qna qna);
    int insertQnaFile(QnaFile qnaFile);
    int deleteQna(int qnaNo);

}
