package com.filmfellows.cinemates.domain.movie.model.service.impl;

import com.filmfellows.cinemates.app.movie.dto.MovieInfoResponse;
import com.filmfellows.cinemates.domain.movie.model.mapper.MovieMapper;
import com.filmfellows.cinemates.domain.movie.model.service.MovieService;
import com.filmfellows.cinemates.domain.movie.model.vo.Movie;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class MovieServiceImpl implements MovieService {

    private final MovieMapper movieMapper;

    @Override
    public List<Movie> selectMovieList() {
        return movieMapper.selectMovieList();
    }

    @Override
    public List<MovieInfoResponse> selectMovieDetail(Long movieNo) {
        return movieMapper.selectMovieDetail(movieNo);
    }









}
