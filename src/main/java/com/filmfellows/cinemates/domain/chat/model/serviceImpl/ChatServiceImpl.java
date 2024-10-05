package com.filmfellows.cinemates.domain.chat.model.serviceImpl;

import com.filmfellows.cinemates.app.chat.dto.ChatRoomMovie;
import com.filmfellows.cinemates.app.chat.dto.CinemaInfoByRegion;
import com.filmfellows.cinemates.app.chat.dto.RegionAndCinemaCount;
import com.filmfellows.cinemates.domain.chat.model.mapper.ChatMapper;
import com.filmfellows.cinemates.domain.chat.model.service.ChatService;
import com.filmfellows.cinemates.domain.chat.model.vo.ChatRoom;
import com.filmfellows.cinemates.domain.chat.model.vo.ChatTag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
    public List<ChatRoom> selectChatRoomList() {
        List<ChatRoom> ChatRoomList = cMapper.selectChatRoomList();
        return ChatRoomList;
    }

    @Override
    public List<ChatRoom> selectChatTagList() {
        List<ChatRoom> chatTagList = cMapper.selectChatTagList();
        return chatTagList;
    }
}
