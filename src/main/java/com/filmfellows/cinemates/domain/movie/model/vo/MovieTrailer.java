package com.filmfellows.cinemates.domain.movie.model.vo;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class MovieTrailer {
    private Long trailerNo;
    private int movieNo;
    private String trailerType;
    private String trailerThumbnailUrl;
    private String trailerUrl;
}
