package com.filmfellows.cinemates.app.movie.dto;

import lombok.*;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class MovieListResponse {
    private int movieNo;
    private String title;
    private LocalDate releaseDate;
    private String screeningStatus;
}
