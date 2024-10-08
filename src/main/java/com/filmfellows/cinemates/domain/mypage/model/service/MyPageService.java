package com.filmfellows.cinemates.domain.mypage.model.service;

import com.filmfellows.cinemates.domain.mypage.model.vo.Qna;
import com.filmfellows.cinemates.domain.mypage.model.vo.QnaFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface MyPageService {
    String selectOrderList();
    List<Qna> selectAllQnaById(String memberId);
    Qna selectOneQnaByNo(int qnaNo);
    QnaFile selectQnaFileByNo(int qnaNo);
    int insertQna(Qna qna, MultipartFile qnaFile) throws IOException;
    int deleteQna(int qnaNo);

}
