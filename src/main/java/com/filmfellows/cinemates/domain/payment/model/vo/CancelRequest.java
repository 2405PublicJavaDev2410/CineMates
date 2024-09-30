package com.filmfellows.cinemates.domain.payment.model.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;

@Getter
@Setter
@ToString
public class CancelRequest {
private String imp_uid;
private BigDecimal cancel_amount;
private String reason;
private String merchant_uid;
private String amount;
private String currency;
private String status;
private BigDecimal checksum;
}
