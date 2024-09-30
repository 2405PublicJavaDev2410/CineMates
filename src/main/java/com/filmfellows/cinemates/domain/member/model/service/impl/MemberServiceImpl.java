package com.filmfellows.cinemates.domain.member.model.service.impl;

import com.filmfellows.cinemates.domain.member.model.mapper.MemberMapper;
import com.filmfellows.cinemates.domain.member.model.service.MemberService;
import com.filmfellows.cinemates.domain.member.model.vo.Member;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {
    private final MemberMapper mMapper;

    @Override
    public int insertMember(Member member) {
        return 0;
    }

    @Override
    public int updateMember(Member member) {
        return 0;
    }

    @Override
    public int deleteMember(Member member) {
        return 0;
    }

    @Override
    public Member loginMember(Member member) {
        return null;
    }

    @Override
    public Member findMemberId(String name, String email) {
        return null;
    }

    @Override
    public Member findMemberPw(String name, String email) {
        return null;
    }
}
