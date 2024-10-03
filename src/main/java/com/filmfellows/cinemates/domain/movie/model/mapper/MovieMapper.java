package com.filmfellows.cinemates.domain.movie.model.mapper;

import com.filmfellows.cinemates.app.movie.dto.MovieInfoResponse;
import com.filmfellows.cinemates.app.movie.dto.UpdateMovieDTO;
import com.filmfellows.cinemates.domain.movie.model.vo.Movie;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface MovieMapper {
    List<Movie> selectMovieList();
    List<MovieInfoResponse> selectMovieDetail(Long movieNo);

    void updateMovie(UpdateMovieDTO updateMovieDTO);
    // 스틸컷 관련 메서드
    void updateStillcut(UpdateMovieDTO.UpdateStillcutDTO stillcut);
    void insertStillcut(@Param("movieNo") Long movieNo, @Param("stillcut") UpdateMovieDTO.UpdateStillcutDTO stillcut);
    void deleteStillcutsByMovieNo(Long movieNo);

    // 트레일러 관련 메서드
    void updateTrailer(UpdateMovieDTO.UpdateTrailerDTO trailer);
    void insertTrailer(@Param("movieNo") Long movieNo, @Param("trailer") UpdateMovieDTO.UpdateTrailerDTO trailer);
    void deleteTrailersByMovieNo(Long movieNo);
}
