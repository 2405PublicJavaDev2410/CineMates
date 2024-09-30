package com.filmfellows.cinemates.domain.payment.model.mapper;

import com.filmfellows.cinemates.domain.payment.model.vo.PaymentInfo;
import com.siot.IamportRestClient.response.Payment;
import org.apache.ibatis.annotations.Mapper;

import java.util.Optional;

@Mapper
public interface PaymentMapper {
        void insertPaymentInfo(PaymentInfo paymentInfo);
}
