package com.filmfellows.cinemates.domain.report.model.service.impl;

import com.filmfellows.cinemates.domain.cinema.model.mapper.CinemaMapper;
import com.filmfellows.cinemates.domain.report.model.mapper.ReportMapper;
import com.filmfellows.cinemates.domain.report.model.service.ReportService;
import com.filmfellows.cinemates.domain.report.model.vo.Report;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class ReportServiceImpl implements ReportService {

    private ReportMapper mapper;
    @Autowired
    public ReportServiceImpl(ReportMapper mapper) {
        this.mapper=mapper;
    }
    @Override
    public List<Report> reportlist() {
        List<Report> rlist = mapper.reportlist();
        return rlist;
    }

    @Override
    public int reportinsert(Report report) {
        int result=mapper.reportinsert(report);
        return result;
    }

    @Override
    public int reportid(String memberId) {
        int result=mapper.reportid(memberId);
        return result;
    }

    @Override
    public int outreportid(String reportId, Date reportDate) {
        int result=mapper.outreportid(reportId,reportDate);
        return result;
    }

    @Override
    public int updatereportid(int reportNo) {
        int result=mapper.updatereportid(reportNo);
        return result;
    }

    @Override
    public int allupdatereportid(String reportId) {
        int result=mapper.allupdatereportid(reportId);
        return result;
    }
}
