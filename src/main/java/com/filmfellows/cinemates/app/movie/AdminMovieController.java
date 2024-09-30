package com.filmfellows.cinemates.app.movie;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminMovieController {

    @GetMapping("admin/movie-list")
    public String showMovieList(){
        return "movie/adminMovieList";
    }

    @GetMapping("admin/insert-movie")
    public String showInsertMovie(){
        return "movie/adminInsertMovie";
    }
}
