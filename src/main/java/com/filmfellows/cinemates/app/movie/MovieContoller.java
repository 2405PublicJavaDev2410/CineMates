package com.filmfellows.cinemates.app.movie;

import com.filmfellows.cinemates.domain.movie.model.service.MovieService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@Slf4j
@RequiredArgsConstructor
public class MovieContoller {

    private final MovieService movieService;

    @GetMapping("movie-list")
    public String showMovieList(){
        return "pages/movie/movieList";
    }
}
