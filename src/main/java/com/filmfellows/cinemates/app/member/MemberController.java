package com.filmfellows.cinemates.app.member;

import com.filmfellows.cinemates.domain.member.model.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@Slf4j
@RequiredArgsConstructor
public class MemberController {
    private final MemberService mService;

    /**
     * 담당자 : 엄태운
     * 관련기능 : 로그인 페이지 이동
     */
    @GetMapping("/member/login")
    public String showLogin() {
        return "member/login";
    }

    /**
     * 담당자 : 엄태운
     * 관련기능 : 회원가입 페이지 이동
     */
    @GetMapping("/member/register")
    public String showRegisterMember() {
        return "member/register";
    }

    /**
     * 담당자 : 엄태운
     * 관련기능 : 아이디/비밀번호 찾기 페이지 이동
     */
    @GetMapping("/member/find-info")
    public String showFindIdOrPw() {
        return "member/find-info";
    }

    /**
     * 담당자 : 엄태운
     * 관련기능 : 회원정보수정 페이지 이동
     */
    @GetMapping("/member/update")
    public String showModifyMember() {
        return "member/update";
    }

    /**
     * 담당자 : 엄태운
     * 관련기능 : 회원탈퇴 페이지 이동
     */
    @GetMapping("/member/remove")
    public String showRemoveMember() {
        return "member/remove";
    }

    /**
     * 담당자 : 엄태운
     * 관련기능 : 회원정보 등록
     */
    public int insertMember() {
        return 0;
    }

    /**
     * 담당자 : 엄태운
     * 관련기능 : 회원정보 수정
     */
    public int updateMember() {
        return 0;
    }

    /**
     * 담당자 : 엄태운
     * 관련기능 : 회원정보 삭제
     */
    public int deleteMember() {
        return 0;
    }

    /**
     * 담당자 : 엄태운
     * 관련기능 : 로그인
     */
    public String loginMember() {
        return "";
    }

    /**
     * 담당자 : 엄태운
     * 관련기능 : 로그아웃
     */
    public String logoutMember() {
        return "";
    }

    /**
     * 담당자 : 엄태운
     * 관련기능 : 아이디 찾기
     */
    public String findMemberId() {
        return "";
    }

    /**
     * 담당자 : 엄태운
     * 관련기능 : 비밀번호 찾기
     */
    public String findMemberPw() {
        return "";
    }

}
