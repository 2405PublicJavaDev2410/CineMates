package com.filmfellows.cinemates.domain.movie.model.service.impl;

import com.filmfellows.cinemates.app.movie.dto.*;
import com.filmfellows.cinemates.domain.movie.model.mapper.MovieMapper;
import com.filmfellows.cinemates.domain.movie.model.service.MovieService;
import com.filmfellows.cinemates.domain.movie.model.vo.Movie;
import com.filmfellows.cinemates.domain.movie.model.vo.Review;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class MovieServiceImpl implements MovieService {

    private final MovieMapper movieMapper;



    @Override
    public List<MovieDTO> selectMovieDetail(Long movieNo) {
        return movieMapper.selectMovieDetail(movieNo);
    }

    @Transactional
    public void updateMovie(MovieDTO movieDTO) {
        // 기본 영화 정보 업데이트
        movieMapper.updateMovie(movieDTO);

        Long movieNo = movieDTO.getMovieNo();

        // 트레일러 처리
        movieMapper.deleteTrailersByMovieNo(movieNo);  // 기존 트레일러 모두 삭제
        if (movieDTO.getTrailers() != null) {
            for (MovieDTO.TrailerDTO trailer : movieDTO.getTrailers()) {
                    movieMapper.insertTrailer(movieNo, trailer);
            }
        }
        // 스틸컷 처리
        movieMapper.deleteStillcutsByMovieNo(movieNo);  // 기존 스틸컷 모두 삭제
        if (movieDTO.getStillcuts() != null) {
            for (MovieDTO.StillcutDTO stillcut : movieDTO.getStillcuts()) {
                    movieMapper.insertStillcut(movieNo, stillcut);
            }
        }

    }

    @Transactional
    public void insertMovie(MovieDTO movie) {
        movieMapper.insertMovie(movie);
        Long movieNo = movie.getMovieNo();

        // 트레일러 처리
        if (movie.getTrailers() != null) {
            for (MovieDTO.TrailerDTO trailer : movie.getTrailers()) {
                movieMapper.insertTrailer(movieNo, trailer);
            }
        }
        // 스틸컷 처리
        if (movie.getStillcuts() != null) {
            for (MovieDTO.StillcutDTO stillcut : movie.getStillcuts()) {
                movieMapper.insertStillcut(movieNo, stillcut);
            }
        }
    }

    @Override
    public List<Movie> selectSearchMovie(Map<String, String> paramMap) {
        return movieMapper.selectSearchMovie(paramMap);
    }

    @Override
    public List<Movie> selectMovieList(RowBounds rowBounds, Integer currentPage) {
        return movieMapper.selectMovieList(rowBounds, currentPage);
    }

    @Override
    public int totalMovieCount() {
        return movieMapper.totalMovieCount();
    }

    @Override
    public List<MovieReservationRateDTO> getMovieReservationRates() {
        return movieMapper.getMovieReservationRates();
    }

    @Override
    public List<MovieListDTO> getMoviesByStatusAndSort(String status, int page, int size, String sortBy) {
        int offset = page * size;
        return movieMapper.selectMoviesByStatusAndSort(status, offset, size, sortBy);
    }

    @Override
    public List<ReviewDTO> getReviewByMovieNo(Long movieNo, int page, int size) {
        int offset = page * size;
        return movieMapper.selectReviewByMovieNo(movieNo, offset, size);
    }

    @Override
    public int addReview(Review addReview) {
        int result = movieMapper.insertReview(addReview);
        return result;
    }

    @Override
    public int getTotalReviewCountByMovieNo(Long movieNo) {
        return movieMapper.selectReviewCountByMovieNo(movieNo);
    }

    @Override
    public void removeReview(String memberId, int reviewNo) {
        movieMapper.deleteReview(memberId, reviewNo);
    }

    @Override
    public ReviewDTO getMyReview(Long movieNo, String memberId) {
        return movieMapper.selectMyReview(movieNo, memberId);
    }

    @Override
    public void removeMovie(Long movieNo) {
        movieMapper.deleteMovie(movieNo);
    }

    @Override
    public List<MovieBannerDTO> getMovieBanner() {
        return movieMapper.selectMovieBanner();
    }


}
