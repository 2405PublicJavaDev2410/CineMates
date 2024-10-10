package com.filmfellows.cinemates.domain.chat.model.serviceImpl;

import com.filmfellows.cinemates.app.chat.dto.ChatRoomMovie;
import com.filmfellows.cinemates.app.chat.dto.CinemaInfoByRegion;
import com.filmfellows.cinemates.app.chat.dto.RegionAndCinemaCount;
import com.filmfellows.cinemates.app.chat.dto.RelativeTime;
import com.filmfellows.cinemates.common.Pagination;
import com.filmfellows.cinemates.domain.chat.model.mapper.ChatMapper;
import com.filmfellows.cinemates.domain.chat.model.service.ChatService;
import com.filmfellows.cinemates.domain.chat.model.vo.ChatRoom;
import com.filmfellows.cinemates.domain.chat.model.vo.ChatTag;
import com.filmfellows.cinemates.domain.chat.model.vo.ChatTimeUtils;
import com.filmfellows.cinemates.domain.member.model.vo.ProfileImg;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ChatServiceImpl implements ChatService {

    private ChatMapper cMapper;

    @Autowired
    public ChatServiceImpl(ChatMapper cMapper) {
        this.cMapper = cMapper;
    }


    @Override
    public List<ChatRoomMovie> selectMovieAll() {
        List<ChatRoomMovie> movieList = cMapper.selectMovieAll();
        return movieList;
    }

    @Override
    public List<CinemaInfoByRegion> selectCinemaByRegion(Integer cinemaLocationCode, String movieNo) {
        List<CinemaInfoByRegion> cinemaListByOne = cMapper.selectCinemaByRegion(cinemaLocationCode, movieNo);
        return cinemaListByOne;
    }

    @Override
    public List<RegionAndCinemaCount> selectCinemaCountByRegionByMovie(String movieNo) {
        List<RegionAndCinemaCount> regionAndCinemaCount = cMapper.selectCinemaCountByRegionByMovie(movieNo);
        return regionAndCinemaCount;
    }

    @Override
    public int insertChatRoom(ChatRoom chatRoom) {
        int result = cMapper.insertChatRoom(chatRoom);
        return result;
    }

    @Override
    public int insertTag(ChatTag chatTag) {
        int result = cMapper.insertTag(chatTag);
        return result;
    }

    @Override
    public Map<String, Object> selectChatRoomList(Integer currentPage, int boardLimit, String tagName) {
        // 전체 채팅방 수 계산
        int totalCount = cMapper.getTotalCount(tagName);
        Pagination pn = new Pagination(totalCount, currentPage, boardLimit);

        int limit = pn.getBoardLimit();
        int offset = (currentPage-1) * limit ;
        RowBounds rowBounds = new RowBounds(offset, limit);

        // 채팅방 리스트 조회
        List<ChatRoom> cList = cMapper.selectChatRoomList(rowBounds, tagName);

        // 채팅방 개설 상대 시간 계산
        List<RelativeTime> relativeTimeList = new ArrayList<>();

        for(ChatRoom item : cList){
            RelativeTime rTime = new RelativeTime();
            // 계산 메소드
            String relativeTime = ChatTimeUtils.getRelativeTime(item.getRoomDate());
            rTime.setRoomDate(item.getRoomDate());
            rTime.setRelativeTime(relativeTime);
            relativeTimeList.add(rTime);
        }

        // map 생성
        Map<String, Object> map = new HashMap<>();


        map.put("relativeTimeList", relativeTimeList);
        map.put("cList", cList);
        map.put("pn", pn);

        return map;
    }

    @Override
    public List<ChatTag> selectChatTagList(String status) {
        List<ChatTag> chatTagList = cMapper.selectChatTagList(status);
        return chatTagList;
    }

    @Override
    public List<ProfileImg> selectProfileList() {
        List<ProfileImg> profileList = cMapper.selectProfileList();
        return profileList;
    }



}
