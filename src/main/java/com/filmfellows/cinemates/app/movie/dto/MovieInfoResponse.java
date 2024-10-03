package com.filmfellows.cinemates.app.movie.dto;

import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class MovieInfoResponse {
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
    private List<MovieStillcut> stillcuts = new ArrayList<>();
    private List<MovieTrailer> trailers = new ArrayList<>();

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @ToString
    public static class MovieStillcut {
        private Long stillcutNo;
        private String stillcutUrl;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @ToString
    public static class MovieTrailer {
        private Long trailerNo;
        private String trailerType;
        private String trailerThumbnailUrl;
        private String trailerUrl;
    }
}

