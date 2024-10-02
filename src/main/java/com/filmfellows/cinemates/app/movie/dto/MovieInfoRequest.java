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
public class MovieInfoRequest {
    private Long movieNo;
    private String title;
    private String posterUrl;
    private LocalDate releaseDate;
    private int runningTime;
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
        private int stillcutNo;
        private String stillcutUrl;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @ToString
    public static class MovieTrailer {
        private int trailerNo;
        private String trailerType;
        private String trailerThumbnailUrl;
        private String trailerUrl;
    }
}
