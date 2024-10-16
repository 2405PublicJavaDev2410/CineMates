package com.filmfellows.cinemates.app.member;

import com.filmfellows.cinemates.app.member.dto.LoginRequest;
import com.filmfellows.cinemates.domain.member.model.service.MemberService;
import com.filmfellows.cinemates.domain.member.model.vo.Member;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@Slf4j
@AllArgsConstructor
public class AdminLoginController {
    private final MemberService mService;

    /**
     * [관리자] 로그인 페이지 이동
     */
    @GetMapping("/admin/login")
    public String showAdminLogin(HttpSession session) {
        session.invalidate();
        return "pages/member/adminLogin";
    }

    /**
     * [관리자] 로그인 페이지
     */
    @PostMapping("/admin/login")
    @ResponseBody
    public String adminLogin(@RequestBody LoginRequest loginRequest,
             HttpSession session) {
        Member member = new Member();
        member.setMemberId(loginRequest.getMemberId());
        member.setMemberPw(loginRequest.getMemberPw());
        member = mService.loginMember(member);
        if(member == null || !member.getRole().equals("ADMIN")) {
            return "fail";
        }
        session.setAttribute("memberId", member.getMemberId());
        session.setAttribute("name", member.getName());
        session.setAttribute("role", member.getRole());
        return "success";
    }
}
