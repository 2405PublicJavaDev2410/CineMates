package com.filmfellows.cinemates.app.member.dto;

import lombok.*;

@Getter
@Setter
@ToString
public class LoginRequest {
    private String memberId;
    private String memberPw;
}
