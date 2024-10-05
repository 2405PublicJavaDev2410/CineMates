package com.filmfellows.cinemates.domain.chat.model.service;


import com.filmfellows.cinemates.app.chat.dto.ChatRoomMovie;
import com.filmfellows.cinemates.app.chat.dto.CinemaInfoByRegion;
import com.filmfellows.cinemates.app.chat.dto.RegionAndCinemaCount;
import com.filmfellows.cinemates.domain.chat.model.vo.ChatRoom;
import com.filmfellows.cinemates.domain.chat.model.vo.ChatTag;

import java.util.List;

public interface ChatService {

    /**
     * 담당자 : 이충무
     * 기능 : 전체 영화 정보 조회
     * @return List<ChatRoomMovie>
     */
    List<ChatRoomMovie> selectMovieAll();

    /**
     * 담당자 : 이충무
     * 기능 : 지역 하나의 극장 리스트 조회
     * @return List<CinemaInfoByRegion> (극장 이름 반환)
     */
    List<CinemaInfoByRegion> selectCinemaByRegion(Integer cinemaLocationCode, String movieNo);

    /**
     * 담당자 : 이충무
     * 기능 : 영화별 상영 극장 개수 리스트 조회
     * @return regionAndCinemaCount (영화별 극장 개수 반화)
     */
    List<RegionAndCinemaCount> selectCinemaCountByRegionByMovie(String movieNo);

    /**
     * 담당자 : 이충무
     * 기능 : 채팅방 생성
     * @return int (성공여부)
     */
    int insertChatRoom(ChatRoom chatRoom);

    /**
     * 담당자 : 이충무
     * 기능 : 채팅방 태그 등록
     * @return int (성공여부)
     */
    int insertTag(ChatTag chatTag);

    /**
     * 담당자 : 이충무
     * 기능 : 채팅방 전체 리스트 조회
     * @return List<ChatRoom> (채팅방 전체 리스트)
     */
    List<ChatRoom> selectChatRoomList();

    /**
     * 담당자 : 이충무
     * 기능 : 채팅방 태그 조회
     * @return List<ChatRoom> (채팅방 태그 리스트)
     */
    List<ChatRoom> selectChatTagList();
}
