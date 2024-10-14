package com.filmfellows.cinemates.domain.report.model.mapper;

import com.filmfellows.cinemates.domain.report.model.vo.Report;
import org.apache.ibatis.annotations.Mapper;

import java.util.Date;
import java.util.List;

@Mapper
public interface ReportMapper {
    List<Report> reportlist();

    int reportinsert(Report report);

    int reportid(String memberId);

    int outreportid(String reportId, Date reportDate);

    int updatereportid(int reportNo);

    int allupdatereportid(String reportId);
}
