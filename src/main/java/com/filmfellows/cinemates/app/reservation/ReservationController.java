package com.filmfellows.cinemates.app.reservation;

import com.filmfellows.cinemates.domain.reservation.model.Service.ReservationService;
import com.filmfellows.cinemates.domain.reservation.model.vo.Reservation;
import com.filmfellows.cinemates.domain.reservation.model.vo.ReservationDTO;
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
        List<ReservationDTO> rList = rService.showReservationPage();

        model.addAttribute("rList", rList);
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
        for(String address : addresses) {
            List<String> cinemas = rService.selectCinemas(address);
            allCinemas.addAll(cinemas);
            System.out.println(cinemas);
       }
        return ResponseEntity.ok(allCinemas);
    }

}
