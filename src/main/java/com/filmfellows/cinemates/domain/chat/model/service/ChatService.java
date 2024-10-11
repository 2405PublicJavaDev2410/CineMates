package com.filmfellows.cinemates.domain.chat.model.service;


import com.filmfellows.cinemates.app.chat.dto.ChatRoomMovie;
import com.filmfellows.cinemates.app.chat.dto.CinemaInfoByRegion;
import com.filmfellows.cinemates.app.chat.dto.RegionAndCinemaCount;
import com.filmfellows.cinemates.domain.chat.model.vo.ChatRoom;
import com.filmfellows.cinemates.domain.chat.model.vo.ChatTag;
import com.filmfellows.cinemates.domain.member.model.vo.ProfileImg;
import org.apache.ibatis.session.RowBounds;

import java.util.List;
import java.util.Map;

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
     * 기능 : 채팅방 검색 리스트 조회
     * @return List<ChatRoom> (채팅방 검색 리스트)
     */
    Map<String, Object> selectChatRoomList(Integer currentPage, int boardLimit, String tagName, List<String> searchMovieList, List<String> searchRoomList, List<String> searchRegionList);

    /**
     * 담당자 : 이충무
     * 기능 : 채팅방 태그 조회
     * @return List<ChatTag> (채팅방 태그 리스트)
     */
    List<ChatTag> selectChatTagList(String status);

    /**
     * 담당자 : 이충무
     * 기능 : 채팅방 프로필 조회
     * @return List<ProfileImg> (채팅방 프로필 리스트)
     */
    List<ProfileImg> selectProfileList();


}
