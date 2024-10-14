package com.filmfellows.cinemates.domain.payment.model.vo;

import com.filmfellows.cinemates.domain.reservation.model.vo.MemberDTO;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class PaymentInfo extends MemberDTO {
    private String imp_uid;
    private String merchant_uid;
    private Integer amount;
    private String status;
    private String buyer_email;
    private String buyer_name;
    private String buyer_tel;
    private String name;
    private Integer movieNo;
    private Integer screenNo;
    private String reservationNo;
}
