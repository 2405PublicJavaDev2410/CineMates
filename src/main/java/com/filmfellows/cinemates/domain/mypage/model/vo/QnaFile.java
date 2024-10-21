package com.filmfellows.cinemates.domain.mypage.model.vo;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class QnaFile {
    private int qnaFileNo;
    private String fileName;
    private String fileRename;
    private String filePath;
    private int qnaNo;
}
