package com.moaplace.dto;

import lombok.Data;

@Data
public class MyBookingCancleRequestDTO {
	
	private int booking_num;
	private String member_id;
	private String member_pwd;
	private String reason;
	private int amount;
	private String imp_uid;
}
