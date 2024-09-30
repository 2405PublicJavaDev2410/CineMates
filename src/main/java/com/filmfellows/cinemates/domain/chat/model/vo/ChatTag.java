package com.filmfellows.cinemates.domain.chat.model.vo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@Getter
@Setter
@ToString
public class ChatTag {
    private Integer tagNo;
    private String tagName;
    private Integer roomNo;
}
