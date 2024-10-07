package com.filmfellows.cinemates.app.member.dto;

import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import static com.filmfellows.cinemates.common.Validation.PW_VALIDATION;

@Getter
@Setter
@ToString
public class RegisterNewPwRequest {
    private String token;
    @Pattern(regexp = PW_VALIDATION)
    private String memberPw;
    @Pattern(regexp = PW_VALIDATION)
    private String checkPassword;
}
