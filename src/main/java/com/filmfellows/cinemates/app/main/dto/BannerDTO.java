package com.filmfellows.cinemates.app.main.dto;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class BannerDTO {
    private Long bannerNo;
    private String bannerTitle;
    private String bannerContent;
    private String bannerUrl;
    private String linkUrl;
    private String pageType;
    private LocalDate startDate;
    private LocalDate endDate;
    private String isActive;
}
