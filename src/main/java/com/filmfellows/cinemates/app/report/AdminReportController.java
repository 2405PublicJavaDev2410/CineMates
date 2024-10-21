package com.filmfellows.cinemates.app.report;

import com.filmfellows.cinemates.common.Pagination;
import com.filmfellows.cinemates.domain.report.model.service.ReportService;
import com.filmfellows.cinemates.domain.report.model.vo.Report;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminReportController {
    private ReportService rService;
    @Autowired
    public AdminReportController(ReportService rService) {
        this.rService=rService;
    }


    @GetMapping("/reportlist")
    public String showreportlist(@RequestParam(value="cp", required=false,defaultValue="1") Integer currentPage
            ,Model model) {
        int totalCount=rService.countreportlist();
        int limit = 10;
        Pagination pn =new Pagination(totalCount, currentPage,limit);
        int offset = (currentPage - 1) * limit;
        RowBounds rowBounds = new RowBounds(offset, limit);
        List<Report> rList=rService.reportlist(currentPage,rowBounds);
        model.addAttribute("pn",pn);
        model.addAttribute("rList", rList);
        return "/pages/report/reportlist";
    }

    @GetMapping("/reportlist2")
    public String showreportlist2(@RequestParam(value="cp", required=false,defaultValue="1") Integer currentPage,
                                  Model model) {
        int totalCount=rService.countreportlist2();
        int limit = 10;
        Pagination pn =new Pagination(totalCount, currentPage,limit);
        int offset = (currentPage - 1) * limit;
        RowBounds rowBounds = new RowBounds(offset, limit);
        List<Report> rList=rService.reportlist2(currentPage,rowBounds);
        model.addAttribute("pn",pn);
        model.addAttribute("rList", rList);
        return "/pages/report/reportlist2";
    }

    @GetMapping("/reportid{memberId}")
    public String reportid(Model model,@PathVariable("memberId") String memberId,
                           @RequestParam int reportNo) {
        System.out.println(memberId);
        int result=rService.reportid(memberId);
        System.out.println("여기1");
        result=rService.updatereportid(reportNo);
        System.out.println("여기2");
        Report report=rService.onereport(reportNo);
        System.out.println("여기3");
        if(report.getReportCategory().equals("채팅")){
            //삭제
            result=rService.deletechat(report.getReportWriteno());
        }else if(report.getReportCategory().equals("리뷰")){
            System.out.println("리뷰여기");
            result=rService.deletereview(report.getReportWriteno());
        }else{
            result=rService.deletechating(report.getReportWriteno());
        }
        return "redirect:/admin/reportlist";
    }
    @GetMapping("/overreportid{memberId}")
    public String overreportid(Model model,@PathVariable("memberId") String memberId,
                               @RequestParam int reportNo) {
        model.addAttribute("memberId", memberId);
        model.addAttribute("reportNo", reportNo);

        return "/pages/report/date";
    }
    @GetMapping("/outreportid{reportId}")
    public String outreportid(Model model,@PathVariable("reportId") String reportId,
                              @RequestParam Date reportDate,
                              @RequestParam int reportNo) {

        int result=rService.outreportid(reportId,reportDate);
        result=rService.allupdatereportid(reportId);
        Report report=rService.onereport(reportNo);
        if(report.getReportCategory().equals("채팅")){
            //삭제
            result=rService.deletechat(report.getReportWriteno());
        }else{
            result=rService.deletereview(report.getReportWriteno());
        }
        return "redirect:/admin/reportlist";

    }

}
