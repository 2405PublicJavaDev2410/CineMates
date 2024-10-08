package com.filmfellows.cinemates.domain.mypage.model.service.impl;

import com.filmfellows.cinemates.common.utility.Util;
import com.filmfellows.cinemates.domain.member.model.vo.ProfileImg;
import com.filmfellows.cinemates.domain.mypage.model.mapper.MyPageMapper;
import com.filmfellows.cinemates.domain.mypage.model.service.MyPageService;
import com.filmfellows.cinemates.domain.mypage.model.vo.Qna;
import com.filmfellows.cinemates.domain.mypage.model.vo.QnaFile;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class MyPageServiceImpl implements MyPageService {
    private final MyPageMapper myMapper;

    @Override
    public String selectOrderList() {
        return "";
    }

    @Override
    public List<Qna> selectAllQnaById(String memberId) {
        return myMapper.selectAllQnaById(memberId);
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
}
