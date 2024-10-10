package com.filmfellows.cinemates.app.movie;

import com.filmfellows.cinemates.app.movie.dto.MovieListDTO;
import com.filmfellows.cinemates.app.movie.dto.MovieReservationRateDTO;
import com.filmfellows.cinemates.domain.movie.model.service.MovieService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.RowBounds;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@Slf4j
@RequiredArgsConstructor
public class MovieContoller {

    private final MovieService movieService;

//    @GetMapping("movie-list")
//    public String showMovieList(Model model ) {
//        List<MovieListDTO> mList = movieService.selectAllMovieList();
//        List<MovieReservationRateDTO> reservationRates = movieService.getMovieReservatinRates();
//
//        // 예매율 정보를 Map으로 변환
//        Map<Long, Double> rateMap = reservationRates.stream()
//                .collect(Collectors.toMap(
//                        MovieReservationRateDTO::getMovieNo,
//                        MovieReservationRateDTO::getReservationRate,
//                        (r1, r2) -> r1  // 중복 키가 있을 경우 첫 번째 값 사용
//                ));
//
//        // 영화 정보에 예매율 추가
//        mList.forEach(movie -> {
//            Double rate = rateMap.get(movie.getMovieNo());
//            movie.setReservationRate(rate != null ? String.format("%.1f%%", rate) : "0.0%");
//        });
//
//
//        model.addAttribute("mList", mList);
//        return "pages/movie/movieList";
//    }


//    @GetMapping("/api/movies")
//    public List<Movie> getMovies(@RequestParam String status) {
//        if ("coming-soon".equals(status)) {
//            return movieService.getComingSoonMovies();
//        } else if ("now-showing".equals(status)) {
//            return movieService.getNowShowingMovies();
//        } else {
//            throw new IllegalArgumentException("Invalid status");
//        }
//    }


//    @GetMapping("/movie-list")
//    @ResponseBody
//    public List<MovieListDTO> getMovies(@RequestParam(required = false, defaultValue = "NOW SHOWING") String status) {
//        List<MovieListDTO> mList = movieService.getMoviesByStatus(status);
//        List<MovieReservationRateDTO> reservationRates = movieService.getMovieReservationRates();
//
//        Map<Long, Double> rateMap = reservationRates.stream()
//                .collect(Collectors.toMap(
//                        MovieReservationRateDTO::getMovieNo,
//                        MovieReservationRateDTO::getReservationRate,
//                        (r1, r2) -> r1
//                ));
//
//        mList.forEach(movie -> {
//            Double rate = rateMap.get(movie.getMovieNo());
//            movie.setReservationRate(rate != null ? String.format("%.1f%%", rate) : "0.0%");
//        });
//
//        return mList;
//    }

    @GetMapping("/movie-list")
    public Object getMovies(@RequestParam(required = false, defaultValue = "NOW SHOWING") String status,
                            Model model,
                            HttpServletRequest request,
                            @RequestParam(defaultValue = "0") int page,
                            @RequestParam(defaultValue = "20") int size) {
        List<MovieListDTO> mList = movieService.getMoviesByStatus(status, page, size);
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
            return "pages/movie/movieList"; // Thymeleaf 템플릿 이름
        }
    }
}

