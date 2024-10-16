package com.filmfellows.cinemates.domain.movie.model.vo;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Review {
    private Long ReviewNo;
    private Long movieNo;
    private String memberId;
    private Integer score;
    private String reviewContent;
    private LocalDate regDate;
    private LocalDate updateDate;
}
