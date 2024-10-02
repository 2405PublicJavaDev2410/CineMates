package com.filmfellows.cinemates.app.member;

import com.filmfellows.cinemates.app.member.dto.*;
import com.filmfellows.cinemates.domain.member.model.service.MemberService;
import com.filmfellows.cinemates.domain.member.model.vo.Member;
import com.filmfellows.cinemates.domain.member.model.vo.ProfileImg;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Controller
@Slf4j
@RequiredArgsConstructor
public class MemberController {
    private final MemberService mService;

    /**
     * 담당자 : 엄태운
     * 관련기능 : 로그인 페이지 이동
     */
    @GetMapping("/login")
    public String showLogin() {
        return "pages/member/login";
    }

    /**
     * 담당자 : 엄태운
     * 관련기능 : 회원가입 페이지 이동
     */
    @GetMapping("/register")
    public String showRegisterMember() {
        return "pages/member/register";
    }

    /**
     * 담당자 : 엄태운
     * 관련기능 : 아이디/비밀번호 찾기 페이지 이동
     */
    @GetMapping("/find-info")
    public String showFindIdOrPw() {
        return "pages/member/find-info";
    }

    /**
     * 담당자 : 엄태운
     * 관련기능 : 회원정보수정 페이지 이동
     */
    @GetMapping("/my-page/update")
    public String showModifyMember(HttpSession session, Model model) {
        String memberId = session.getAttribute("memberId").toString();
        Member member = mService.getOneMember(memberId);
        ProfileImg profileImg = mService.getOneProfileImg(memberId);
        String birthDate = convertTimestampToString(member.getBirthDate());
        model.addAttribute("member", member);
        model.addAttribute("birthDate", birthDate);
        if(profileImg != null) {
            log.info(profileImg.toString());
            model.addAttribute("profileImg", profileImg);
        }
        return "pages/mypage/update";
    }

    /**
     * 담당자 : 엄태운
     * 관련기능 : 회원탈퇴 페이지 이동
     */
    @GetMapping("/my-page/remove")
    public String showRemoveMember(HttpSession session, Model model) {
        String memberId = session.getAttribute("memberId").toString();
        Member member = mService.getOneMember(memberId);
        model.addAttribute("member", member);
        return "pages/mypage/remove";
    }

    /**
     * 담당자 : 엄태운
     * 관련기능 : 회원정보 등록
     */
    @PostMapping("/register")
    public String insertMember(@ModelAttribute @Valid RegisterRequest registerRequest,
            @RequestParam(value="profileImg", required=false) MultipartFile profileImg)
            throws IllegalStateException, IOException {
//        if(registerRequest.getMemberPw().equals(registerRequest.getCheckPassword())) {
        Member member = new Member();
        member.setMemberId(registerRequest.getMemberId());
        member.setMemberPw(registerRequest.getMemberPw());
        member.setName(registerRequest.getName());
        member.setBirthDate(convertStringToTimestamp(String.valueOf(registerRequest.getBirthDate())));
        member.setEmail(registerRequest.getEmail());
        member.setPhone(registerRequest.getPhone());
        log.info(member.toString());
        // 프로필 이미지 파일이 있는 경우에만 처리
        int result = mService.insertMember(member,
                (profileImg != null && !profileImg.isEmpty() ? profileImg : null));
        if(result == 0) {
            throw new IllegalStateException();
        }
//            }
        return "redirect:/login";
    }

    /**
     * 담당자 : 엄태운
     * 관련기능 : 회원정보 수정
     */
    @PostMapping("/modify")
    public String updateMember(@ModelAttribute @Valid ModifyRequest modifyRequest,
            @RequestParam(value="profileImg", required=false)  MultipartFile profileImg,
            HttpSession session) throws IOException {
        String memberId = session.getAttribute("memberId").toString();
        Member member = mService.getOneMember(memberId);
        member.setMemberPw(modifyRequest.getMemberPw());
        member.setPhone(modifyRequest.getPhone());
        int result = mService.updateMember(member, profileImg);
        return "redirect:/my-page/update";
    }

    /**
     * 담당자 : 엄태운
     * 관련기능 : 회원정보 삭제
     */
    @PostMapping("/remove")
    public String deleteMember(@ModelAttribute @Valid RemoveRequest removeRequest,
           HttpSession session) {
        String memberId = session.getAttribute("memberId").toString();
        removeRequest.setMemberId(memberId);
        Member member = mService.getOneMember(memberId);
        if(memberId.equals(removeRequest.getMemberId()) && member.getMemberPw().equals(removeRequest.getMemberPw())) {
            log.info(member.toString());
            int result = mService.deleteMember(member);
            session.invalidate();
        }
        return "redirect:/";
    }

    /**
     * 담당자 : 엄태운
     * 관련기능 : 생년월일 String을 Timestamp로 변환
     */
    public Timestamp convertStringToTimestamp(String birthDate) {
        // 입력받은 String을 Date로 변환
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        LocalDate localDate = LocalDate.parse(birthDate, formatter);
        // Date를 Timestamp로 변환
        return Timestamp.valueOf(localDate.atStartOfDay());
    }

    /**
     * 담당자 : 엄태운
     * 관련기능 : 생년월일 Timestamp를 String으로 변환
     */
    public String convertTimestampToString(Timestamp birthDate) {
        // Timestamp 타입의 데이터를 Date로 변환
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return birthDate.toLocalDateTime().format(formatter);
    }

    /**
     * 담당자 : 엄태운
     * 관련기능 : 로그인
     */
    @PostMapping("/login")
    @ResponseBody
    public String loginMember(@Valid LoginRequest loginRequest,
          HttpSession session, Model model) {
        Member member = new Member();
        member.setMemberId(loginRequest.getMemberId());
        member.setMemberPw(loginRequest.getMemberPw());
        member = mService.loginMember(member);
        ProfileImg profileImg = mService.getOneProfileImg(member.getMemberId());
        // 세션에 로그인 정보 저장
        session.setAttribute("memberId", member.getMemberId());
        session.setAttribute("name", member.getName());
        // 로그인 시 메인에 회원&프로필사진 정보 전달
        model.addAttribute("member", member);
        model.addAttribute("profileImg", profileImg);
        return "redirect:/";
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
    @PostMapping("/find-id")
    public FindIdResponse findMemberId(@ModelAttribute @Valid FindIdRequest findIdRequest) {
        Member member = mService.findMemberId(findIdRequest.getName(), findIdRequest.getEmail());
        return new FindIdResponse();
    }

    /**
     * 담당자 : 엄태운
     * 관련기능 : 비밀번호 찾기
     */
    @PostMapping("/find-pw")
    public String findMemberPw() {

        return "";
    }

}
