package com.filmfellows.cinemates.app.reservation;

import com.filmfellows.cinemates.domain.reservation.model.Service.ReservationService;
import com.filmfellows.cinemates.domain.reservation.model.vo.Reservation;
import com.filmfellows.cinemates.domain.reservation.model.vo.ReservationDTO;
import com.filmfellows.cinemates.domain.reservation.model.vo.ShowInfoDTO;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Controller
public class ReservationController {
    private static final String ALPHA_NUMERIC_STRING = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static SecureRandom random = new SecureRandom();

    @Autowired
    ReservationService rService;
    @Autowired
    private ReservationDTO reservationDTO;

    private static String generateRandomString(int length) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < length; i++) {
            int character = random.nextInt(ALPHA_NUMERIC_STRING.length());
            builder.append(ALPHA_NUMERIC_STRING.charAt(character));
        }
        return builder.toString();
    }

    @GetMapping("/Ticketing")
    public String showShowTimePage(Model model, HttpSession session) {
//        String memberId = (String)session.getAttribute("memberId");
//        if(memberId==null) {
//            return "redirect:/";
//        }
//        List<ReservationDTO> rList = rService.showReservationPage();
        List<String> rList = rService.selectCinemaName();
        List<String> processedList = new ArrayList<>();

        for (String cinemaGroup : rList) {
            String[] cinemas = cinemaGroup.split(",");
            for (String cinema : cinemas) {
                processedList.add(cinema.trim());
            }
        }
        model.addAttribute("rList", processedList);
        System.out.println("보여줘" + processedList);
        return "pages/reservation/Showtime";
    }

    @PostMapping("/Ticketing/PersonSeat")
    public String showPersonSeatPage(@ModelAttribute ReservationDTO rDTO, Model model) {

        String randomString = generateRandomString(10);
        rDTO.setReservationNo(randomString);
        System.out.println(rDTO);
        List<ReservationDTO> rList = rService.showReservedSeats();
        System.out.println(rList);
        model.addAttribute("rList", rList);
        model.addAttribute("rDTO", rDTO);
        return "pages/reservation/PersonSeat";
    }

    @GetMapping("/getCinemas")
    public ResponseEntity<List<String>> selectCinemas(@RequestParam String cinemaAddress) {
        List<String> addresses = Arrays.asList(cinemaAddress.split("/"));
        List<String> allCinemas = new ArrayList<>();
        for (String address : addresses) {
            List<String> cinemas = rService.selectCinemas(address);
            allCinemas.addAll(cinemas);
            System.out.println(cinemas);
        }
        return ResponseEntity.ok(allCinemas);
    }

    @GetMapping("/getMovies")
    public ResponseEntity<List<String>> selectMovies(@RequestParam String cinemaName) {
        System.out.println("영화 이름" + cinemaName);
        List<String> mList = rService.selectMovies(cinemaName);
        System.out.println("영화 목록: " + mList);
        return ResponseEntity.ok(mList);
    }

    @GetMapping("/getShowtimes")
    public ResponseEntity<List<ShowInfoDTO>> selectShowInfo(@RequestParam String cinemaName, @RequestParam String title) {
        List<ShowInfoDTO> sList = rService.selectShowInfo(cinemaName, title);
        List<ReservationDTO> rList = rService.selectReservationSeat();

        for (ShowInfoDTO show : sList) {
            int totalSeats = Integer.parseInt(show.getScreenSeat());
            int reservedSeats = (int) rList.stream()
                    .filter(r -> r.getScreenName().equals(show.getScreenName()) &&
                            r.getShowtimeTime().equals(show.getShowtimeTime()))
                    .flatMap(r -> Arrays.stream(r.getReservationSeat().split(",")))
                    .count();
            int availableSeats = totalSeats - reservedSeats;
            show.setAvailableSeats(availableSeats);
        }

        System.out.println("나와라" + sList);
        System.out.println("rList: " + rList);
        return ResponseEntity.ok(sList);
    }
}
