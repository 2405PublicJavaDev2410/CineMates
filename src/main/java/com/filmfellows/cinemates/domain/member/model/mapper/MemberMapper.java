package com.filmfellows.cinemates.domain.member.model.mapper;

import com.filmfellows.cinemates.domain.member.model.vo.Member;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MemberMapper {
    int insertMember(Member member);
    int updateMember(Member member);
    int deleteMember(Member member);
    Member selectOneById(String memberId);
    Member selectOneByName(String memberName);
}
