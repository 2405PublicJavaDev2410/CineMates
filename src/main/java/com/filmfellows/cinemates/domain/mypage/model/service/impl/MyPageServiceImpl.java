package com.filmfellows.cinemates.domain.mypage.model.service.impl;

import com.filmfellows.cinemates.domain.mypage.model.mapper.MyPageMapper;
import com.filmfellows.cinemates.domain.mypage.model.service.MyPageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    public String selectAllQna() {
        return "";
    }

    @Override
    public String selectOneQnaByNo() {
        return "";
    }

    @Override
    public int insertQna() {
        return 0;
    }

    @Override
    public int deleteQna() {
        return 0;
    }
}
