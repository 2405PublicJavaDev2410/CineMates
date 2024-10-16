package com.filmfellows.cinemates.domain.cinema.model.vo;

import java.sql.Date;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Setter
@Getter
public class Showtime {
    private int showtimeNo;
    private int screenNo;
    private int movieNo;
    private String showtimeTime;
    private int showtimeSeat;
    private Date showtimeStart;
    private Date showtimeEnd;

    private int cinemaNo;
    private String screenName;
    private int screenSeat;
    private String title;
    private String rating;
    private int availableSeats;
    private int runningTime;
    private String genre;
    private String cinemaName;

}
