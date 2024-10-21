package com.filmfellows.cinemates.domain.main.model.service;

import com.filmfellows.cinemates.app.main.dto.BannerDTO;
import com.filmfellows.cinemates.app.main.dto.ChatRoomDTO;
import com.filmfellows.cinemates.app.main.dto.boxOfficeDTO;
import com.filmfellows.cinemates.domain.main.model.vo.Banner;

import java.util.List;

public interface MainService {
    List<boxOfficeDTO> getBoxOfficeList();

    List<BannerDTO> getBannerList();

    void addBanner(Banner banner);

    List<BannerDTO> getBannerDetail(Long bannerNo);

    void updateBanner(BannerDTO bannerDTO);

    void removeBanner(Long bannerNo);

    List<BannerDTO> getMainBannerList();

    List<ChatRoomDTO> getChatRoomList();

}
