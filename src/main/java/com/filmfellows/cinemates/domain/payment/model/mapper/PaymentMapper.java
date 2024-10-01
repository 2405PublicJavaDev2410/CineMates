package com.filmfellows.cinemates.domain.payment.model.mapper;

import com.filmfellows.cinemates.domain.payment.model.vo.PaymentInfo;
import com.filmfellows.cinemates.domain.reservation.model.vo.ReservationDTO;
import com.siot.IamportRestClient.response.Payment;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Optional;

@Mapper
public interface PaymentMapper {
        void insertPaymentInfo(PaymentInfo paymentInfo);

        List<ReservationDTO> searchPayment(ReservationDTO rDto);
}
