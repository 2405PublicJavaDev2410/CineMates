package com.filmfellows.cinemates.app.payment;

import com.filmfellows.cinemates.domain.payment.model.service.PaymentService;
import com.filmfellows.cinemates.domain.payment.model.vo.PaymentInfo;
import com.siot.IamportRestClient.IamportClient;
import com.siot.IamportRestClient.exception.IamportResponseException;
import com.siot.IamportRestClient.response.IamportResponse;
import com.siot.IamportRestClient.response.Payment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
@Controller
public class PaymentController {
    @Autowired
    private PaymentService paymentService;

    private final IamportClient iamportClient;

    @Value("${IMP_API_KEY}")
    String apiKey;
    @Value("${IMP_API_SECRETKEY}")
    String apiSecret;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
        this.iamportClient = new IamportClient(apiKey, apiSecret);
    }

    @GetMapping("/payTest")
    public String gopay() {
        return "pages/payment/inipay";
    }

    @GetMapping("/cancel")
    public String cancelpay() {
        return "cancel";
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
    public ResponseEntity<String> saveBuyerInfo(@RequestBody PaymentInfo paymentInfo) {
        System.out.println("Controller 지나감");
        System.out.println("Amount: " + paymentInfo.getAmount());
        System.out.println("PaymentInfo: " + paymentInfo);
        paymentService.saveBuyerInfo(paymentInfo);
        return ResponseEntity.ok("결제정보 저장 완");
    }

    @GetMapping("/payments/cancel/{imp_uid}")
    public ResponseEntity<String> cancelPayment(@PathVariable String imp_uid)
            throws IamportResponseException, IOException {
        System.out.println("Cancel: " + imp_uid);
        IamportResponse<Payment> response = paymentService.cancelPayment(imp_uid);
        return ResponseEntity.ok("취소 완!");
    }
}
