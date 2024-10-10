package com.filmfellows.cinemates.app.movie;

import com.filmfellows.cinemates.app.movie.dto.*;
import com.filmfellows.cinemates.domain.movie.model.service.MovieService;
import com.filmfellows.cinemates.domain.movie.model.vo.Movie;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@Slf4j
@RequiredArgsConstructor
public class AdminMovieController {

    private final MovieService movieService;

    /**
     * [관리자] 영화 리스트 페이지
     */
    @GetMapping("admin/movie-list")
    public String showMovieList(Model model
            , @RequestParam(value = "cp", required = false, defaultValue = "1") Integer currentPage) {
        int totalCount = movieService.totalMovieCount();
        Pagination pn = new Pagination(totalCount, currentPage);
        int limit = 10;
        int offset = (currentPage - 1) * limit;
        RowBounds rowBounds = new RowBounds(offset, limit);
        List<Movie> movies = movieService.selectMovieList(rowBounds, currentPage);
        model.addAttribute("movies", movies);
        model.addAttribute("pn", pn);
        return "pages/movie/adminMovieList";
    }

    /**
     * [관리자] 영화 검색
     */
    @PostMapping("/admin/search-movie")
    public String searchMovie(Model model
            , @RequestParam("searchCondition") String searchCondition
            , @RequestParam("searchKeyword") String searchKeyword) {
            Map<String, String> paramMap = new HashMap<String, String>();
            paramMap.put("searchCondition", searchCondition);
            paramMap.put("searchKeyword", searchKeyword);
            List<Movie> searchList = movieService.selectSearchMovie(paramMap);
            model.addAttribute("sList", searchList);
            model.addAttribute("searchKeyword", paramMap.get("searchKeyword"));
            model.addAttribute("searchCondition", paramMap.get("searchCondition"));
            log.info(searchList.toString());
            log.info(paramMap.get("searchCondition"));
            log.info(paramMap.get("searchKeyword"));
            return "pages/movie/adminSearchList";
    }


    /**
     * [관리자] 영화 디테일 페이지
     */
    @GetMapping("admin/movie-detail/{movieNo}")
    public String showMovieDetail(@PathVariable Long movieNo, Model model) {
        List<MovieDTO> movieInfo = movieService.selectMovieDetail(movieNo);
        model.addAttribute("movieInfo", movieInfo);
        return "pages/movie/adminMovieDetail";
    }

    /**
     * [관리자] 영화 등록 페이지
     */
    @GetMapping("admin/insert-movie")
    public String showInsertMovie() {
        return "pages/movie/adminInsertMovie";
    }

    /**
     * [관리자] 영화 등록
     */
    @PostMapping("admin/insert-movie")
    public String InsertMovie(MovieDTO movieDTO) {
        MovieDTO movie = new MovieDTO();
        movie.setTitle(movieDTO.getTitle());
        movie.setPosterUrl(movieDTO.getPosterUrl());
        movie.setReleaseDate(movieDTO.getReleaseDate());
        movie.setRunningTime(movieDTO.getRunningTime());
        movie.setRating(movieDTO.getRating());
        movie.setSynopsis(movieDTO.getSynopsis());
        movie.setDirector(movieDTO.getDirector());
        movie.setActors(movieDTO.getActors());
        movie.setGenre(movieDTO.getGenre());
        movie.setProductionCountry(movieDTO.getProductionCountry());
        movie.setScreeningStatus(movieDTO.getScreeningStatus());
        movie.setIsBookable(movieDTO.getIsBookable());
        movie.setTrailers(movieDTO.getTrailers());
        movie.setStillcuts(movieDTO.getStillcuts());
        movieService.insertMovie(movie);
        return "redirect:/admin/movie-list";
    }

    /**
     * [관리자] 영화 정보 수정
     */
    @PostMapping("admin/update-movie")
    public String updateMovieInfo(@ModelAttribute MovieDTO movieDTO) {
        movieService.updateMovie(movieDTO);
        return "redirect:/admin/movie-detail/" + movieDTO.getMovieNo();
    }

    /**
     * Timestamp 에서 String으로 변환
     */
    private String convertTimestampToString(Timestamp timestamp) {
        if (timestamp == null) {
            return "미정";
        }
        LocalDate localDate = timestamp.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        return localDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }

}