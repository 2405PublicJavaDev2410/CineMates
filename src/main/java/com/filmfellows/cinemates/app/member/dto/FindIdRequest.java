package com.filmfellows.cinemates.app.member.dto;

import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import static com.filmfellows.cinemates.common.Validation.EMAIL_VALIDATION;
import static com.filmfellows.cinemates.common.Validation.NAME_VALIDATION;

@Getter
@Setter
@ToString
public class FindIdRequest {
    @Pattern(regexp = NAME_VALIDATION)
    private String name;
    @Pattern(regexp = EMAIL_VALIDATION)
    private String email;
}
