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
    int updateProfileImg(ProfileImg profileImg);
    Member selectOneById(String memberId);
    ProfileImg selectOneProfileImgById(String memberId);
    Member selectOneByNameAndEmail(@Param("name") String name, @Param("email") String email);
    Member selectOneByIdAndEmail(@Param("memberId") String memberId, @Param("email") String email);
    int updatePassword(@Param("memberId")String memberId, @Param("memberPw") String newPassword);
    boolean countByMemberId(String memberId);

}
