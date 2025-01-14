package com.filmfellows.cinemates.app.chat.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class CinemaInfoByRegion {
    private Integer cinemaNo;
    private String cinemaName;
}
