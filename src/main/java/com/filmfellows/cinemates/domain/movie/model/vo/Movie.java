package com.filmfellows.cinemates.domain.movie.model.vo;


import lombok.*;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Movie {
    private Long movieNo;
    private String title;
    private String posterUrl;
    private LocalDate releaseDate;
    private Integer runningTime;
    private String rating;
    private String synopsis;
    private String director;
    private String actors;
    private String genre;
    private String productionCountry;
    private String screeningStatus;

//    private String formattedReleaseDate;

}
