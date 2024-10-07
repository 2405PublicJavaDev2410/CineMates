package com.filmfellows.cinemates.app.movie;

import com.filmfellows.cinemates.app.movie.dto.MovieListDTO;
import com.filmfellows.cinemates.domain.movie.model.service.MovieService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@Slf4j
@RequiredArgsConstructor
public class MovieContoller {

    private final MovieService movieService;

    @GetMapping("movie-list")
    public String showMovieList(Model model) {
        List<MovieListDTO> mList = movieService.selectMovieList();
        model.addAttribute("mList", mList);
        return "pages/movie/movieList";
    }
}
