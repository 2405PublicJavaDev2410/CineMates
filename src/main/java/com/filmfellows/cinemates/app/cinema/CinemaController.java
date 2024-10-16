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
        System.out.println("selectdate:"+selectDate);
        String memberId=(String)session.getAttribute("memberId");
        List<Cinema> cList= cService.locationsearch(locationcode);
        LocalDate currentdate= LocalDate.now();
        System.out.println("currentdate:"+currentdate);
        if(cinemaNo!=0) {
            Cinema cinema=cService.onecinemasearch(cinemaNo);
            model.addAttribute("cinema",cinema);

            if(selectDate==null||selectDate.equals(currentdate)) {
                System.out.println("이쪽으로 들어왔어 :"+currentdate);
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

        System.out.println(address);
        return "cinema/parking/map";
    }
    @GetMapping("/admin")
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

        System.out.println(cinema.toString());
        int result=cService.insertcinema(cinema);

        if(result>0) {
            return "pages/cinema/admin/sucess";
        }else{
            return "pages/cinema/admin/failed";
        }
    }
    @GetMapping("/update{cinemaNo}")
    public String showAdminupdate(@PathVariable("cinemaNo") int cinemaNo,Model model) {
        System.out.println(cinemaNo);
        Cinema cinema=cService.onecinemasearch(cinemaNo);
        model.addAttribute("cinema",cinema);
        return "pages/cinema/admin/update";
    }
    @PostMapping("/update")
    public String Adminupdate(Model model,Cinema cinema) {
        System.out.println(cinema.toString());
        int result=cService.updatecinema(cinema);

        if(result>0) {
            return "pages/cinema/admin/sucess";
        }else{
            return "pages/cinema/admin/failed";
        }
    }
    @GetMapping("/delete{cinemaNo}")
    public String Admindelete(@PathVariable("cinemaNo") int cinemaNo,Model model) {
        System.out.println(cinemaNo);
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
        System.out.println(showtime.toString());

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
        System.out.println(screen.toString());
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



}
