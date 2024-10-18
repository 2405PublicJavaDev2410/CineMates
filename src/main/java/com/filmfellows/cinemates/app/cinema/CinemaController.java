package com.filmfellows.cinemates.app.cinema;



import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

import com.filmfellows.cinemates.domain.cinema.model.service.CinemaService;
import com.filmfellows.cinemates.domain.cinema.model.vo.Cinema;
import com.filmfellows.cinemates.domain.cinema.model.vo.Screen;
import com.filmfellows.cinemates.domain.cinema.model.vo.Showtime;
import com.filmfellows.cinemates.domain.movie.model.vo.Movie;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping("/cinema")
public class CinemaController {

    private CinemaService cService;
    @Autowired
    public CinemaController(CinemaService cService) {
        this.cService=cService;
    }

    @GetMapping("/main")
    public String showmain() {
        return "redirect:/cinema/location?locationcode=1&cinemaNo=1";
    }
    @GetMapping("/location")
    public String showlocationmain(@RequestParam("locationcode") int locationcode,
                                   @RequestParam(value="cinemaNo", required=false,defaultValue="0") int cinemaNo,
                                   @RequestParam(value="selectDate", required=false) LocalDate selectDate,
                                   Model model,
                                   HttpSession session) {
        String memberId=(String)session.getAttribute("memberId");
        List<Cinema> cList= cService.locationsearch(locationcode);
        LocalDate currentdate= LocalDate.now();
        if(cinemaNo!=0) {
            Cinema cinema=cService.onecinemasearch(cinemaNo);
            model.addAttribute("cinema",cinema);

            if(selectDate==null||selectDate.equals(currentdate)) {
                List<Showtime> sList=cService.showtimelistcurrent(cinemaNo);
                if(sList.size()>0) {
                    model.addAttribute("sList",sList);
                    model.addAttribute("selectDate",currentdate);
                }else {
                    sList=null;
                }
            }else{
                List<Showtime> sList=cService.showtimelistdate(cinemaNo, Date.valueOf(selectDate));
                if(sList.size()>0) {
                    model.addAttribute("sList",sList);
                    model.addAttribute("selectDate",selectDate);
                }else {
                    sList=null;
                }
            }
        }else {

        }


        LocalDate day1= currentdate.plusDays(1);
        LocalDate day2= currentdate.plusDays(2);
        LocalDate day3= currentdate.plusDays(3);
        LocalDate day4= currentdate.plusDays(4);
        LocalDate day5= currentdate.plusDays(5);
        LocalDate day6= currentdate.plusDays(6);
        model.addAttribute("cList",cList);
        model.addAttribute("currentdate",currentdate);
        model.addAttribute("day1",day1);
        model.addAttribute("day2",day2);
        model.addAttribute("day3",day3);
        model.addAttribute("day4",day4);
        model.addAttribute("day5",day5);
        model.addAttribute("day6",day6);
        model.addAttribute("memberId",memberId);
        return"pages/cinema/main";
    }
    @GetMapping("/parking/{cinemaNo}&{cinemaAddress}")
    public String showParking(@PathVariable("cinemaNo") Integer cinemaNo,
                              @PathVariable("cinemaAddress") String cinemaAddress,
                              Model model) {

        model.addAttribute("cinemaAddress",cinemaAddress);
        return "pages/cinema/parking/"+cinemaNo;
    }

    @GetMapping("/map{address}")
    public String showMap(@PathVariable("address") String address) {
        return "cinema/parking/map";
    }

}
