package com.filmfellows.cinemates.app.member.dto;

import lombok.*;

import java.sql.Timestamp;
import java.time.LocalDate;

@Getter
@Setter
@ToString
public class RegisterRequest {
    private String memberId;
    private String memberPw;
    private String checkPassword;
    private String name;
    private String birthDate;
    private String email;
    private String auth;
    private String phone;
}
