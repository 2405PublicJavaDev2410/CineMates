package com.filmfellows.cinemates.domain.payment.model.mapper;

import com.filmfellows.cinemates.domain.payment.model.vo.PaymentInfo;
import com.filmfellows.cinemates.domain.reservation.model.vo.ReservationDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface PaymentMapper {

    void insertPaymentInfo(Map<String, Object> buyerInfo);

    String selectImpUid(String reservationNo);

    void deletePaymentInfo(String impUid);
}
