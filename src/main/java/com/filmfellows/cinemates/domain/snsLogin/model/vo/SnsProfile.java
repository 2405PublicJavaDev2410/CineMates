package com.filmfellows.cinemates.domain.snsLogin.model.vo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.sql.Timestamp;

@Setter
@Getter
@NoArgsConstructor
@ToString
public class SnsProfile {
    private String snsId;
    private String memberId;
    private String snsType;
    private String name;
    private String profileImg;
    private String email;
    private String birthDate;
    private String mobile;
    private Timestamp connectDate;
}