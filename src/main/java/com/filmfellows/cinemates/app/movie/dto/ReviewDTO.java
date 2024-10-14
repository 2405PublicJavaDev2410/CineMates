package com.filmfellows.cinemates.app.movie.dto;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ReviewDTO {
    private Long ReviewNo;
    private Long movieNo;
    private String MemberId;
    private Integer score;
    private String reviewContent;
    private LocalDate regDate;
    private LocalDate updateDate;
}
