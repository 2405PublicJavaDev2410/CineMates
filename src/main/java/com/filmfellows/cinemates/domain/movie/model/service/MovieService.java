package com.filmfellows.cinemates.domain.movie.model.service;

import com.filmfellows.cinemates.app.movie.dto.MovieInfoResponse;
import com.filmfellows.cinemates.app.movie.dto.UpdateMovieDTO;
import com.filmfellows.cinemates.domain.movie.model.vo.Movie;

import java.util.List;

public interface MovieService {
    List<Movie> selectMovieList();
    List<MovieInfoResponse> selectMovieDetail(Long movieNo);
    void updateMovie(UpdateMovieDTO updateMovieDTO);
}
