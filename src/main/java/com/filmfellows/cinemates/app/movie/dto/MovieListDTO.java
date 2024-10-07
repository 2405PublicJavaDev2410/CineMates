package com.filmfellows.cinemates.app.movie.dto;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class MovieListDTO {
    private Long movieNo;
    private String title;
    private String posterUrl;
    private LocalDate releaseDate;
    private String rating;
    private String screeningStatus;
}
