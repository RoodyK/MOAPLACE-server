package com.moaplace.dto;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MyBookingDTO {

	private int booking_num;
	private Date regdate;
	private byte[] show_thumbnail;
	private String show_name;
	private String hall_name;
	private Date schedule_date;
	private String schedule_time;
	private String booking_seat;
	private int booking_price;
	private String payment_status;
	private int show_num;
}
