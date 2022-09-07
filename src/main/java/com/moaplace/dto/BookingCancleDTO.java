package com.moaplace.dto;

import lombok.Data;

@Data
public class BookingCancleDTO {

	private String imp_uid;
	private String reason;
	private int amount;
	private int booking_num;
	
}
