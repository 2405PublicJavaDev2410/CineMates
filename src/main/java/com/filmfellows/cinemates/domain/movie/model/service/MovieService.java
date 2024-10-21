package com.filmfellows.cinemates.domain.movie.model.service;

import com.filmfellows.cinemates.app.movie.dto.*;
import com.filmfellows.cinemates.domain.movie.model.vo.Movie;
import com.filmfellows.cinemates.domain.movie.model.vo.Review;
import org.apache.ibatis.session.RowBounds;

import java.util.List;
import java.util.Map;

public interface MovieService {



    List<MovieDTO> selectMovieDetail(Long movieNo);
    void updateMovie(MovieDTO movieDTO);

    void insertMovie(MovieDTO movie);

    List<Movie> selectSearchMovie(Map<String, String> paramMap);

    List<Movie> selectMovieList(RowBounds rowBounds, Integer currentPage);
    int totalMovieCount();

    List<MovieReservationRateDTO> getMovieReservationRates();

    List<MovieListDTO> getComingSoonMovies();

    List<MovieListDTO> getNowShowingMovies();

    List<MovieListDTO> getMoviesByStatusAndSort(String status, int page, int size, String sortBy);

    List<MovieDTO.StillcutDTO> selectStillcutsPaginated(Long movieNo, int page, int size);

    List<ReviewDTO> getReviewByMovieNo(Long movieNo, int page, int size);

    int addReview(Review addReview);

    int getTotalReviewCountByMovieNo(Long movieNo);

    void removeReview(String memberId, int reviewNo);

    ReviewDTO getMyReview(Long movieNo, String memberId);

    void removeMovie(Long movieNo);

    List<MovieBannerDTO> getMovieBanner();
}
