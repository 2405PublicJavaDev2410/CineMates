package com.filmfellows.cinemates.app.mypage.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class RegisterQnaRequest {
    private String title;
    private String content;
}
