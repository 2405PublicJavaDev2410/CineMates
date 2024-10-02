package com.filmfellows.cinemates.app.member.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class RemoveRequest {
    private String memberId;
    private String memberPw;
}
