package com.filmfellows.cinemates.domain.member.model.service;

import com.filmfellows.cinemates.domain.member.model.vo.Member;
import com.filmfellows.cinemates.domain.member.model.vo.ProfileImg;
import com.filmfellows.cinemates.domain.naverapi.model.vo.NaverProfile;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface MemberService {
    int insertMember(Member member, MultipartFile uploadFile) throws IllegalStateException, IOException;
    int updateMember(Member member, MultipartFile reloadFile) throws IllegalStateException, IOException;
    int deleteMember(Member member);
    Member loginMember(Member member);
    Member getOneMember(String memberId);
    ProfileImg getOneProfileImg(String memberId);
    Member findMemberId(Member member);
    Member findMemberPw(Member member);
    int updatePassword(Member member);
    boolean isIdDuplicate(String memberId);
    boolean isEmailDuplicate(String email);
    // 소셜 로그인
    String findSnsIdByEmailAndType(String email, String snsType);
    NaverProfile loginSnsMember(String snsId);
    int registerSnsMember(NaverProfile naverProfile);
}