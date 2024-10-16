package com.filmfellows.cinemates.app.movie.dto;

import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class MovieDTO {
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
    private String isBookable;
    private List<MovieDTO.TrailerDTO> trailers;
    private List<MovieDTO.StillcutDTO> stillcuts;

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @ToString
    public static class TrailerDTO {
        private Long trailerNo;
        private String trailerType;
        private String trailerThumbnailUrl;
        private String trailerUrl;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @ToString
    public static class StillcutDTO {
        private Long stillcutNo;
        private String stillcutUrl;
    }

}
