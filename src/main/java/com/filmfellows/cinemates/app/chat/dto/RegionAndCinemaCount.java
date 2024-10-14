package com.filmfellows.cinemates.app.chat.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class RegionAndCinemaCount {
    private Integer cinemaLocationCode;
    private String cinemaCount;
}
