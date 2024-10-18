package com.filmfellows.cinemates.domain.store.model.service;

import com.filmfellows.cinemates.domain.member.model.vo.Member;
import com.filmfellows.cinemates.domain.store.model.mapper.StorePayMapper;
import com.filmfellows.cinemates.domain.store.model.vo.StorePayment;
import com.siot.IamportRestClient.IamportClient;
import com.siot.IamportRestClient.request.CancelData;
import com.siot.IamportRestClient.response.IamportResponse;
import com.siot.IamportRestClient.response.Payment;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Configuration
@Service
@RequiredArgsConstructor
public class StorePayService {

    private IamportClient iamportClient;

    private final StorePayMapper storePayMapper;

    public IamportResponse<Payment> validateIamport(String impUid) {
        try {
            return iamportClient.paymentByImpUid(impUid);
            } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Transactional
    public void saveBuyerAndOrderInfo(StorePayment paymentInfo) {
        storePayMapper.insertPayment(paymentInfo);
    }

    public IamportResponse<Payment> cancelPayment(String impUid) {
        try {
            CancelData cancelData = new CancelData(impUid, true);
            return iamportClient.cancelPaymentByImpUid(cancelData);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public void deletePaymentInfo(String impUid) {
        storePayMapper.deletePaymentInfo(impUid);
    }

    public String selectImpUid(Integer purchaseNo) {
        return storePayMapper.selectImpUid(String.valueOf(purchaseNo));
    }

    public Member getMemberById(String memberId) {
        return storePayMapper.selectMemberById(memberId);
    }
}
