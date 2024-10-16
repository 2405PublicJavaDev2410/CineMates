package com.filmfellows.cinemates.app.movie;

import com.filmfellows.cinemates.app.movie.dto.MovieDTO;
import com.filmfellows.cinemates.app.movie.dto.MovieListDTO;
import com.filmfellows.cinemates.app.movie.dto.MovieReservationRateDTO;
import com.filmfellows.cinemates.app.movie.dto.ReviewDTO;
import com.filmfellows.cinemates.domain.movie.model.service.MovieService;
import com.filmfellows.cinemates.domain.movie.model.vo.Review;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
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
        log.info("=================="+reviewList.toString());
        int totalReviews = reviewList.size();
        if (!movieInfo.isEmpty()) {
            MovieDTO movie = movieInfo.get(0);

            int totalTrailers = movie.getTrailers() != null ? movie.getTrailers().size() : 0;
            int totalStillcuts = movie.getStillcuts() != null ? movie.getStillcuts().size() : 0;
            model.addAttribute("totalTrailers", totalTrailers);
            model.addAttribute("totalStillcuts", totalStillcuts);
        }

        model.addAttribute("reviewList", reviewList);
        model.addAttribute("movieInfo", movieInfo);
//        model.addAttribute("stillcuts", stillcuts);
//        model.addAttribute("hasMoreStillcuts", hasMoreStillcuts);
        model.addAttribute("totalReviews", totalReviews);

//        if (Boolean.TRUE.equals(isAjax)) {
//            return "fragments/stillcutList :: stillcutList";
//        }
        return "pages/movie/movieDetail";
    }

//    @PostMapping("/addReview")
//    public String addReview(Model model, Review review, HttpSession session) {
//        String memberId = session.getAttribute("memberId").toString();
//
//        Review addReview = new Review();
//        addReview.setMovieNo(review.getMovieNo());
//        addReview.setMemberId(memberId);
//        addReview.setScore(review.getScore());
//        addReview.setReviewContent(review.getReviewContent());
//
//        movieService.addReview(addReview);
//
////        boolean isLoggedIn = session.getAttribute("memberId") != null;
////        model.addAttribute("isLoggedIn", isLoggedIn);
//
//        return "redirect:/movie-detail/" + addReview.getMovieNo();
//    }

    @PostMapping("/addReview")
    @ResponseBody
    public ResponseEntity<?> addReview(@RequestBody Review review, HttpSession session) {
        String memberId = (String) session.getAttribute("memberId");
//        if (memberId == null) {
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
//                    .body(Map.of("success", false, "message", "로그인이 필요합니다."));
//        }

        Review addReview = new Review();
        addReview.setMovieNo(review.getMovieNo());
        addReview.setMemberId(memberId);
        addReview.setScore(review.getScore());
        addReview.setReviewContent(review.getReviewContent());
//        addReview.setRegDate(new Date()); // 현재 날짜 설정


        try {
            int result = movieService.addReview(addReview);
            if (result > 0) {
                return ResponseEntity.ok(Map.of("success", true, "message", "리뷰가 성공적으로 등록되었습니다."));
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body(Map.of("success", false, "message", "리뷰 등록에 실패했습니다."));
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("success", false, "message", "리뷰 등록 중 오류가 발생했습니다."));
        }
    }
}

