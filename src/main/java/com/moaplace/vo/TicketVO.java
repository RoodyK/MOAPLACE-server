package com.moaplace.vo;

import lombok.AllArgsConstructor;

import lombok.NoArgsConstructor;

import lombok.Data;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TicketVO {
	private int ticket_num;
	private int booking_num;
	private String ticket_rating;
	private int ticket_count;
	private String ticket_grade;
	private int ticket_price;
}
