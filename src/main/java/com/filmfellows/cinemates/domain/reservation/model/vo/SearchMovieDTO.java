package com.filmfellows.cinemates.domain.reservation.model.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString

public class SearchMovieDTO {
    private String title;
    private Integer movieNo;
    private String isBookAble;
}
