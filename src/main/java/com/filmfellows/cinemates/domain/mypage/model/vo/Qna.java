package com.filmfellows.cinemates.domain.mypage.model.vo;

import lombok.*;

import java.sql.Timestamp;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Qna {
    private Integer qnaNo;
    private String title;
    private String content;
    private Timestamp regDate;
    private Timestamp modDate;
    private String memberId;
    private int parentQnaNo;
}
