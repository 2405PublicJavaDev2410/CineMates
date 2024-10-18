package com.filmfellows.cinemates.app.store;

import com.filmfellows.cinemates.domain.member.model.vo.Member;
import com.filmfellows.cinemates.domain.store.model.service.StorePayService;
import com.filmfellows.cinemates.domain.store.model.vo.StorePayment;
import com.siot.IamportRestClient.IamportClient;
import com.siot.IamportRestClient.response.IamportResponse;
import com.siot.IamportRestClient.response.Payment;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/store")
public class StorePayController {

    private final StorePayService spService;
    private final IamportClient iamportClient;

    @Value("${IMP_API_KEY2}")
    String apiKey;
    @Value("${IMP_API_SECRETKEY2}")
    String apiSecret;

    public StorePayController(StorePayService spService) {
        this.spService = spService;
        this.iamportClient = new IamportClient(apiKey, apiSecret);
    }

    @PostMapping("/ready")
    public String readyToPayStore(@ModelAttribute("StorePaymentInfo") StorePayment pInfo
            , Model model, HttpSession session) {
        String memberId = (String) session.getAttribute("memberId");

        // 여기에서 회원 정보를 조회하여 pInfo에 설정하는 로직 추가
        Member member = spService.getMemberById(memberId);
        if (member != null) {
            pInfo.setBuyer_email(member.getEmail());
            pInfo.setBuyer_name(member.getName());
            pInfo.setBuyer_tel(member.getPhone());
        } else {
            // 회원 정보가 없을 경우 에러 처리
            return "redirect:/error";
        }

        session.setAttribute("pInfo", pInfo);
        model.addAttribute("pInfo", pInfo);
        return "store/storePayment";
    }

    @PostMapping("/validation/{imp_uid}")
    @ResponseBody
    public IamportResponse<Payment> validateIamport(@PathVariable String imp_uid) {
        return spService.validateIamport(imp_uid);
    }

    @PostMapping("/save_buyerInfo")
    @ResponseBody
    public ResponseEntity<String> saveBuyerInfo(@RequestBody StorePayment paymentInfo, HttpSession session) {
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
