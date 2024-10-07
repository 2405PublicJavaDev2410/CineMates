package com.filmfellows.cinemates.domain.reservation.model.vo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class ShowInfoDTO {

    private String screenSeat;
    private String showtimeTime;
    private String screenName;
    private Integer availableSeats;
    private Integer screenNo;
    private Integer movieNo;
    private String title;
    private Integer cinemaNo;
    private String cinemaName;
}
