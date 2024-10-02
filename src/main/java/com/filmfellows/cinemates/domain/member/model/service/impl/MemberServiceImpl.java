package com.filmfellows.cinemates.domain.member.model.service.impl;

import com.filmfellows.cinemates.common.utility.Util;
import com.filmfellows.cinemates.domain.member.model.mapper.MemberMapper;
import com.filmfellows.cinemates.domain.member.model.service.MemberService;
import com.filmfellows.cinemates.domain.member.model.vo.Member;
import com.filmfellows.cinemates.domain.member.model.vo.ProfileImg;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
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
            String filePath = "/cimenates/member/";
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
    public int updateMember(Member member) {
        return 0;
    }

    @Override
    public int deleteMember(Member member) {
        return 0;
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
    public Member findMemberId(String name, String email) {
        return null;
    }

    @Override
    public Member findMemberPw(String name, String email) {
        return null;
    }
}
