package com.filmfellows.cinemates.app.mypage;

import com.filmfellows.cinemates.app.movie.dto.Pagination;
import com.filmfellows.cinemates.app.mypage.dto.QnaDTO;
import com.filmfellows.cinemates.app.mypage.dto.RegisterQnaRequest;
import com.filmfellows.cinemates.domain.mypage.model.service.MyPageService;
import com.filmfellows.cinemates.domain.mypage.model.vo.Qna;
import com.filmfellows.cinemates.domain.mypage.model.vo.QnaFile;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.sql.Timestamp;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Controller
@Slf4j
@RequiredArgsConstructor
public class MyPageController {
    private final MyPageService myService;

    /**
     * 담당자 : 엄태운
     * 관련기능 : 예매내역 페이지 이동
     */
    @GetMapping("/my-page/find-reservation")
    public String showMyReservation() {
        return "pages/mypage/findReservation";
    }

    /**
     * 담당자 : 엄태운
     * 관련기능 : 구매내역 페이지 이동
     */
    @GetMapping("/my-page/order-list")
    public String showMyOrder() {
        return "pages/mypage/orderList";
    }

    /**
     * 담당자 : 엄태운
     * 관련기능 : 문의내역 페이지 이동
     */
    @GetMapping("/my-page/qna-list")
    public String showMyQna(HttpSession session, Model model,
            @RequestParam(value = "cp", required = false, defaultValue = "1") Integer currentPage) {
        String memberId = (String) session.getAttribute("memberId");
        int totalCount = myService.getTotalQnaCountById(memberId);
        Pagination pn = new Pagination(totalCount, currentPage);
        int limit = pn.getBoardLimit();
        int offset = (currentPage - 1) * limit;
        RowBounds rBounds = new RowBounds(offset, limit);
        List<QnaDTO> qList = myService.selectAllQnaById(currentPage, memberId, rBounds);
        model.addAttribute("qList", qList);
        model.addAttribute("pn", pn);
        return "pages/mypage/qnaList";
    }

    /**
     * 담당자 : 엄태운
     * 관련기능 : 문의 상세조회 페이지 이동
     */
    @GetMapping("/my-page/qna-detail/{qnaNo}")
    public String showQnaDetail(@PathVariable("qnaNo") Integer qnaNo, Model model) {
        Qna qna = myService.selectOneQnaByNo(qnaNo);
        QnaFile qnaFile = myService.selectQnaFileByNo(qnaNo);
        model.addAttribute("qna", qna);
        if(qnaFile != null) {
            model.addAttribute("qnaFile", qnaFile);
        }
        return "pages/mypage/qnaDetail";
    }

    /**
     * 담당자 : 엄태운
     * 관련기능 : 문의 등록 페이지 이동
     */
    @GetMapping("/my-page/qna-register")
    public String showRegisterQna() {
        return "pages/mypage/qnaRegister";
    }

    /**
     * 담당자 : 엄태운
     * 관련기능 : 예매번호로 조회
     */
    public String showReserveInfoByNo() {
        return "";
    }

    /**
     * 담당자 : 엄태운
     * 관련기능 : 문의 등록
     */
    @PostMapping("/qna-register")
    @ResponseBody
    public String insertQna(@ModelAttribute RegisterQnaRequest request,
            @RequestParam(value="qnaFile", required=false) MultipartFile qnaFile,
            HttpSession session) throws IllegalStateException, IOException {
        String memberId = session.getAttribute("memberId").toString();
        Qna qna = new Qna();
        qna.setTitle(request.getTitle());
        qna.setContent(request.getContent());
        qna.setMemberId(memberId);
        // 첨부파일이 있는 경우에만 처리
        int result = myService.insertQna(qna, (qnaFile != null && !qnaFile.isEmpty() ? qnaFile : null));
        if(result == 1) {
            return "success";
        }else {
            return "fail";
        }
    }

    /**
     * 담당자 : 엄태운
     * 관련기능 : 문의 삭제
     */
    @GetMapping("/qna-delete/{qnaNo}")
    public String deleteQna(@PathVariable("qnaNo") Integer qnaNo) {
        myService.deleteQna(qnaNo);
        return "redirect:/my-page/qna-list";
    }

    /**
     * 담당자 : 엄태운
     * 관련기능 : 작성일자 Timestamp를 String으로 변환
     */
    public String convertTimestampToString(Timestamp regDate) {
        // Timestamp 타입의 데이터를 Date로 변환
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return regDate.toLocalDateTime().format(formatter);
    }

}
