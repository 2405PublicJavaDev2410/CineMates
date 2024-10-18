package com.filmfellows.cinemates.domain.main.model.service.impl;

import com.filmfellows.cinemates.app.main.dto.boxOfficeDTO;
import com.filmfellows.cinemates.domain.main.model.mapper.MainMapper;
import com.filmfellows.cinemates.domain.main.model.service.MainService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class MianServiceImpl implements MainService {

    private final MainMapper mainMapper;

    @Override
    public List<boxOfficeDTO> getBoxOfficeList() {
        return mainMapper.selectBoxOfficeList();
    }
}
