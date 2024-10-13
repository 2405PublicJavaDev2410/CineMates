package com.filmfellows.cinemates.domain.member.model.mapper;

import com.filmfellows.cinemates.domain.kakaologin.model.vo.KakaoProfile;
import com.filmfellows.cinemates.domain.member.model.vo.Member;
import com.filmfellows.cinemates.domain.member.model.vo.ProfileImg;
import com.filmfellows.cinemates.domain.naverlogin.model.vo.NaverProfile;
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
    boolean countByEmail(String email);
    // 네이버 로그인
    String selectSnsIdByEmailAndType(@Param("email") String email, @Param("snsType") String snsType);
    NaverProfile selectOneNaverById(String snsId);
    KakaoProfile selectOneKakaoById(String snsId);
    int insertSnsIdToMember(String snsId);
    int insertNaverMember(NaverProfile naverProfile);
    int insertKakaoMember(KakaoProfile kakaoProfile);
    int deleteSnsMember(String snsId);


}
