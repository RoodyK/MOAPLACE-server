package com.moaplace.dto;

import lombok.Data;

@Data
public class AdminBookingDetailDTO {
	
	private String scheduleDate;
	private String scheduleTime;
	private int bookingCount;
	private String bookingSeat;
	private String ticketAge;
	private String ticketGrade;
	private int ticket_count;
	private String paymentDate;
	private int ticketPrice;
	private int usePoint;
	private String paymentMethod;
	private int bookingPrice;
	private String paymentStatus;
	private String merchantUid;
	private String impUid;

}
