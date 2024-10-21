package com.filmfellows.cinemates.domain.movie.model.mapper;

import com.filmfellows.cinemates.app.movie.dto.*;
import com.filmfellows.cinemates.domain.movie.model.vo.Movie;
import com.filmfellows.cinemates.domain.movie.model.vo.Review;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import java.util.List;
import java.util.Map;

@Mapper
public interface MovieMapper {

    // [관리자] 영화 세부정보
    List<MovieDTO> selectMovieDetail(Long movieNo);
    // [관리자] 영화 정보 수정
    void updateMovie(MovieDTO MovieDTO);
    // [관리자] 스틸컷 관련 메서드(등록, 삭제)
    void insertStillcut(@Param("movieNo") Long movieNo, @Param("stillcut") MovieDTO.StillcutDTO stillcut);
    void deleteStillcutsByMovieNo(Long movieNo);
    // [관리자] 트레일러 관련 메서드(등록, 삭제)
    void insertTrailer(@Param("movieNo") Long movieNo, @Param("trailer") MovieDTO.TrailerDTO trailer);
    void deleteTrailersByMovieNo(Long movieNo);
    // [관리자] 영화 등록
    void insertMovie(MovieDTO movie);
    // [관리자] 영화 검색
    List<Movie> selectSearchMovie(Map<String, String> paramMap);
    // [관리자] 영화 리스트
    List<Movie> selectMovieList(RowBounds rowBounds, Integer currentPage);
    int totalMovieCount();

    List<MovieReservationRateDTO> getMovieReservationRates();

    List<MovieListDTO> selectComingSoonMovies();

    List<MovieListDTO> selectNowShowingMovies();

    List<MovieListDTO> selectMoviesByStatusAndSort(@Param("status") String status,
                                            @Param("offset") int offset,
                                            @Param("size") int size,
                                            @Param("sortBy") String sortBy);

    List<MovieDTO.StillcutDTO> selectStillcutsPaginated(@Param("movieNo") Long movieNo,
                                                        @Param("offset") int offset,
                                                        @Param("size") int size);

    List<ReviewDTO> selectReviewByMovieNo(@Param("movieNo") Long movieNo,
                                          @Param("offset") int offset,
                                          @Param("size") int size);

    int insertReview(Review addReview);

    int selectReviewCountByMovieNo(Long movieNo);

    void deleteReview(String memberId, int reviewNo);

    ReviewDTO selectMyReview(Long movieNo, String memberId);

    void deleteMovie(Long movieNo);

    List<MovieBannerDTO> selectMovieBanner();
}
