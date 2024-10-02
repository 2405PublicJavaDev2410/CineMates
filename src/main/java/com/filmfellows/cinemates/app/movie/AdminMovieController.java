package com.filmfellows.cinemates.app.movie;

import com.filmfellows.cinemates.app.movie.dto.MovieInfoRequest;
import com.filmfellows.cinemates.app.movie.dto.MovieInfoResponse;
import com.filmfellows.cinemates.domain.movie.model.service.MovieService;
import com.filmfellows.cinemates.domain.movie.model.vo.Movie;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Controller
@Slf4j
@RequiredArgsConstructor
public class AdminMovieController {

    private final MovieService movieService;

    /**
     * [관리자] 영화 리스트 페이지
     */
    @GetMapping("admin/movie-list")
    public String showMovieList(Model model){
        List<Movie> movies= movieService.selectMovieList();
//        for (Movie movie : movies) {
//            String formattedDate = convertTimestampToString(movie.getReleaseDate());
//            movie.setFormattedReleaseDate(formattedDate);
//        }
        model.addAttribute("movies", movies);
        return "pages/movie/adminMovieList";
    }

    /**
     * [관리자] 영화 디테일 페이지
     */
    @GetMapping("admin/movie-detail/{movieNo}")
    public String showMovieDetail(@PathVariable Long movieNo, Model model){
        List<MovieInfoResponse> movieInfo = movieService.selectMovieDetail(movieNo);
//        log.info(movieInfo.toString());
        model.addAttribute("movieInfo", movieInfo);
        return "pages/movie/adminMovieDetail";
    }

    /**
     * [관리자] 영화 정보 수정
     */
    @PostMapping("admin/update-movie")
    public String updateMovieInfo(MovieInfoRequest request, @RequestParam Long movieNo) {

        return "redirect:/admin/movie-detail/" + movieNo;
    }


    /**
     * Timestamp 에서 String으로 변환
     */
    private String convertTimestampToString(Timestamp timestamp) {
        if(timestamp == null) {
            return "미정";
        }
        LocalDate localDate = timestamp.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        return localDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }



}
