package com.filmfellows.cinemates.app.main.dto;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class boxOfficeDTO {
    private Long movieNo;
    private String title;
    private String posterUrl;

    private int visitorCount;
    private int totalCount;

    private String reservationRate;

//    private Long ReviewNo;
//    private Integer score;

    private String reviewCount;
    private String averageScore;

}
