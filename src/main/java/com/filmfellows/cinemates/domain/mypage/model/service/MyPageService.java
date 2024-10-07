package com.filmfellows.cinemates.domain.mypage.model.service;

import com.filmfellows.cinemates.domain.mypage.model.vo.Qna;
import com.filmfellows.cinemates.domain.mypage.model.vo.QnaFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface MyPageService {
    String selectOrderList();
    String selectAllQna();
    String selectOneQnaByNo();
    int insertQna(Qna qna, MultipartFile qnaFile) throws IOException;
    int deleteQna();
}
