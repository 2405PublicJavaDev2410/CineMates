package com.filmfellows.cinemates.domain.movie.model.mapper;

import com.filmfellows.cinemates.app.movie.dto.MovieInfoResponse;
import com.filmfellows.cinemates.domain.movie.model.vo.Movie;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface MovieMapper {
    List<Movie> selectMovieList();
    List<MovieInfoResponse> selectMovieDetail(Long movieNo);
}
