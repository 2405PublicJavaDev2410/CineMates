package com.filmfellows.cinemates.app.test;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class testController {

    @GetMapping("/test/testPage")
    public String showTest(){
        return "pages/test/testPage";
    }
}
