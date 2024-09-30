package com.filmfellows.cinemates.domain.member.model.service;

import com.filmfellows.cinemates.domain.member.model.vo.Member;

public interface MemberService {
    int insertMember(Member member);
    int updateMember(Member member);
    int deleteMember(Member member);
    Member loginMember(Member member);
    Member findMemberId(String name, String email);
    Member findMemberPw(String name, String email);
}