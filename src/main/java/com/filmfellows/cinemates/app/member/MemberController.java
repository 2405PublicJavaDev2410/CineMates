package com.filmfellows.cinemates.app.member;

import com.filmfellows.cinemates.app.member.dto.*;
import com.filmfellows.cinemates.domain.emailverification.model.service.EmailVerificationService;
import com.filmfellows.cinemates.domain.member.model.vo.MemberProfile;
import com.filmfellows.cinemates.domain.report.model.vo.Report;
import com.filmfellows.cinemates.domain.snsLogin.model.service.KakaoApiService;
import com.filmfellows.cinemates.domain.snsLogin.model.vo.KakaoApi;
import com.filmfellows.cinemates.domain.member.model.service.MemberService;
import com.filmfellows.cinemates.domain.member.model.vo.Member;
import com.filmfellows.cinemates.domain.member.model.vo.ProfileImg;
import com.filmfellows.cinemates.domain.snsLogin.model.service.NaverApiService;
import com.filmfellows.cinemates.domain.snsLogin.model.vo.NaverApi;
import com.filmfellows.cinemates.domain.snsLogin.model.vo.SnsProfile;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Controller
@Slf4j
@RequiredArgsConstructor
public class MemberController {
    private final MemberService mService;
    private final EmailVerificationService evService;
    private final NaverApi naverApi;
    private final NaverApiService naverApiService;
    private final KakaoApi kakaoApi;
    private final KakaoApiService kakaoApiService;

    /**
     * 담당자 : 엄태운
     * 관련기능 : 로그인 페이지 이동
     */
    @GetMapping("/login")
    public String showLogin(Model model, HttpSession session) {
        if (session != null) {
            session.invalidate();
        }
        String state = UUID.randomUUID().toString();
        String naverLoginUrl = String.format("https://nid.naver.com/oauth2.0/authorize?client_id=%s&redirect_uri=%s&response_type=code&state=%s",
                naverApi.getNaverClientId(), naverApi.getNaverRedirectUri(), state);
        String kakaoLoginUrl = String.format("https://kauth.kakao.com/oauth/authorize?client_id=%s&redirect_uri=%s&response_type=code&state=%s",
                kakaoApi.getKakaoApiKey(), kakaoApi.getKakaoRedirectUri(), state);
        model.addAttribute("naverLoginUrl", naverLoginUrl);
        model.addAttribute("kakaoLoginUrl", kakaoLoginUrl);
        return "pages/member/login";
    }

    /**
     * 담당자 : 엄태운
     * 관련기능 : 네이버 로그인 후 인증 처리
     */
    @GetMapping("/naver/callback")
    public String naverLoginCallback(@RequestParam("code") String code,
                                     @RequestParam("state") String state,
                                     HttpSession session, Model model) {
        // 1. 액세스 토큰 발급 요청
        String accessToken = naverApiService.getAccessToken(code, state);
        System.out.println("accessToken : " + accessToken);
        // 2. 액세스 토큰을 이용해 사용자 정보 조회
        SnsProfile snsProfile = naverApiService.getUserInfo(accessToken);
        String snsId = mService.findSnsIdByEmailAndType(snsProfile.getEmail(), snsProfile.getSnsType());
        // 사용자 정보로 로그인 또는 회원가입 처리
        System.out.println("snsId : " + snsId);
        MemberProfile memberProfile = new MemberProfile();
        if (snsId != null) {
            SnsProfile newMember = mService.loginSnsMember(snsId);
            memberProfile.setProfileImg(newMember.getProfileImg());
            session.setAttribute("memberProfile", memberProfile);
            log.info(newMember.toString());
            // 세션에 sns 로그인 정보 저장
            session.setAttribute("memberId", newMember.getSnsId());
            session.setAttribute("name", newMember.getName());
            session.setAttribute("snsType", newMember.getSnsType());
            session.setAttribute("token", accessToken);
            model.addAttribute("member", newMember);
        } else {
            // 기존 MEMBER_TBL의 MEMBER_ID에 SNS_ID 저장
            String formattedSnsId
                = "N_" + snsProfile.getSnsId().substring(snsProfile.getSnsId().length() - 8);
            snsProfile.setSnsId(formattedSnsId);
            int result = mService.insertSnsIdToMember(snsProfile.getSnsId());
            if(result == 1) {
                // SNS_INFO_TBL에 정보 저장
                snsProfile.setMemberId(snsProfile.getSnsId());
                mService.insertSnsMember(snsProfile);
            }
            memberProfile.setProfileImg(snsProfile.getProfileImg());
            session.setAttribute("memberProfile", memberProfile);
            session.setAttribute("memberId", snsProfile.getSnsId());
            session.setAttribute("name", snsProfile.getName());
            session.setAttribute("snsType", snsProfile.getSnsType());
            session.setAttribute("token", accessToken);
            model.addAttribute("member", snsProfile);
        }
        // 3. 로그인 후 홈으로 리다이렉트
        return "redirect:/";
    }

    /**
     * 담당자 : 엄태운
     * 관련기능 : 카카오 로그인 후 인증 처리
     */
    @GetMapping("/kakao/callback")
    public String kakaoLoginCallback(@RequestParam("code") String code,
                                     HttpSession session, Model model) {
        String accessToken = kakaoApiService.getAccessToken(code);
        System.out.println("accessToken : " + accessToken);

        SnsProfile snsProfile = kakaoApiService.getUserInfo(accessToken);
        System.out.println("카카오 프로필 " + snsProfile);
        String snsId = mService.findSnsIdByEmailAndType(snsProfile.getEmail(), snsProfile.getSnsType());
        // 사용자 정보로 로그인 또는 회원가입 처리
        System.out.println("snsId : " + snsId);
        MemberProfile memberProfile = new MemberProfile();
        if (snsId != null) {
            SnsProfile newMember = mService.loginSnsMember(snsId);
            log.info(newMember.toString());
            memberProfile.setProfileImg(newMember.getProfileImg());
            session.setAttribute("memberProfile", memberProfile);
            session.setAttribute("memberId", newMember.getSnsId());
            session.setAttribute("name", newMember.getName());
            session.setAttribute("snsType", newMember.getSnsType());
            session.setAttribute("token", accessToken);
            model.addAttribute("member", newMember);
        } else {
            // 기존 MEMBER_TBL의 MEMBER_ID에 SNS_ID 저장
            String formattedSnsId
                = "K_" + snsProfile.getSnsId().substring(snsProfile.getSnsId().length() - 8);
            snsProfile.setSnsId(formattedSnsId);
            int result = mService.insertSnsIdToMember(snsProfile.getSnsId());
            if(result == 1) {
                // SNS_IFO_TBL에 정보 저장
                snsProfile.setMemberId(snsProfile.getSnsId());
                mService.insertSnsMember(snsProfile);
            }
            memberProfile.setProfileImg(snsProfile.getProfileImg());
            session.setAttribute("memberProfile", memberProfile);
            session.setAttribute("memberId", snsProfile.getSnsId());
            session.setAttribute("name", snsProfile.getName());
            session.setAttribute("snsType", snsProfile.getSnsType());
            session.setAttribute("token", accessToken);
            model.addAttribute("member", snsProfile);
        }
        // 3. 로그인 후 홈으로 리다이렉트
        return "redirect:/";
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
        return "pages/member/findInfo";
    }

    /**
     * 담당자 : 엄태운
     * 관련기능 : 회원정보수정 페이지 이동
     */
    @GetMapping("/my-page/update")
    public String showModifyMember(HttpSession session, Model model) {
        String memberId = (String) session.getAttribute("memberId");
        String snsType = (String) session.getAttribute("snsType");
        if (memberId == null) {
            return "redirect:/login";
        }
        if(snsType == null) {
            Member member = mService.getOneMember(memberId);
            ProfileImg profileImg = mService.getOneProfileImg(memberId);
            String birthDate = member.getBirthDate() != null ? convertTimestampToString(member.getBirthDate()) : "";
            model.addAttribute("member", member);
            model.addAttribute("birthDate", birthDate);
            if(profileImg != null) {
                model.addAttribute("profileImg", profileImg);
            }
        }else {
            model.addAttribute("snsType", snsType);
        }
        return "pages/mypage/update";
    }

    /**
     * 담당자 : 엄태운
     * 관련기능 : 회원탈퇴 페이지 이동
     */
    @GetMapping("/my-page/remove")
    public String showRemoveMember(HttpSession session, Model model) {
        String memberId = (String) session.getAttribute("memberId");
        String snsType = (String) session.getAttribute("snsType");
        if (memberId == null) {
            return "redirect:/login";
        }
        if(snsType == null) {
            Member member = mService.getOneMember(memberId);
            model.addAttribute("member", member);
        }else {
            model.addAttribute("snsType", snsType);
        }
        return "pages/mypage/remove";
    }

    /**
     * 담당자 : 엄태운
     * 관련기능 : 비밀번호 재설정 페이지 이동
     */
    @GetMapping("/reset-pw")
    public String showResetPassword(@RequestParam("token") String token, Model model) {
        // 유효하지 않은 토큰이면 로그인 페이지로 리다이렉트
        if(!evService.isValidToken(token)) {
            return "redirect:/login";
        }
        model.addAttribute("token", token);
        log.info("token : {}", token);
        return "pages/member/resetPassword";
    }

    /**
     * 담당자 : 엄태운
     * 관련기능 : 회원가입
     */
    @PostMapping("/register")
    @ResponseBody
    public String insertMember(@ModelAttribute @Valid RegisterRequest registerRequest,
            @RequestParam(value="profileImg", required=false) MultipartFile profileImg,
                               @RequestParam("verificationCode") String verCode)
            throws IllegalStateException, IOException {
        // 이메일 인증번호 검증
        boolean isVerified = evService.verifyCode(registerRequest.getEmail(), verCode);
        if(!isVerified) {
            return "verification_fail";
        }

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
        if(result == 1) {
            return "success";
        }else {
            return "fail";
        }
    }

    /**
     * 담당자 : 엄태운
     * 관련기능 : 아이디 중복 체크
     */
    @GetMapping("/checkId")
    @ResponseBody
    public String checkDuplicatedId(@RequestParam("memberId") String memberId) {
        boolean result = mService.isIdDuplicate(memberId);
        log.info("isDuplicated : {}", result);
        return result ? "duplicated" : "available";
    }

    /**
     * 담당자 : 엄태운
     * 관련기능 : 이메일 중복 체크
     */
    @GetMapping("/checkEmail")
    @ResponseBody
    public String checkDuplicatedEmail(@RequestParam("email") String email) {
        boolean result = mService.isEmailDuplicate(email);
        log.info("isDuplicated : {}", result);
        return result ? "duplicated" : "available";
    }

    /**
     * 담당자 : 엄태운
     * 관련기능 : 회원정보 수정
     */
    @PostMapping("/modify")
    @ResponseBody
    public String updateMember(@ModelAttribute @Valid ModifyRequest modifyRequest,
            @RequestParam(value="profileImg", required=false)  MultipartFile profileImg,
            HttpSession session) throws IllegalStateException, IOException {
        String memberId = session.getAttribute("memberId").toString();
        Member member = mService.getOneMember(memberId);
        member.setMemberPw(modifyRequest.getMemberPw());
        member.setPhone(modifyRequest.getPhone());
        int result = mService.updateMember(member, (profileImg != null && !profileImg.isEmpty() ? profileImg : null));

        if(result == 1) {
            // 프로필 이미지 업데이트
            if (profileImg != null && !profileImg.isEmpty()) {
                ProfileImg newProfileImg = mService.getOneProfileImg(memberId);
                if (newProfileImg != null) {
                    String fullPath = newProfileImg.getFilePath() + newProfileImg.getFileRename();
                    MemberProfile memberProfile = new MemberProfile();
                    memberProfile.setProfileImg(fullPath);
                    session.setAttribute("memberProfile", memberProfile);
                }
            }
            return "success";
        } else {
            return "fail";
        }
    }

    /**
     * 담당자 : 엄태운
     * 관련기능 : 회원탈퇴
     */
    @PostMapping("/remove")
    @ResponseBody
    public String deleteMember(@RequestBody @Valid RemoveRequest removeRequest,
                                                 HttpSession session) {
        String memberId = session.getAttribute("memberId").toString();
        Member member = mService.getOneMember(memberId);
        if(member.getMemberPw().equals(removeRequest.getMemberPw())) {
            mService.deleteMember(member);
            session.invalidate();
            return "success";
        }else {
            return "fail";
        }
    }

    /**
     * 담당자 : 엄태운
     * 관련기능 : 소셜계정 회원탈퇴
     */
    @PostMapping("/sns/remove")
    @ResponseBody
    public String deleteSnsMember(HttpSession session) {
        String accessToken = (String) session.getAttribute("token");
        String memberId = (String) session.getAttribute("memberId");
        String snsType = (String) session.getAttribute("snsType");
        log.info("엑세스 토큰 : " + accessToken);
        if (accessToken != null) {
            String result = null;
            if("NAVER".equals(snsType)) {
                // 네이버 연동 해제
                result = naverApiService.revokeNaverAccessToken(accessToken);
            }else if ("KAKAO".equals(snsType)) {
                // 카카오 연동 해제
                result = kakaoApiService.revokeKakaoAccessToken(accessToken);
            }
            if(result != null) {
                mService.deleteSnsMember(memberId);
            }
            session.invalidate();
            return "success";
        }
       return "fail";
    }

    /**
     * 담당자 : 엄태운
     * 관련기능 : 로그인
     */
    @PostMapping("/login")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> loginMember(@RequestBody @Valid LoginRequest loginRequest,
                                                           HttpSession session) {
        Member member = new Member();
        member.setMemberId(loginRequest.getMemberId());
        member.setMemberPw(loginRequest.getMemberPw());
        member = mService.loginMember(member);
//        System.out.println(member.toString());
        Map<String, Object> response = new HashMap<>();

        if (member != null) {
            // 신고 상태 조회
            Report report = mService.searchOneReportById(member.getMemberId());
            if(member.getReportCount() >= 3
                    && member.getBanPeriod() != null
                    && report.getReportStatus().equals("완료")) {
                response.put("status", "ban");
                response.put("member", member);
                response.put("report", report);
                // 신고 정보 포함하여 리턴
                return ResponseEntity.ok(response);
            }
            session.setAttribute("memberId", member.getMemberId());
            session.setAttribute("name", member.getName());

            // 프로필 이미지 처리
            ProfileImg profileImg = mService.getOneProfileImg(member.getMemberId());
            MemberProfile memberProfile = new MemberProfile();
            if (profileImg != null) {
                String fullPath = profileImg.getFilePath() + profileImg.getFileRename();
                memberProfile.setProfileImg(fullPath);
            } else {
                memberProfile.setProfileImg("/img/default_profile.png");
            }
            session.setAttribute("memberProfile", memberProfile);

            response.put("status", "success");
            response.put("member", member);
            return ResponseEntity.ok(response);
        }
        response.put("status", "fail");
        return ResponseEntity.ok(response);
    }

    /**
     * 담당자 : 엄태운
     * 관련기능 : 로그아웃
     */
    @GetMapping("/logout")
    public String logoutMember(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }

    /**
     * 담당자 : 엄태운
     * 관련기능 : 아이디 찾기
     */
    @PostMapping("/find-id")
    @ResponseBody
    public FindIdResponse findMemberId(@RequestBody @Valid FindIdRequest findIdRequest) {
        log.info(findIdRequest.toString());
        Member member = new Member();
        member.setName(findIdRequest.getName());
        member.setEmail(findIdRequest.getEmail());
        member = mService.findMemberId(member);
        if (member != null) {
            return new FindIdResponse(member.getName(), member.getMemberId());
        }
        return new FindIdResponse("조회 결과 없음", null);
    }

    /**
     * 담당자 : 엄태운
     * 관련기능 : 이메일 인증번호 전송
     */
    @PostMapping("/send-verification-code")
    @ResponseBody
    public Map<String, Object> sendVerificationCode(@RequestBody EmailVerifyRequest emailVerifyRequest) {
        String email = emailVerifyRequest.getEmail();
        Map<String, Object> response = new HashMap<>();

        if (email == null || email.isEmpty()) {
            response.put("success", false);
            response.put("message", "이메일이 유효하지 않습니다.");
            return response;
        }

        boolean isSent = evService.sendVerificationCode(email);
        log.info("isSent : {}", isSent);
        if (isSent) {
            response.put("success", true);
            response.put("message", "인증번호가 발송되었습니다. 이메일을 확인해주세요.");
        } else {
            response.put("success", false);
            response.put("message", "인증번호 발송에 실패하였습니다. 다시 시도해주세요.");
        }
        return response;
    }

    /**
     * 담당자 : 엄태운
     * 관련기능 : 인증번호 인증
     */
    @PostMapping("/verify-code")
    @ResponseBody
    public Map<String, Object> verifyCode(@RequestBody CodeVerifyRequest codeVerifyRequest) {
        Map<String, Object> response = new HashMap<>();
        String email = codeVerifyRequest.getEmail();
        String code = codeVerifyRequest.getVerificationCode();

        // 서비스에서 저장된 인증번호를 가져와서 입력값과 비교
        String storedCode = evService.getStoredCode(email);
        if (storedCode != null && storedCode.equals(code)) {
            response.put("success", true);
        } else {
            response.put("success", false);
        }
        return response;
    }

    /**
     * 담당자 : 엄태운
     * 관련기능 : 비밀번호 재설정 링크 전송
     */
    @PostMapping("/send-reset-link")
    @ResponseBody
    public String sendResetLink(@RequestBody @Valid FindPwRequest findPwRequest) {
        log.info("Reset password request for email: {}", findPwRequest.getEmail());
        Member member = new Member();
        member.setMemberId(findPwRequest.getMemberId());
        member.setEmail(findPwRequest.getEmail());
        Member oneMember = mService.findMemberPw(member);
        if(oneMember == null) {
            return "fail";
        }
        evService.sendPasswordResetEmail(oneMember.getEmail(), oneMember.getMemberId());
        return "success";
    }

    /**
     * 담당자 : 엄태운
     * 관련기능 : 비밀번호 재설정
     */
    @PostMapping("/reset-pw")
    @ResponseBody
    public String findMemberPw(@RequestBody @Valid RegisterNewPwRequest newPwRequest) {
        log.info("Received new password request: {}", newPwRequest);
        // 유효한 토큰인지 검증
        String memberId = evService.verifyToken(newPwRequest.getToken());
        if (memberId == null) {
            return "invalid_token";
        }
        Member member = new Member();
        member.setMemberId(memberId);
        member.setMemberPw(newPwRequest.getMemberPw());
        // 비밀번호 업데이트
        int result = mService.updatePassword(member);
        if(result == 0) {
            return "fail";
        }
            return "success";
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

}
