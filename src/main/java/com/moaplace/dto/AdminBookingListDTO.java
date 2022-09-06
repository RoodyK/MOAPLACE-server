package com.moaplace.dto;

import lombok.Data;

@Data
public class AdminBookingListDTO {
	
	private int bookingNum;
	private String memberId;
	private String showName;
	private String scheduleDate;
	private String scheduleTime;
	private int bookingPrice;
	private String bookingDate;
	private String status;

}
