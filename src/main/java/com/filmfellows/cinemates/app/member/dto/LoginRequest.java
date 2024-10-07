package com.filmfellows.cinemates.app.member.dto;

import jakarta.validation.constraints.Pattern;
import lombok.*;

import static com.filmfellows.cinemates.common.Validation.ID_VALIDATION;
import static com.filmfellows.cinemates.common.Validation.PW_VALIDATION;

@Getter
@Setter
@ToString
public class LoginRequest {
    @Pattern(regexp = ID_VALIDATION)
    private String memberId;
    @Pattern(regexp = PW_VALIDATION)
    private String memberPw;
}
