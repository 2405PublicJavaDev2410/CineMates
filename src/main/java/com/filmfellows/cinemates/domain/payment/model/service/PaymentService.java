package com.filmfellows.cinemates.domain.payment.model.service;

import com.filmfellows.cinemates.domain.payment.model.mapper.PaymentMapper;
import com.filmfellows.cinemates.domain.payment.model.vo.PaymentInfo;
import com.siot.IamportRestClient.IamportClient;
import com.siot.IamportRestClient.request.CancelData;
import com.siot.IamportRestClient.response.IamportResponse;
import com.siot.IamportRestClient.response.Payment;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

public class PaymentService {
    private IamportClient iamportClient;

    private PaymentInfo paymentInfo;

    @Value("${IMP_API_KEY}")
    String apiKey;
    @Value("${IMP_API_SECRETKEY}")
    String apiSecret;

    @PostConstruct
    public void init() {
        this.iamportClient = new IamportClient(apiKey, apiSecret);
    }

    @Autowired
    private PaymentMapper pmapper;

    public void saveBuyerInfo(PaymentInfo paymentInfo) {
        if (paymentInfo.getAmount() == null) {
            throw new IllegalArgumentException("Amount cannot be null");
        }
        pmapper.insertPaymentInfo(paymentInfo);
        System.out.println("Service: save_buyerInfo 메소드 종료");
    }

    public IamportResponse<Payment> validateIamport(String imp_uid) {
        try {
            IamportResponse<Payment> payment = iamportClient.paymentByImpUid(imp_uid);
            System.out.println("Service ValidatepaymentInfo:" + payment);
            return payment;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public IamportResponse<Payment> cancelPayment(String imp_uid) {
        try {
            CancelData cancelData = new CancelData(imp_uid, true);
            IamportResponse<Payment> payment = iamportClient.cancelPaymentByImpUid(cancelData);
            System.out.println("Service CancelpaymentInfo:" + payment);

            return payment;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public void setPmapper(PaymentMapper pmapper) {
        this.pmapper = pmapper;
    }
}
