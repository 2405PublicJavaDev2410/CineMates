package com.filmfellows.cinemates.app.cinema;


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

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;


@Controller
@RequestMapping("/admin")
public class AdminCinemaController {

    private CinemaService cService;
    @Autowired
    public AdminCinemaController(CinemaService cService) {
        this.cService=cService;
    }


    @GetMapping("/cinema")
    public String showAdmin(Model model) {
        List<Cinema> cList=cService.allcinema();
        model.addAttribute("cList",cList);
        return "pages/cinema/admin/adminlist";
    }
    @GetMapping("/insert")
    public String showAdmininsert(Model model) {
        return "pages/cinema/admin/insert";
    }
    @PostMapping("/insert")
    public String Admininsert(Model model,Cinema cinema) {
        int result=cService.insertcinema(cinema);
        if(result>0) {
            return "pages/cinema/admin/sucess";
        }else{
            return "pages/cinema/admin/failed";
        }
    }
    @GetMapping("/update{cinemaNo}")
    public String showAdminupdate(@PathVariable("cinemaNo") int cinemaNo,Model model) {
        Cinema cinema=cService.onecinemasearch(cinemaNo);
        model.addAttribute("cinema",cinema);
        return "pages/cinema/admin/update";
    }
    @PostMapping("/update")
    public String Adminupdate(Model model,Cinema cinema) {
        int result=cService.updatecinema(cinema);
        if(result>0) {
            return "pages/cinema/admin/sucess";
        }else{
            return "pages/cinema/admin/failed";
        }
    }
    @GetMapping("/delete{cinemaNo}")
    public String Admindelete(@PathVariable("cinemaNo") int cinemaNo,Model model) {
        int result=cService.deletecinema(cinemaNo);
        if(result>0) {
            return "pages/cinema/admin/sucess";
        }else{
            return "pages/cinema/admin/failed";
        }

    }
    @GetMapping("/admineshowtime{cinemaNo}")
    public String admineshowtime(@PathVariable("cinemaNo") int cinemaNo,Model model) {
        List<Showtime> sList=cService.showtimelist(cinemaNo);
        List<Showtime> allsList=cService.allshowtimelist(cinemaNo);

        model.addAttribute("sList",sList);
        model.addAttribute("cinemaNo",cinemaNo);
        model.addAttribute("allsList",allsList);
        return "pages/cinema/admin/adminshowtime";
    }
    @GetMapping("/showtimeinsert{cinemaNo}")
    public String showtimeinsert(@PathVariable("cinemaNo") int cinemaNo,Model model){

        List<Screen> scrList=cService.onecinemascreen(cinemaNo);
        List<Movie> mList=cService.searchmovie();
        model.addAttribute("mList",mList);
        model.addAttribute("scrList",scrList);

        return "pages/cinema/admin/showtimeinsert";
    }
    @PostMapping("/showtimeinsert")
    public String showtimeinsert(Model model,Showtime showtime) {

        int result=cService.showtimeinsert(showtime);

        if(result>0) {
            return "pages/cinema/admin/sucess";
        }else{
            return "pages/cinema/admin/failed";
        }
    }
    @GetMapping("/screen{cinemaNo}")
    public String showScreen(@PathVariable("cinemaNo") int cinemaNo,Model model) {
        List<Screen> scrList=cService.onecinemascreen(cinemaNo);

        model.addAttribute("scrList",scrList);
        model.addAttribute("cinemaNo",cinemaNo);
        return "pages/cinema/admin/showscreen";
    }
    @GetMapping("/insertscreen{cinemaNo}")
    public String showInsertScreen(@PathVariable("cinemaNo") int cinemaNo,Model model) {
        return "pages/cinema/admin/insertscreen";
    }
    @PostMapping("/insertscreen")
    public String insertScreen(Screen screen) {
        int result=cService.insertScreen(screen);
        if(result>0) {
            return "pages/cinema/admin/sucess";
        }else{
            return "pages/cinema/admin/failed";
        }
    }
    @GetMapping("/updatescreen{screenNo}")
    public String showUpdateScreen(@PathVariable("screenNo") int screenNo,Model model) {
        Screen screen=cService.onescreen(screenNo);
        model.addAttribute("screen",screen);
        return "pages/cinema/admin/updatescreen";
    }
    @PostMapping("updatescreen")
    public String updateScreen(Screen screen) {
        int result =cService.updatescreen(screen);
        if(result>0) {
            return "pages/cinema/admin/sucess";
        }else{
            return "pages/cinema/admin/failed";
        }
    }
    @GetMapping("/deletescreen{screenNo}")
    public String showDeleteScreen(@PathVariable("screenNo") int screenNo) {
        int result=cService.deletescreen(screenNo);
        if(result>0) {
            return "pages/cinema/admin/sucess";
        }else{
            return "pages/cinema/admin/failed";
        }
    }
    @GetMapping("/updateshowtime{showtimeNo}")
    public String showupdateshowtime(@PathVariable("showtimeNo") int showtimeNo,Model model) {
        Showtime showtime=cService.oneshowtime(showtimeNo);
        List<Screen> scrList=cService.onecinemascreen(showtime.getCinemaNo());
        List<Movie> mList=cService.searchmovie();
        model.addAttribute("mList",mList);
        model.addAttribute("scrList",scrList);
        model.addAttribute("showtime",showtime);
        return "pages/cinema/admin/updateshowtime";

    }

    @PostMapping("/updateshowtime")
    public String updateshowtime(Showtime showtime){
        int result=cService.updateshowtime(showtime);
        if(result>0) {
            return "pages/cinema/admin/sucess";
        }else{
            return "pages/cinema/admin/failed";
        }
    }
    @GetMapping("/deleteshowtime{showtimeNo}")
    public String deleteshowtime(@PathVariable("showtimeNo") int showtimeNo){
        int result=cService.deleteshowtime(showtimeNo);
        if(result>0) {
            return "pages/cinema/admin/sucess";
        }else{
            return "pages/cinema/admin/failed";
        }
    }
    @GetMapping("/map")
    public String showmap(Model model) {
        return "pages/cinema/cinemamap";
    }



}
