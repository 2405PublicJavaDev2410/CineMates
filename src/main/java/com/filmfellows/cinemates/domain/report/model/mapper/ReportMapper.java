package com.filmfellows.cinemates.domain.report.model.mapper;

import com.filmfellows.cinemates.domain.report.model.vo.Report;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.session.RowBounds;

import java.util.Date;
import java.util.List;

@Mapper
public interface ReportMapper {
    List<Report> reportlist(Integer currentPage, RowBounds rowBounds);

    int reportinsert(Report report);

    int reportid(String memberId);

    int outreportid(String reportId, Date reportDate);

    int updatereportid(int reportNo);

    int allupdatereportid(String reportId);

    Report onereport(int reportNo);

    int deletechat(int reportWriteno);

    int deletereview(int reportWriteno);

    int countreportlist();

    List<Report> reportlist2(Integer currentPage, RowBounds rowBounds);

    int countreportlist2();

    int deletechating(int reportWriteno);

    int overlapreport(int writeno, String category);
}
