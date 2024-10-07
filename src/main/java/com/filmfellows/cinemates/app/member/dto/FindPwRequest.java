package com.filmfellows.cinemates.app.member.dto;

import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import static com.filmfellows.cinemates.common.Validation.EMAIL_VALIDATION;
import static com.filmfellows.cinemates.common.Validation.ID_VALIDATION;

@Getter
@Setter
@ToString
public class FindPwRequest {
    @Pattern(regexp = ID_VALIDATION)
    private String memberId;
    @Pattern(regexp = EMAIL_VALIDATION)
    private String email;
}
