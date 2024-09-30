package com.filmfellows.cinemates.app.reservation;

import com.filmfellows.cinemates.domain.reservation.model.Service.ReservationService;
import com.filmfellows.cinemates.domain.reservation.model.vo.Reservation;
import com.filmfellows.cinemates.domain.reservation.model.vo.ReservationDTO;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class ReservationController {
    @Autowired
    ReservationService rService;

    @GetMapping("/Ticketing")
    public String showReservationPage(Model model, HttpSession session) {
        session.getAttribute("memberId");
        List<ReservationDTO> rList = rService.showReservationPage();
        model.addAttribute("rList",rList);
        return "pages/Showtime";
    }

    @PostMapping("/Order")
    public String showOrderPage (@ModelAttribute ReservationDTO rDTO) {

    }

    @PostMapping("/Ticketing")
    public String insertReservationInfo(@ModelAttribute ReservationDTO rDTO) {
        int result = rService.insertReservationInfo(rDTO);
        return "pages/PersonSeat";
    }
}
