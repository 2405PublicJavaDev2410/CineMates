package com.filmfellows.cinemates.domain.member.model.service.impl;

import com.filmfellows.cinemates.common.utility.Util;
import com.filmfellows.cinemates.domain.member.model.mapper.MemberMapper;
import com.filmfellows.cinemates.domain.member.model.service.MemberService;
import com.filmfellows.cinemates.domain.member.model.vo.Member;
import com.filmfellows.cinemates.domain.member.model.vo.ProfileImg;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {
    private final MemberMapper mMapper;

    @Override
    public int insertMember(Member member, MultipartFile uploadFile) throws IllegalStateException, IOException {
        int result = mMapper.insertMember(member);
        if(uploadFile != null) {
            String fileName = uploadFile.getOriginalFilename();
            String fileRename = Util.fileRename(fileName);
            String filePath = "/cinemates/member/";
            uploadFile.transferTo(new File("C:/uploadFile/member/" + fileRename));
            ProfileImg profileImg = new ProfileImg();
            profileImg.setFileName(fileName);
            profileImg.setFileRename(fileRename);
            profileImg.setFilePath(filePath);
            profileImg.setMemberId(member.getMemberId());
            result = mMapper.insertProfileImg(profileImg);
        }
        return result;
    }

    @Override
    public int updateMember(Member member, MultipartFile reloadFile) throws IllegalStateException, IOException {
        int result = mMapper.updateMember(member);
        if(reloadFile != null && !reloadFile.isEmpty()) {
            String fileName = reloadFile.getOriginalFilename();
            String fileRename = Util.fileRename(fileName);
            String filePath = "/cinemates/member/";
            ProfileImg profileImg = new ProfileImg();
            reloadFile.transferTo(new File("C:/uploadFile/member/" + fileRename));
            // profileImg 객체에 새로운 정보 저장
            profileImg.setFileName(fileName);
            profileImg.setFileRename(fileRename);
            profileImg.setFilePath(filePath);
            profileImg.setMemberId(member.getMemberId());
            // originProfileImg 객체에 기존 정보 저장
            ProfileImg originProfileImg = mMapper.selectOneProfileImgById(member.getMemberId());
            if(originProfileImg != null) {
                // 로컬저장소 내 기존 파일 삭제
                File mFile = new File("C:/uploadFile/member/" + originProfileImg.getFileRename());
                mFile.delete();
                // 기존 데이터가 있으면 업데이트
                result = mMapper.updateProfileImg(profileImg);
            }else {
                // 기존 데이터가 없으면 새로 저장
                result = mMapper.insertProfileImg(profileImg);
            }
        }
        return result;
    }

    @Override
    public int deleteMember(Member member) {
        return mMapper.deleteMember(member);
    }

    @Override
    public Member loginMember(Member member) {
        Member result = mMapper.selectOneById(member.getMemberId());
        if(result != null && member.getMemberPw() != null
                && result.getMemberPw().equals(member.getMemberPw())
                && !result.getDeleteYn().equals("Y")) {
            return result;
        }
        return null;
    }

    @Override
    public Member getOneMember(String memberId) {
        return mMapper.selectOneById(memberId);
    }

    @Override
    public ProfileImg getOneProfileImg(String memberId) {
        return mMapper.selectOneProfileImgById(memberId);
    }

    @Override
    public Member findMemberId(Member member) {
        return mMapper.selectOneByNameAndEmail(member.getName(), member.getEmail());
    }

    @Override
    public Member findMemberPw(String memberId, String email) {
        return null;
    }

    @Override
    public boolean isIdDuplicate(String memberId) {
        return mMapper.countByMemberId(memberId);
    }

}
