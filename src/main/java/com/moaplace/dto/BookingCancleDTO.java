package com.moaplace.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class BookingCancleDTO {

	private String imp_uid;
	private String reason;
	private int amount;
	private int booking_num;
	
}
