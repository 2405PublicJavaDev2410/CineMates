package com.filmfellows.cinemates.app.mypage;

import com.filmfellows.cinemates.app.mypage.dto.*;
import com.filmfellows.cinemates.common.Pagination;
import com.filmfellows.cinemates.domain.mypage.model.service.MyPageService;
import com.filmfellows.cinemates.domain.mypage.model.vo.Qna;
import com.filmfellows.cinemates.domain.mypage.model.vo.QnaFile;
import com.filmfellows.cinemates.domain.reservation.model.Service.ReservationService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.RowBounds;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Controller
@Slf4j
@RequiredArgsConstructor
public class MyPageController {
    private final MyPageService myService;
    private final ReservationService rService;

    /**
     * 담당자 : 엄태운
     * 관련기능 : 예매내역 페이지 이동
     */
    @GetMapping("/my-page/find-reservation")
    public String showMyReservation(HttpSession session) {
        String memberId = (String) session.getAttribute("memberId");
        if (memberId == null) {
            return "redirect:/login";
        }
        return "pages/mypage/findReservation";
    }

    /**
     * 담당자 : 엄태운
     * 관련기능 : 구매내역 페이지 이동
     */
    @GetMapping("/my-page/order-list")
    public String showMyOrder(HttpSession session) {
        String memberId = (String) session.getAttribute("memberId");
        if (memberId == null) {
            return "redirect:/login";
        }
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
        if(memberId == null) {
            return "redirect:/login";
        }
        int totalCount = myService.getTotalQnaCountById(memberId);
        int boardLimit = 10;
        Pagination pn = new Pagination(totalCount, currentPage, boardLimit);
        int offset = (currentPage - 1) * boardLimit;
        RowBounds rBounds = new RowBounds(offset, boardLimit);
        List<QnaDTO> qList = myService.selectAllQnaById(currentPage, memberId, rBounds);
        model.addAttribute("qList", qList);
        model.addAttribute("pn", pn);
        return "pages/mypage/qnaList";
    }

    /**
     * 담당자 : 엄태운
     * 관련기능 : 문의 검색
     */
    @PostMapping("/my-page/search-qna")
    public String searchQna(Model model,
            @RequestParam("searchCondition") String searchCondition,
            @RequestParam("searchKeyword") String searchKeyword,
            @RequestParam(value = "cp", required = false, defaultValue = "1") Integer currentPage,
            HttpSession session) {
        String memberId = (String) session.getAttribute("memberId");
        if (memberId == null) {
            return "redirect:/login";
        }
        int totalCount = myService.getTotalQnaCountByIdAndKeyword(memberId, searchCondition, searchKeyword);
        int boardLimit = 10;
        Pagination pn = new Pagination(totalCount, currentPage, boardLimit);
        int offset = (currentPage - 1) * boardLimit;
        RowBounds rBounds = new RowBounds(offset, boardLimit);
        List<QnaDTO> sList = myService.searchQnaByIdAndKeyword(memberId, searchCondition, searchKeyword, rBounds);
        model.addAttribute("sList", sList);
        model.addAttribute("pn", pn);
        model.addAttribute("searchCondition", searchCondition);
        model.addAttribute("searchKeyword", searchKeyword);
        return "pages/mypage/qnaSearchList";
    }

    /**
     * 담당자 : 엄태운
     * 관련기능 : 문의 상세조회 페이지 이동
     */
    @GetMapping("/my-page/qna-detail/{qnaNo}")
    public String showQnaDetail(@PathVariable("qnaNo") Integer qnaNo, Model model, HttpSession session) {
        String memberId = (String) session.getAttribute("memberId");
        if (memberId == null) {
            return "redirect:/login";
        }
        Qna qna = myService.selectOneQnaByNo(qnaNo);
        QnaFile qnaFile = myService.selectQnaFileByNo(qnaNo);
        model.addAttribute("qna", qna);
        if(qna.getParentQnaNo() == null) {
            Qna reply = myService.selectOneReplyByNo(qnaNo);
            if(reply != null) {
                model.addAttribute("reply", reply);
            }
        }
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
    public String showRegisterQna(HttpSession session) {
        String memberId = (String) session.getAttribute("memberId");
        if (memberId == null) {
            return "redirect:/login";
        }
        return "pages/mypage/qnaRegister";
    }

    /**
     * 담당자 : 엄태운
     * 관련기능 : 예매번호로 조회
     */
    @GetMapping("/find-reservation")
    @ResponseBody
    public myReservationResponse showReserveInfoByNo(HttpSession session,
            @RequestParam("reservationNo") String reservationNo) {
        String memberId = (String) session.getAttribute("memberId");
        myReservationRequest request = new myReservationRequest();
        request.setMemberId(memberId);
        request.setReservationNo(reservationNo);
        myReservationResponse response = rService.selectReservationInfo(request);
        System.out.println(response);
        if(response != null) {
            return response;
        }
        return null;
    }

    /**
     * 담당자 : 엄태운
     * 관련기능 : 구매내역 조회
     */
    @GetMapping("/find-order")
    @ResponseBody
    public ResponseEntity<List<myOrderResponse>> showOrderList(HttpSession session,
           @ModelAttribute myOrderRequest request,
           @RequestParam("startDate") String startDate,
           @RequestParam("endDate") String endDate) {
        String memberId = (String) session.getAttribute("memberId");
        request.setStartDate(startDate);
        request.setEndDate(endDate);
        request.setMemberId(memberId);
        System.out.println(request);
        List<myOrderResponse> response = myService.selectOrderList(request);
        System.out.println("응답" + response);

        if (response != null && !response.isEmpty()) {
            return ResponseEntity.ok(response); // 200 OK와 함께 리스트 반환
        } else {
            return ResponseEntity.noContent().build(); // 204 No Content
        }
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

}
