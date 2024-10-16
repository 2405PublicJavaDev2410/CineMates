package com.filmfellows.cinemates.app.report;

import com.filmfellows.cinemates.domain.report.model.service.ReportService;
import com.filmfellows.cinemates.domain.report.model.vo.Report;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
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
        report.setReportId("MEM002");
        int result=rService.reportinsert(report);
        return "/pages/report/report";
    }
    @GetMapping("/reportlist")
    public String showreportlist(Model model) {
        List<Report> rList=rService.reportlist();
        model.addAttribute("rList", rList);
        return "/pages/report/reportlist";
    }
    @GetMapping("/reportid{memberId}")
    public String reportid(Model model,@PathVariable("memberId") String memberId,
                           @RequestParam int reportNo) {
        int result=rService.reportid(memberId);
        result=rService.updatereportid(reportNo);
        return "redirect:/report/reportlist";
    }
    @GetMapping("/overreportid{memberId}")
    public String overreportid(Model model,@PathVariable("memberId") String memberId) {
        model.addAttribute("memberId", memberId);
        return "/pages/report/date";
    }
    @GetMapping("/outreportid{reportId}")
    public String outreportid(Model model,@PathVariable("reportId") String reportId,
                              @RequestParam Date reportDate) {

        int result=rService.outreportid(reportId,reportDate);
        result=rService.allupdatereportid(reportId);
        return "redirect:/report/reportlist";


    }

}
