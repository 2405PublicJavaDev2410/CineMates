package com.filmfellows.cinemates.domain.report.model.mapper;

import com.filmfellows.cinemates.domain.report.model.vo.Report;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ReportMapper {
    List<Report> reportlist();
}
