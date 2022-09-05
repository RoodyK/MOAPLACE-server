package com.moaplace.dto;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MyBookingDetailDTO {
	
	private int booking_num;
	private Date regdate;
	private String show_name;
	private Date schedule_date;
	private String schedule_time;
	private String booking_seat;
	private String hall_name;
	private Date payment_date;
	private int booking_price;
	private int use_point;
	private String payment_method;
	private String payment_status;
	private int show_num;
	private String imp_uid;
	
}
