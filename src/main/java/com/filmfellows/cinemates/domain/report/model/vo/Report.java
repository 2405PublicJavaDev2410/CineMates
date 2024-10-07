package com.filmfellows.cinemates.domain.report.model.vo;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.sql.Date;

@ToString
@Setter
@Getter
public class Report {
    private int reportNo;
    private String reportId;
    private String reportOption;
    private String reportContent;
    private Date reportDate;
}
