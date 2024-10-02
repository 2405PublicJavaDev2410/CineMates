package com.filmfellows.cinemates.app.movie.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class MovieTrailerResponse {
    private Long trailerNo;
    private String trailerType;
    private String trailerThumbnailUrl;
    private String trailerUrl;
}
