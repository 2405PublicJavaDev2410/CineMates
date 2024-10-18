package com.filmfellows.cinemates.domain.report.model.service;

import com.filmfellows.cinemates.domain.report.model.vo.Report;
import org.apache.ibatis.session.RowBounds;

import java.util.Date;
import java.util.List;

public interface ReportService {

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
    /**
     * 레포트번호로 조회
     * pram reportNo
     * return report

     */
    Report onereport(int reportNo);
    /**
     * 채팅방 제재삭제
     * pram reportWriteno
     * return result

     */
    int deletechat(int reportWriteno);
    /**
     * 리뷰 제재삭제
     * pram reportWriteno
     * return result

     */
    int deletereview(int reportWriteno);

    /**
     * 신고 처리중 카운팅
     * pram
     * return List<Report>

     */
    int countreportlist();
    /**
     * 신고 전체조회(처리중)
     * pram
     * return List<Report>

     */
    List<Report> reportlist(Integer currentPage, RowBounds rowBounds);
    /**
     * 신고 전체조회(완료)
     * pram
     * return List<Report>

     */
    List<Report> reportlist2(Integer currentPage, RowBounds rowBounds);
    /**
     * 신고 전체조회(완료)카운팅
     * pram
     * return int

     */
    int countreportlist2();
    /**
     * 채팅방 채팅 제재삭제
     * pram reportWriteno
     * return result

     */
    int deletechating(int reportWriteno);
}
