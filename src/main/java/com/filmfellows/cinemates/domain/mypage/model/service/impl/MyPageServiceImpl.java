package com.filmfellows.cinemates.domain.mypage.model.service.impl;

import com.filmfellows.cinemates.app.mypage.dto.QnaDTO;
import com.filmfellows.cinemates.app.mypage.dto.myOrderRequest;
import com.filmfellows.cinemates.app.mypage.dto.myOrderResponse;
import com.filmfellows.cinemates.common.utility.Util;
import com.filmfellows.cinemates.domain.member.model.vo.ProfileImg;
import com.filmfellows.cinemates.domain.mypage.model.mapper.MyPageMapper;
import com.filmfellows.cinemates.domain.mypage.model.service.MyPageService;
import com.filmfellows.cinemates.domain.mypage.model.vo.Qna;
import com.filmfellows.cinemates.domain.mypage.model.vo.QnaFile;
import com.filmfellows.cinemates.domain.store.model.mapper.PurchaseMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class MyPageServiceImpl implements MyPageService {
    private final MyPageMapper myMapper;
    private final PurchaseMapper pMapper;

    @Override
    public List<myOrderResponse> selectOrderList(myOrderRequest request) {
        return pMapper.selectOrderList(request);
    }

    @Override
    public List<QnaDTO> selectAllQnaById(Integer currentPage, String memberId, RowBounds rBounds) {
        return myMapper.selectAllQnaById(currentPage, memberId, rBounds);
    }

    @Override
    public List<QnaDTO> selectAllQna(RowBounds rBounds, Integer currentPage) {
        return myMapper.selectAllQna(rBounds, currentPage);
    }

    @Override
    public Qna selectOneQnaByNo(int qnaNo) {
        return myMapper.selectOneQnaByNo(qnaNo);
    }

    @Override
    public QnaFile selectQnaFileByNo(int qnaNo) {
        return myMapper.selectQnaFileByNo(qnaNo);
    }

    @Override
    public List<QnaDTO> searchQnaByIdAndKeyword(String memberId, String searchCondition, String searchKeyword, RowBounds rBounds) {
        return myMapper.selectQnaByIdAndKeyword(memberId, searchCondition, searchKeyword, rBounds);
    }

    @Override
    public List<QnaDTO> searchQnaByKeyword(String searchKeyword, RowBounds rBounds) {
        return myMapper.selectQnaByKeyword(searchKeyword, rBounds);
    }

    @Override
    public Qna selectOneReplyByNo(Integer parentQnaNo) {
        return myMapper.selectOneReplyByNo(parentQnaNo);
    }

    @Override
    public int insertQna(Qna qna, MultipartFile uploadFile) throws IllegalStateException, IOException {
        int result = myMapper.insertQna(qna);
        if(result > 0 && uploadFile != null && !uploadFile.isEmpty()) {
            String fileName = uploadFile.getOriginalFilename();
            String fileRename = Util.fileRename(fileName);
            String filePath = "/cinemates/qna/";
            uploadFile.transferTo(new File("C:/uploadFile/qna/" + fileRename));
            QnaFile qnaFile = new QnaFile();
            qnaFile.setFileName(fileName);
            qnaFile.setFileRename(fileRename);
            qnaFile.setFilePath(filePath);
            qnaFile.setQnaNo(qna.getQnaNo());
            result = myMapper.insertQnaFile(qnaFile);
        }
        return result > 0 ? 1 : 0;
    }

    @Override
    public int deleteQna(int qnaNo) {
        return myMapper.deleteQna(qnaNo);
    }

    @Override
    public int insertReply(Qna qna) {
        return myMapper.insertReply(qna);
    }

    @Override
    public int getTotalQnaCountById(String memberId) {
        return myMapper.getTotalQnaCountById(memberId);
    }

    @Override
    public int getTotalQnaCount() {
        return myMapper.getTotalQnaCount();
    }

    @Override
    public int getTotalQnaCountByIdAndKeyword(String memberId, String searchCondition, String searchKeyword) {
        return myMapper.getTotalQnaCountByIdAndKeyword(memberId, searchCondition, searchKeyword);
    }

    @Override
    public int getTotalQnaCountByKeyword(String searchKeyword) {
        return myMapper.getTotalQnaCountByKeyword(searchKeyword);
    }
}
