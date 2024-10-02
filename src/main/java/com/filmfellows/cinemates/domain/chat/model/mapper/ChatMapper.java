package com.filmfellows.cinemates.domain.chat.model.mapper;

import com.filmfellows.cinemates.app.chat.dto.ChatRoomMovie;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ChatMapper {

    /**
     * 담당자 : 이충무
     * 기능 : 전체 영화 정보 조회 mapper
     * @return List<ChatRoomMovie>
     */
    List<ChatRoomMovie> selectMovieAll();

    /**
     * 담당자 : 이충무
     * 기능 : 지역 하나의 극장 리스트 조회 mapper
     * @return List<String> (극장 이름 반환)
     */
    List<String> selectCinemaByRegion(String region);
}
