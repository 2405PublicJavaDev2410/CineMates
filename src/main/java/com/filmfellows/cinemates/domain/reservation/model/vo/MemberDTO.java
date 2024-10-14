package com.filmfellows.cinemates.domain.reservation.model.vo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class MemberDTO {

    private String memberId;
    private String memberPw;
    private String name;
    private String phone;
    private String email;
}
