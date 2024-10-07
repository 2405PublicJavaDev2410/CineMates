package com.filmfellows.cinemates.domain.report.model.service.impl;

import com.filmfellows.cinemates.domain.cinema.model.mapper.CinemaMapper;
import com.filmfellows.cinemates.domain.report.model.mapper.ReportMapper;
import com.filmfellows.cinemates.domain.report.model.service.ReportService;
import com.filmfellows.cinemates.domain.report.model.vo.Report;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
}
