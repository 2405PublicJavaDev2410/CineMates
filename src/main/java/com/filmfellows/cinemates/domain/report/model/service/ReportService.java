package com.filmfellows.cinemates.domain.report.model.service;

import com.filmfellows.cinemates.domain.report.model.vo.Report;

import java.util.Date;
import java.util.List;

public interface ReportService {
    /**
     * 신고 전체조회
     * pram
     * return List<Report>

     */
    List<Report> reportlist();
    /**
     * 신고 등록
     * pram report
     * return result

     */
    int reportinsert(Report report);
    /**
     * 회원신고 등록
     * pram report
     * return result

     */
    int reportid(String memberId);
    /**
     * 회원제재 등록
     * pram report
     * return result

     */
    int outreportid(String reportId, Date reportDate);
    /**
     * 제재완료 등록
     * pram reportNo
     * return result

     */
    int updatereportid(int reportNo);
    /**
     * 제재3회등록시 전체 완료처리
     * pram reportId
     * return result

     */
    int allupdatereportid(String reportId);
}
