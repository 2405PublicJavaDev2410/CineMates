package com.filmfellows.cinemates.app.mypage;

import com.filmfellows.cinemates.common.Pagination;
import com.filmfellows.cinemates.app.mypage.dto.QnaDTO;
import com.filmfellows.cinemates.app.mypage.dto.RegisterReplyRequest;
import com.filmfellows.cinemates.domain.mypage.model.service.MyPageService;
import com.filmfellows.cinemates.domain.mypage.model.vo.Qna;
import com.filmfellows.cinemates.domain.mypage.model.vo.QnaFile;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@Slf4j
@AllArgsConstructor
public class AdminMyPageController {
    private final MyPageService myService;

    /**
     * [관리자] 문의내역 페이지 이동
     */
    @GetMapping("/admin/qna-list")
    public String showQnaList(Model model,
          @RequestParam(value = "cp", required = false, defaultValue = "1") Integer currentPage) {
        int totalCount = myService.getTotalQnaCount();
        int boardLimit = 10;
        Pagination pn = new Pagination(totalCount, currentPage, boardLimit);
        int offset = (currentPage - 1) * boardLimit;
        RowBounds rBounds = new RowBounds(offset, boardLimit);
        List<QnaDTO> qList = myService.selectAllQna(rBounds, currentPage);
        model.addAttribute("qList", qList);
        model.addAttribute("pn", pn);
        return "pages/mypage/adminQnaList";
    }

    /**
     * [관리자] 문의 상세조회 페이지 이동
     */
    @GetMapping("/admin/qna-detail/{qnaNo}")
    public String showQnaDetail(@PathVariable("qnaNo") Integer qnaNo, Model model) {
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
        return "pages/mypage/adminQnaDetail";
    }

    /**
     * [관리자] 문의 답변 등록
     */
    @PostMapping("/register-reply")
    @ResponseBody
    public String insertReply(@RequestBody RegisterReplyRequest request,
                              HttpSession session) {
        Qna qna = new Qna();
        String title = "QnaReply";
        qna.setTitle(title);
        qna.setContent(request.getContent());
        qna.setMemberId(session.getAttribute("memberId").toString());
        qna.setParentQnaNo(request.getParentQnaNo());
        int result = myService.insertReply(qna);
        if(result == 1) {
            return "success";
        }
        return "fail";
    }

}