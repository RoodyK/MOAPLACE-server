package com.moaplace.vo;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookingVO {
	private int booking_num;
	private int member_num;
	private int schedule_num;
	private int booking_price;
	private String booking_seat;
	private int use_point;
	private Date regdate;
}
