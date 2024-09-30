package com.filmfellows.cinemates.app.mypage;

import com.filmfellows.cinemates.domain.mypage.model.service.MyPageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@Slf4j
@RequiredArgsConstructor
public class MyPageController {
    private final MyPageService myService;

    /**
     * 담당자 : 엄태운
     * 관련기능 : 예매내역 페이지 이동
     */
    @GetMapping("/my-page/booking-list")
    public String showMyReservation() {
        return "mypage/booking-list";
    }

    /**
     * 담당자 : 엄태운
     * 관련기능 : 구매내역 페이지 이동
     */
    public String showMyOrder() {
        return "";
    }

    /**
     * 담당자 : 엄태운
     * 관련기능 : 문의내역 페이지 이동
     */
    public String showMyQna() {
        return "";
    }

    /**
     * 담당자 : 엄태운
     * 관련기능 : 문의 상세조회 페이지 이동
     */
    public String showQnaDetail() {
        return "";
    }

    /**
     * 담당자 : 엄태운
     * 관련기능 : 문의 등록 페이지 이동
     */
    public String showRegisterQna() {
        return "";
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
    public int insertQna() {
        return 0;
    }

    /**
     * 담당자 : 엄태운
     * 관련기능 : 문의 삭제
     */
    public int deleteQna() {
        return 0;
    }

}
