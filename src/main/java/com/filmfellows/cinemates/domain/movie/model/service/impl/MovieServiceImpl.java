package com.filmfellows.cinemates.domain.movie.model.service.impl;

import com.filmfellows.cinemates.app.movie.dto.MovieInfoResponse;
import com.filmfellows.cinemates.app.movie.dto.UpdateMovieDTO;
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

    @Transactional
    public void updateMovie(UpdateMovieDTO updateMovieDTO) {
        // 기본 영화 정보 업데이트
        movieMapper.updateMovie(updateMovieDTO);

        Long movieNo = updateMovieDTO.getMovieNo();

        // 스틸컷 처리
        movieMapper.deleteStillcutsByMovieNo(movieNo);  // 기존 스틸컷 모두 삭제
        if (updateMovieDTO.getStillcuts() != null) {
            for (UpdateMovieDTO.UpdateStillcutDTO stillcut : updateMovieDTO.getStillcuts()) {
                if (stillcut.getStillcutUrl() != null && !stillcut.getStillcutUrl().isEmpty()) {
                    movieMapper.insertStillcut(movieNo, stillcut);
                }
            }
        }

        // 트레일러 처리
        movieMapper.deleteTrailersByMovieNo(movieNo);  // 기존 트레일러 모두 삭제
        if (updateMovieDTO.getTrailers() != null) {
            for (UpdateMovieDTO.UpdateTrailerDTO trailer : updateMovieDTO.getTrailers()) {
                if (trailer.getTrailerUrl() != null && !trailer.getTrailerUrl().isEmpty()) {
                    movieMapper.insertTrailer(movieNo, trailer);
                }
            }
        }
    }
}
