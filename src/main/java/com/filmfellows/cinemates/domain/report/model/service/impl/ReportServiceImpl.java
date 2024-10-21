package com.filmfellows.cinemates.domain.report.model.service.impl;

import com.filmfellows.cinemates.domain.cinema.model.mapper.CinemaMapper;
import com.filmfellows.cinemates.domain.report.model.mapper.ReportMapper;
import com.filmfellows.cinemates.domain.report.model.service.ReportService;
import com.filmfellows.cinemates.domain.report.model.vo.Report;
import org.apache.ibatis.session.RowBounds;
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
    public List<Report> reportlist(Integer currentPage, RowBounds rowBounds) {
        List<Report> rlist = mapper.reportlist(currentPage,rowBounds);
        return rlist;
    }

    @Override
    public List<Report> reportlist2(Integer currentPage, RowBounds rowBounds) {
        List<Report> rlist = mapper.reportlist2(currentPage,rowBounds);
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

    @Override
    public Report onereport(int reportNo) {
        Report repot=mapper.onereport(reportNo);
        return repot;
    }

    @Override
    public int deletechat(int reportWriteno) {
        int result=mapper.deletechat(reportWriteno);
        return result;
    }

    @Override
    public int deletereview(int reportWriteno) {
        int result=mapper.deletereview(reportWriteno);
        return result;
    }


    @Override
    public int countreportlist() {
        int totalcount=mapper.countreportlist();
        return totalcount;
    }
    @Override
    public int countreportlist2() {
        int totalcount=mapper.countreportlist2();
        return totalcount;
    }

    @Override
    public int deletechating(int reportWriteno) {
        int result=mapper.deletechating(reportWriteno);
        return result;
    }

    @Override
    public int overlapreport(int writeno, String category) {
        int count=mapper.overlapreport(writeno,category);
        return count;
    }
}
