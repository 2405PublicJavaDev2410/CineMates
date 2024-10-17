package com.filmfellows.cinemates.domain.chat.model.vo;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ChatJoin {
    private Integer roomNo;
    private String memberId;
}
