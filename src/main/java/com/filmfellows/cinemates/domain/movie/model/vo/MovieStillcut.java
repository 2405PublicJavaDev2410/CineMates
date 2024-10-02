package com.filmfellows.cinemates.domain.movie.model.vo;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class MovieStillcut {
    private Long stillcutNo;
    private int movieNo;
    private String stillcutUrl;
}
