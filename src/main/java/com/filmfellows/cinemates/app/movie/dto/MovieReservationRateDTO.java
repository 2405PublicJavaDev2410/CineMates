package com.filmfellows.cinemates.app.movie.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class MovieReservationRateDTO {
    private Long movieNo;
    private String title;
    private int visitorCount;
    private int totalCount;

    private double reservationRate;
}
