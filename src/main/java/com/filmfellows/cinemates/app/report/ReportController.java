package com.filmfellows.cinemates.app.report;

import com.filmfellows.cinemates.common.Pagination;
import com.filmfellows.cinemates.domain.report.model.service.ReportService;
import com.filmfellows.cinemates.domain.report.model.vo.Report;
import jakarta.servlet.http.HttpSession;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/report")
public class ReportController {
    private ReportService rService;
    @Autowired
    public ReportController(ReportService rService) {
        this.rService=rService;
    }

    @GetMapping("/report/{writeNo}&{writer}&{category}")
    public String showreport(Model model,@PathVariable String writeNo,@PathVariable String writer
    ,@PathVariable String category) {
        System.out.println(writeNo);
        System.out.println(writer);
        System.out.println(category);

        model.addAttribute("writeNo", writeNo);
        model.addAttribute("writer", writer);
        model.addAttribute("category", category);
        return "/pages/report/report";
    }
    @PostMapping("/report")
    public String insertreport(Report report,String writer,String category,String writeNo,Model model) {
        System.out.println(report.toString());
        report.setReportId(writer);
        report.setReportWriteno(Integer.parseInt(writeNo));
        report.setReportCategory(category);
        int count=rService.overlapreport(Integer.parseInt(writeNo),category);
        if(count>0) {
            model.addAttribute("message", "중복건입니다.");
            return "pages/cinema/admin/failmessage";
        }
        int result=rService.reportinsert(report);
        if(result>0) {
            return "pages/cinema/admin/sucess";
        }else{
            return "pages/cinema/admin/failed";
        }
    }

}
