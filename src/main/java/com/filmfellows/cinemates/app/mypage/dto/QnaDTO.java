package com.filmfellows.cinemates.app.mypage.dto;

import lombok.*;

import java.sql.Timestamp;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class QnaDTO {
    private Integer qnaNo;
    private String title;
    private String content;
    private Timestamp regDate;
    private Timestamp modDate;
    private String memberId;
    private Integer parentQnaNo;
    private String replyStatus; // 답변 상태
}
