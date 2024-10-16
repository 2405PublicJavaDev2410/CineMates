/*
package com.filmfellows.cinemates.app.store;

import com.filmfellows.cinemates.domain.store.model.service.StorePaymentService;
import com.filmfellows.cinemates.domain.store.model.vo.StorePaymentInfo;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/store")
@RequiredArgsConstructor
public class StorePaymentController {

    private final StorePaymentService spService;

    @PostMapping("/ready")
    public String readyToPayStore(@ModelAttribute("StorePaymentInfo") StorePaymentInfo pInfo
            , Model model, HttpSession session) {
        String memberId = (String) session.getAttribute("memberId");

        // 여기에서 회원 정보를 조회하여 pInfo에 설정하는 로직 추가

        session.setAttribute("pInfo", pInfo);
        model.addAttribute("pInfo", pInfo);
        return "store/storePayment";
    }

    @GetMapping("/storepayment")
    public String showPayForm(@ModelAttribute)

    @PostMapping("/validation/{imp_uid}")
    @ResponseBody
    public IamportResponse<Payment> validateIamport(@PathVariable String imp_uid) {
        return spService.validateIamport(imp_uid);
    }

    @PostMapping("/save_buyerInfo")
    @ResponseBody
    public ResponseEntity<String> saveBuyerInfo(@RequestBody StorePaymentInfo paymentInfo, HttpSession session) {
        String memberId = (String) session.getAttribute("memberId");

        spService.saveBuyerAndOrderInfo(paymentInfo);
        return ResponseEntity.ok("결제정보 저장 완료");
    }

    @GetMapping("/cancel/{imp_uid}")
    public ResponseEntity<String> cancelPayment(@PathVariable String imp_uid) {
        IamportResponse<Payment> response = spService.cancelPayment(imp_uid);
        spService.deletePaymentInfo(imp_uid);
        return ResponseEntity.ok("취소 완료!");
    }

    @GetMapping("/getImpUid")
    public ResponseEntity<String> getImpUid(@RequestParam Integer purchaseNo) {
        String impUid = spService.selectImpUid(purchaseNo);
        return ResponseEntity.ok(impUid);
    }
}
*/
