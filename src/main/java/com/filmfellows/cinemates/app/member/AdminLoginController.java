package com.filmfellows.cinemates.app.member;

import com.filmfellows.cinemates.domain.member.model.service.MemberService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@Slf4j
@AllArgsConstructor
public class AdminLoginController {
    private final MemberService mService;

    /**
     * [관리자] 로그인 페이지 이동
     */
    @GetMapping("/admin/login")
    public String showAdminLogin() {
        return "pages/member/adminLogin";
    }

}
