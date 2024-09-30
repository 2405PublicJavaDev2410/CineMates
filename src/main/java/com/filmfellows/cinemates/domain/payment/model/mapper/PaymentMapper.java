package com.filmfellows.cinemates.domain.payment.model.mapper;

import com.filmfellows.cinemates.domain.payment.model.vo.PaymentInfo;
import org.apache.ibatis.annotations.Mapper;

public interface PaymentMapper {
    @Mapper
        void insertPaymentInfo(PaymentInfo paymentInfo);
}
