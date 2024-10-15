package com.filmfellows.cinemates.app.movie;

import com.filmfellows.cinemates.app.movie.dto.MovieDTO;
import com.filmfellows.cinemates.app.movie.dto.MovieListDTO;
import com.filmfellows.cinemates.app.movie.dto.MovieReservationRateDTO;
import com.filmfellows.cinemates.app.movie.dto.ReviewDTO;
import com.filmfellows.cinemates.domain.movie.model.service.MovieService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@Slf4j
@RequiredArgsConstructor
public class MovieContoller {

    private final MovieService movieService;

    @GetMapping("/movie-list")
    public Object showMovieList(@RequestParam(required = false, defaultValue = "NOW SHOWING") String status,
                            Model model,
                            HttpServletRequest request,
                            @RequestParam(defaultValue = "0") int page,
                            @RequestParam(defaultValue = "20") int size,
                            @RequestParam(defaultValue = "releaseDate") String sortBy) {
        List<MovieListDTO> mList = movieService.getMoviesByStatusAndSort(status, page, size, sortBy);
        List<MovieReservationRateDTO> reservationRates = movieService.getMovieReservationRates();

        Map<Long, Double> rateMap = reservationRates.stream()
                .collect(Collectors.toMap(
                        MovieReservationRateDTO::getMovieNo,
                        MovieReservationRateDTO::getReservationRate,
                        (r1, r2) -> r1
                ));

        mList.forEach(movie -> {
            Double rate = rateMap.get(movie.getMovieNo());
            movie.setReservationRate(rate != null ? String.format("%.1f%%", rate) : "0.0%");
        });

        // AJAX 요청 확인
        if ("XMLHttpRequest".equals(request.getHeader("X-Requested-With"))) {
            // AJAX 요청인 경우 JSON 응답
            return ResponseEntity.ok(mList);
        } else {
            // 일반 요청인 경우 HTML 페이지 렌더링
            model.addAttribute("mList", mList);
            return "pages/movie/movieList";
        }
    }

    @GetMapping("/movie-detail/{movieNo}")
    public String showMovieDetail(@PathVariable Long movieNo, Model model
//                                  @RequestParam(defaultValue = "0") int page,
//                                  @RequestParam(defaultValue = "5") int size,
//                                  @RequestParam(required = false) Boolean isAjax
    ) {
        List<MovieDTO> movieInfo = movieService.selectMovieDetail(movieNo);
//        List<MovieDTO.StillcutDTO> stillcuts = movieService.selectStillcutsPaginated(movieNo, page, size);
//        boolean hasMoreStillcuts = stillcuts.size() == size;

        List<ReviewDTO> reviewList = movieService.getReviewByMovieNo(movieNo);

        int trailerCount = movieService.getTrailrtConunt(movieNo);
        int stillcutCount = movieService.getStillcutCount(movieNo);
        model.addAttribute("reviewList", reviewList);
        model.addAttribute("movieInfo", movieInfo);
//        model.addAttribute("stillcuts", stillcuts);
//        model.addAttribute("hasMoreStillcuts", hasMoreStillcuts);
        model.addAttribute("trailerCount", trailerCount);
        model.addAttribute("stillcutCount", stillcutCount);

//        if (Boolean.TRUE.equals(isAjax)) {
//            return "fragments/stillcutList :: stillcutList";
//        }
        return "pages/movie/movieDetail";
    }
}

