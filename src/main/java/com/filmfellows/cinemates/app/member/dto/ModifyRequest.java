package com.filmfellows.cinemates.app.member.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ModifyRequest {
    private String memberPw;
    private String checkPassword;
    private String phone;
}
