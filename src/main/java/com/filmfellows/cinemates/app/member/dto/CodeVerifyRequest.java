package com.filmfellows.cinemates.app.member.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class CodeVerifyRequest {
    private String email;
    private String verificationCode;
}