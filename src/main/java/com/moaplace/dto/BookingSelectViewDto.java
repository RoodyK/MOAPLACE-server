package com.moaplace.dto;

import lombok.Data;

@Data
public class BookingSelectViewDto {
	
	private int showNum;
	private String title;
	private String showStart;
	private String showEnd;
	private int hallNum;
	private int seatCnt;
	private int extraCnt;
	private int scheduleNum;
	private String scheduleDate;
	private String scheduleTime;
	

}
