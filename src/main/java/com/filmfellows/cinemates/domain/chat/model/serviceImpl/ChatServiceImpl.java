package com.filmfellows.cinemates.domain.chat.model.serviceImpl;

import com.filmfellows.cinemates.app.chat.dto.ChatRoomMovie;
import com.filmfellows.cinemates.domain.chat.model.mapper.ChatMapper;
import com.filmfellows.cinemates.domain.chat.model.service.ChatService;
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
    public List<String> selectCinemaByRegion(String region) {
        List<String> cinemaListByOne = cMapper.selectCinemaByRegion(region);
        return cinemaListByOne;
    }
}
