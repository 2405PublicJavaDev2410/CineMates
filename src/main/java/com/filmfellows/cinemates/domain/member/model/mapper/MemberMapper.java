package com.filmfellows.cinemates.domain.member.model.mapper;

import com.filmfellows.cinemates.domain.member.model.vo.Member;
import com.filmfellows.cinemates.domain.member.model.vo.ProfileImg;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface MemberMapper {
    int insertMember(Member member);
    int updateMember(Member member);
    int deleteMember(Member member);
    int insertProfileImg(ProfileImg profileImg);
    Member selectOneById(String memberId);
    ProfileImg selectOneProfileImgById(String memberId);
    Member selectOneByName(String memberName);
}
