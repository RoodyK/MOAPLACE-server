package com.moaplace.vo;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaymentVO {
	private int payment_num;
	private int booking_num;
	private int merchant_uid;
	private int imp_uid;
	private int booking_price;
	private String payment_method;
	private String payment_status;
	private Date payment_date;
}
