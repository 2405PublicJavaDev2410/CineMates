package com.filmfellows.cinemates.domain.mypage.model.mapper;

import com.filmfellows.cinemates.app.mypage.dto.QnaDTO;
import com.filmfellows.cinemates.app.mypage.dto.myOrderResponse;
import com.filmfellows.cinemates.domain.mypage.model.vo.Qna;
import com.filmfellows.cinemates.domain.mypage.model.vo.QnaFile;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

@Mapper
public interface MyPageMapper {
    List<QnaDTO> selectAllQnaById(@Param("currentPage") Integer currentPage, @Param("memberId") String memberId, RowBounds rowBounds);
    List<QnaDTO> selectAllQna(RowBounds rBounds, Integer currentPage);
    Qna selectOneQnaByNo(int qnaNo);
    QnaFile selectQnaFileByNo(int qnaNo);
    List<QnaDTO> selectQnaByIdAndKeyword(@Param("memberId") String memberId, @Param("searchCondition") String searchCondition, @Param("searchKeyword") String searchKeyword, RowBounds rBounds);
    List<QnaDTO> selectQnaByKeyword(String searchKeyword, RowBounds rBounds);
    Qna selectOneReplyByNo(Integer parentQnaNo);
    int insertQna(Qna qna);
    int insertQnaFile(QnaFile qnaFile);
    int deleteQna(int qnaNo);
    int insertReply(Qna qna);
    int getTotalQnaCountById(String memberId);
    int getTotalQnaCount();
    int getTotalQnaCountByIdAndKeyword(@Param("memberId") String memberId, @Param("searchCondition") String searchCondition, @Param("searchKeyword") String searchKeyword);
    int getTotalQnaCountByKeyword(String searchKeyword);
}
