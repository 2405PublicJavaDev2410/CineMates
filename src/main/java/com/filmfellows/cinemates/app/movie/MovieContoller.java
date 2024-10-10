package com.filmfellows.cinemates.app.movie;

import com.filmfellows.cinemates.app.movie.dto.MovieListDTO;
import com.filmfellows.cinemates.app.movie.dto.MovieReservationRateDTO;
import com.filmfellows.cinemates.domain.movie.model.service.MovieService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.RowBounds;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@Slf4j
@RequiredArgsConstructor
public class MovieContoller {

    private final MovieService movieService;

    @GetMapping("movie-list")
    public String showMovieList(Model model) {
        List<MovieListDTO> mList = movieService.selectAllMovieList();
        List<MovieReservationRateDTO> reservationRates = movieService.getMovieReservatinRates();

        // 예매율 정보를 Map으로 변환
        Map<Long, Double> rateMap = reservationRates.stream()
                .collect(Collectors.toMap(
                        MovieReservationRateDTO::getMovieNo,
                        MovieReservationRateDTO::getReservationRate,
                        (r1, r2) -> r1  // 중복 키가 있을 경우 첫 번째 값 사용
                ));

        // 영화 정보에 예매율 추가
        mList.forEach(movie -> {
            Double rate = rateMap.get(movie.getMovieNo());
            movie.setReservationRate(rate != null ? String.format("%.1f%%", rate) : "0.0%");
        });


        model.addAttribute("mList", mList);
        return "pages/movie/movieList";
    }

//    public List<MovieReservationRateDTO> getMovieReservationRates(){
//        List<MovieReservationRateDTO> resList = movieService.getMovieReservatinRates();
////        log.info("getMovieReservationRates");
//      return resList;
//    }
}

