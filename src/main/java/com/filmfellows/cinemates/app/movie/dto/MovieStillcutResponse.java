package com.filmfellows.cinemates.app.movie.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class MovieStillcutResponse {
    private Long stillcutNo;
    private String stillcutUrl;
}
