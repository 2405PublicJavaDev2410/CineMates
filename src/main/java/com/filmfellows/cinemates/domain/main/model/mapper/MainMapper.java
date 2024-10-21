package com.filmfellows.cinemates.domain.main.model.mapper;

import com.filmfellows.cinemates.app.main.dto.BannerDTO;
import com.filmfellows.cinemates.app.main.dto.ChatRoomDTO;
import com.filmfellows.cinemates.app.main.dto.boxOfficeDTO;
import com.filmfellows.cinemates.domain.main.model.vo.Banner;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MainMapper {
    List<boxOfficeDTO> selectBoxOfficeList();

    List<BannerDTO> selectBannerList();

    void insertBanner(Banner banner);

    List<BannerDTO> selectBannerByBannerNo(Long bannerNo);

    void updateBanner(BannerDTO bannerDTO);

    void deleteBanner(Long bannerNo);

    List<BannerDTO> selectMainBannerList();

    List<ChatRoomDTO> selectChatRoomList();
}
