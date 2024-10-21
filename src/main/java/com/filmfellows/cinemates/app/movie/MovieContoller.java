package com.filmfellows.cinemates.app.movie;

import com.filmfellows.cinemates.app.movie.dto.*;
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

import java.util.HashMap;
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
        List<MovieBannerDTO> bList = movieService.getMovieBanner();

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
            model.addAttribute("bList", bList);
            return "pages/movie/movieList";
        }
    }

    @GetMapping("/movie-detail/{movieNo}")
    public Object showMovieDetail(@PathVariable Long movieNo, Model model,
                                  HttpServletRequest request,
                                  HttpSession session,
                                  @RequestParam(defaultValue = "0") int page,
                                  @RequestParam(defaultValue = "10") int size) {

        List<MovieDTO> movieInfo = movieService.selectMovieDetail(movieNo);
        List<ReviewDTO> reviewList = movieService.getReviewByMovieNo(movieNo, page, size);

        ReviewDTO myReview = null;
        String memberId = (String) session.getAttribute("memberId");
        if(memberId != null) {
            myReview = movieService.getMyReview(movieNo, memberId);
        }

        int totalReviews = movieService.getTotalReviewCountByMovieNo(movieNo);

        if (!movieInfo.isEmpty()) {
            MovieDTO movie = movieInfo.get(0);

            int totalTrailers = movie.getTrailers() != null ? movie.getTrailers().size() : 0;
            int totalStillcuts = movie.getStillcuts() != null ? movie.getStillcuts().size() : 0;
            model.addAttribute("totalTrailers", totalTrailers);
            model.addAttribute("totalStillcuts", totalStillcuts);
        }

        Map<String, Object> response = new HashMap<>();
        response.put("reviews", reviewList);
        response.put("myReview", myReview);
        response.put("totalReviews", totalReviews);

        if ("XMLHttpRequest".equals(request.getHeader("X-Requested-With"))) {
            // AJAX 요청인 경우 JSON 응답
            return ResponseEntity.ok(response);
        } else {
            // 일반 요청인 경우 HTML 페이지 렌더링
            model.addAttribute("reviewList", reviewList);
            model.addAttribute("myReview", myReview);
            model.addAttribute("movieInfo", movieInfo);
            model.addAttribute("totalReviews", totalReviews);

            return "pages/movie/movieDetail";
        }
    }


    @PostMapping("/addReview")
    @ResponseBody
    public ResponseEntity<?> addReview(@RequestBody Review review, HttpSession session) {
        String memberId = (String) session.getAttribute("memberId");
        if (memberId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("success", false, "message", "로그인이 필요합니다."));
        }

        Review addReview = new Review();
        addReview.setMovieNo(review.getMovieNo());
        addReview.setMemberId(memberId);
        addReview.setScore(review.getScore());
        addReview.setReviewContent(review.getReviewContent());

        log.info(addReview.toString());
//        boolean success = movieService.addReview(addReview);
        int result = movieService.addReview(addReview);
//        if (success) {
//            return ResponseEntity.ok(Map.of("success", true, "message", "리뷰가 성공적으로 추가되었습니다."));
//        } else {
//            return ResponseEntity.ok(Map.of("success", false, "message", "이미 이 영화에 대한 리뷰를 작성하셨습니다."));
//        }
        try {
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

    @DeleteMapping("/removeReview/{reviewNo}")
    @ResponseBody
    public ResponseEntity<?> removeReview(HttpSession session, @PathVariable int reviewNo) {
        String memberId = (String) session.getAttribute("memberId");
        if (memberId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인이 필요합니다.");
        }

        try {
            movieService.removeReview(memberId, reviewNo);
            return ResponseEntity.ok().body(Map.of("success", true));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("success", false, "message", e.getMessage()));
        }
    }

    @GetMapping("/checkLoginAndReview")
    @ResponseBody
    public ResponseEntity<?> checkLoginAndReview(HttpSession session, Long movieNo) {
        String memberId = (String) session.getAttribute("memberId");
        if (memberId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("success", false, "message", "로그인이 필요합니다."));
        }

        ReviewDTO existingReview = movieService.getMyReview(movieNo, memberId);
        if (existingReview == null) {
            return ResponseEntity.ok(Map.of("success", true, "message", "리뷰가 성공적으로 추가되었습니다."));
        }
        return ResponseEntity.ok(Map.of("success", false, "message", "이미 이 영화에 대한 리뷰를 작성하셨습니다."));
    }
}

