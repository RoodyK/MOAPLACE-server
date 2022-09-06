package com.moaplace.dto;

import lombok.Data;

@Data
public class AdminBookingDetailResponseDTO {
	
	private String scheduleDate;
	private String scheduleTime;
	private int bookingCount;
	private String ticketDetail;
	private String bookingSeat;
	private String paymentDate;
	private int AllticketPrice;
	private int usePoint;
	private String paymentMethod;
	private int bookingPrice;
	private String paymentStatus;
	private String merchantUid;
	private String impUid;
	

}
