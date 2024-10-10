package com.filmfellows.cinemates.domain.cinema.model.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Setter
@Getter
public class Cinema {
    private int cinemaNo;
    private int cinemaLocationCode;
    private String cinemaName;
    private String cinemaAddress;
    private String cinemaCall;
    private int cinemaScreens;
    private int cinemaSeat;




}
