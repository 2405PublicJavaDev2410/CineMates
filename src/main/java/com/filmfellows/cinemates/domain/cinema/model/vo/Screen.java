package com.filmfellows.cinemates.domain.cinema.model.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Setter
@Getter
public class Screen {

    private int screenNo;
    private int cinemaNo;
    private String screenName;
    private int screenSeat;
}
