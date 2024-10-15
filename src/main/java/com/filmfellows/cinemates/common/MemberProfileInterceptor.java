package com.filmfellows.cinemates.common;

import com.filmfellows.cinemates.domain.member.model.service.MemberService;
import com.filmfellows.cinemates.domain.member.model.vo.MemberProfile;
import com.filmfellows.cinemates.domain.member.model.vo.ProfileImg;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.web.servlet.HandlerInterceptor;

@RequiredArgsConstructor
public class MemberProfileInterceptor implements HandlerInterceptor {
    private final MemberService mService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession();
        MemberProfile memberProfile = (MemberProfile) session.getAttribute("memberProfile");
        String memberId = (String) session.getAttribute("memberId");
        String snsProfileImg = (String) session.getAttribute("profileImg");

        // 일반 로그인 프로필 이미지 처리
        if (memberId != null) {
            ProfileImg profileImg = mService.getOneProfileImg(memberId);
            if (profileImg != null) {
                String fullPath = profileImg.getFilePath() + profileImg.getFileRename();
                if (memberProfile == null) {
                    memberProfile = new MemberProfile();
                }
                memberProfile.setProfileImg(fullPath);
                session.setAttribute("memberProfile", memberProfile);
            } else {
                if (memberProfile == null) {
                    memberProfile = new MemberProfile();
                }
                memberProfile.setProfileImg("/img/chat/defaultProfile.png");
                session.setAttribute("memberProfile", memberProfile);
            }
        }

        // 소셜 로그인 프로필 이미지 처리
        if (snsProfileImg != null) {
            if (memberProfile == null) {
                memberProfile = new MemberProfile();
            }
            memberProfile.setProfileImg(snsProfileImg);
            session.setAttribute("memberProfile", memberProfile);
        }

        if (memberProfile != null) {
            request.setAttribute("profileImg", memberProfile.getProfileImg());
        }

        return true;
    }
}
