package com.filmfellows.cinemates.domain.emailverification.model.vo;

import lombok.*;

import java.sql.Timestamp;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class PwResetToken {
    private int tokenNo;
    private String token;
    private String memberId;
    private Timestamp createTime;
    private Timestamp expireTime;
}
