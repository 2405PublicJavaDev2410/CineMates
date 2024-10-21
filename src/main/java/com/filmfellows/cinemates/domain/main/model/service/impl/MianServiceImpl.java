package com.filmfellows.cinemates.domain.main.model.service.impl;

import com.filmfellows.cinemates.app.main.dto.BannerDTO;
import com.filmfellows.cinemates.app.main.dto.ChatRoomDTO;
import com.filmfellows.cinemates.app.main.dto.boxOfficeDTO;
import com.filmfellows.cinemates.domain.main.model.mapper.MainMapper;
import com.filmfellows.cinemates.domain.main.model.service.MainService;
import com.filmfellows.cinemates.domain.main.model.vo.Banner;
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

    @Override
    public List<BannerDTO> getBannerList() {
        return mainMapper.selectBannerList();
    }

    @Override
    public void addBanner(Banner banner) {
        mainMapper.insertBanner(banner);
    }

    @Override
    public List<BannerDTO> getBannerDetail(Long bannerNo) {
        return mainMapper.selectBannerByBannerNo(bannerNo);
    }

    @Override
    public void updateBanner(BannerDTO bannerDTO) {
        mainMapper.updateBanner(bannerDTO);
    }

    @Override
    public void removeBanner(Long bannerNo) {
        mainMapper.deleteBanner(bannerNo);
    }

    @Override
    public List<BannerDTO> getMainBannerList() {
        return mainMapper.selectMainBannerList();
    }

    @Override
    public List<ChatRoomDTO> getChatRoomList() {
        return mainMapper.selectChatRoomList();
    }

//
}
