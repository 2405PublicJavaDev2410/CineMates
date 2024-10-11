package com.filmfellows.cinemates.app.payment;

import com.filmfellows.cinemates.domain.member.model.vo.Member;
import com.filmfellows.cinemates.domain.payment.model.service.PaymentService;
import com.filmfellows.cinemates.domain.payment.model.vo.PaymentInfo;
import com.filmfellows.cinemates.domain.reservation.model.Service.ReservationService;
import com.filmfellows.cinemates.domain.reservation.model.vo.MemberDTO;
import com.filmfellows.cinemates.domain.reservation.model.vo.ReservationDTO;
import com.filmfellows.cinemates.domain.reservation.model.vo.ShowInfoDTO;
import com.siot.IamportRestClient.IamportClient;
import com.siot.IamportRestClient.exception.IamportResponseException;
import com.siot.IamportRestClient.response.IamportResponse;
import com.siot.IamportRestClient.response.Payment;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@Controller
public class PaymentController {
    @Autowired
    private PaymentService paymentService;
    @Autowired
    private ReservationDTO rDTO;
    private final IamportClient iamportClient;
    @Autowired
    private ReservationService rService;
    @Value("${IMP_API_KEY}")
    String apiKey;
    @Value("${IMP_API_SECRETKEY}")
    String apiSecret;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
        this.iamportClient = new IamportClient(apiKey, apiSecret);
    }

    @PostMapping("/paymentReady")
    public String readyTogoPay(@ModelAttribute("ReservationDTO") ReservationDTO rDTO,
                               @ModelAttribute("ShowInfoDTO") ShowInfoDTO sDTO,Model model, HttpSession session) {
        System.out.println("paymentReady" + rDTO);
        String memberId = (String)session.getAttribute("memberId");
        MemberDTO info = rService.selectMemberInfo(memberId);
        System.out.println("info" + info);

        rDTO.setBuyer_email(info.getEmail());
        rDTO.setBuyer_name(info.getName());
        rDTO.setBuyer_tel(info.getPhone());

        session.setAttribute("rDTO", rDTO);
        session.setAttribute("sDTO", sDTO);
        System.out.println("rDTO 함 보여바라" + rDTO);
        System.out.println("sDTO Payment" + sDTO);
        model.addAttribute("rDTO", rDTO);
        return "redirect:/payment?reservationNo=" + rDTO.getReservationNo();
    }

    @GetMapping("/payment")
    public String showPayForm(@ModelAttribute ReservationDTO rDTO,
                              @ModelAttribute("ShowInfoDTO") ShowInfoDTO sDTO,Model model, HttpSession session) {
        String memberId = (String)session.getAttribute("memberId");
        ReservationDTO DTO = (ReservationDTO) session.getAttribute("rDTO");
        ShowInfoDTO DTOs = (ShowInfoDTO) session.getAttribute("sDTO");
        model.addAttribute("memberId", memberId);
        System.out.println("showPayForm" + DTO);
        System.out.println("showPayForm sDTO" + sDTO);
        model.addAttribute("rDTO", DTO);
        model.addAttribute("sDTO", DTOs);
        return "pages/payment/inipay";
    }


    @PostMapping("/validation/{imp_uid}")
    @ResponseBody
    public IamportResponse<Payment> validateIamport(@PathVariable String imp_uid)
            throws IamportResponseException, IOException {
        System.out.println("validation Controller :" + imp_uid);
        return paymentService.validateIamport(imp_uid);
    }

    @PostMapping("/save_buyerInfo")
    @ResponseBody
    public ResponseEntity<String> saveBuyerInfo(
            @RequestBody Map<String, Object> data
            ,HttpSession session
//            @RequestBody PaymentInfo paymentInfo
    ) {
//        System.out.println("PaymentInfo: " + paymentInfo);
        Map<String, Object> buyerInfo = (Map<String, Object>) data.get("buyerInfo");
        Map<String, Object> reserveInfo = (Map<String, Object>) data.get("reserveInfo");
//        paymentService.saveBuyerInfo(paymentInfo);
        System.out.println("saveBuyerInfo" + buyerInfo);
        System.out.println("saveBuyerInfo2" + reserveInfo);
        String memberId = (String)session.getAttribute("memberId");

        paymentService.saveBuyerAndOrderInfo(buyerInfo, reserveInfo);
        return ResponseEntity.ok("결제정보 저장 완");
    }

    @GetMapping("/payments/cancel/{imp_uid}")
    public ResponseEntity<String> cancelPayment(@PathVariable String imp_uid)
            throws IamportResponseException, IOException {
        System.out.println("Cancel: " + imp_uid);
        IamportResponse<Payment> response = paymentService.cancelPayment(imp_uid);
        paymentService.deleteReserveAndPaymentInfo(imp_uid);
        return ResponseEntity.ok("취소 완!");
    }

    @GetMapping("/getImpUid")
    public ResponseEntity<String> getImpUid(@RequestParam String reservationNo) {
        System.out.println("getReservationNo: " + reservationNo);
        String impUid= paymentService.selectImpUid(reservationNo);

        System.out.println("impUid : " + impUid);
        return ResponseEntity.ok(impUid);
    }
}
