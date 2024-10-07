package com.filmfellows.cinemates.domain.member.model.vo;

import lombok.*;

import java.sql.Timestamp;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Member {
    private String memberId;
    private String memberPw;
    private String name;
    private Timestamp birthDate;
    private String phone;
    private String email;
    private String status;
    private String deleteYn;
    private String role;
    private Timestamp regDate;
}
