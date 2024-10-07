package com.filmfellows.cinemates.app.member.dto;

import jakarta.validation.constraints.Pattern;
import lombok.*;

import static com.filmfellows.cinemates.common.Validation.*;

@Getter
@Setter
@ToString
public class RegisterRequest {
    @Pattern(regexp = ID_VALIDATION)
    private String memberId;
    @Pattern(regexp = PW_VALIDATION)
    private String memberPw;
    @Pattern(regexp = PW_VALIDATION)
    private String checkPassword;
    @Pattern(regexp = NAME_VALIDATION)
    private String name;
    @Pattern(regexp = BIRTH_DATE_VALIDATION)
    private String birthDate;
    @Pattern(regexp = EMAIL_VALIDATION)
    private String email;
    @Pattern(regexp = PHONE_VALIDATION)
    private String phone;
}
