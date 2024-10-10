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
    private int availableSeats;

}
