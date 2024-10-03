package com.filmfellows.cinemates.app.movie.dto;

import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UpdateMovieDTO {
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
    private List<UpdateStillcutDTO> stillcuts;
    private List<UpdateTrailerDTO> trailers;
    private List<UpdateStillcutDTO> newStillcuts;
    private List<UpdateTrailerDTO> newTrailers;

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @ToString
    public static class UpdateStillcutDTO {
        private Long stillcutNo;
        private String stillcutUrl;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @ToString
    public static class UpdateTrailerDTO {
        private Long trailerNo;
        private String trailerType;
        private String trailerThumbnailUrl;
        private String trailerUrl;
    }
}
