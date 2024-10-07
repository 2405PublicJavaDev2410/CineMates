package com.filmfellows.cinemates.app.report;

import com.filmfellows.cinemates.domain.report.model.service.ReportService;
import com.filmfellows.cinemates.domain.report.model.vo.Report;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/report")
public class ReportController {
    private ReportService rService;
    @Autowired
    public ReportController(ReportService rService) {
        this.rService=rService;
    }

    @GetMapping("/report")
    public String showreport() {
        return "/pages/report/report";
    }
    @PostMapping("/report")
    public String insertreport(Report report) {
        System.out.println(report.toString());

        return "/pages/report/report";
    }
    @GetMapping("/reportlist")
    public String showreportlist() {
        List<Report> rList=rService.reportlist();
        return "/pages/report/reportlist";
    }
}
