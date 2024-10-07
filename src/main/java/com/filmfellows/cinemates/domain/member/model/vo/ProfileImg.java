package com.filmfellows.cinemates.domain.member.model.vo;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ProfileImg {
    private int profileImgNo;
    private String fileName;
    private String fileRename;
    private String filePath;
    private String memberId;
    private MultipartFile uploadFile;
}
