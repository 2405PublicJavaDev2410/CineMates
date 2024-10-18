package com.filmfellows.cinemates.app.main;

import com.filmfellows.cinemates.app.main.dto.boxOfficeDTO;
import com.filmfellows.cinemates.domain.main.model.service.MainService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@Slf4j
@RequiredArgsConstructor
public class MainController {

    private final MainService mainService;

    @GetMapping("/")
    public String showMain(Model model) {
        List<boxOfficeDTO> bList = mainService.getBoxOfficeList();
//        List<MovieListDTO> mList = movieService.selectAllMovieList();
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

        model.addAttribute("bList", bList);
        log.info("===================" + bList.toString());
        return "pages/main/index";
    }
}
