package com.moaplace.dto;

import lombok.Data;

@Data
//예매페이지 공연장, 공연명 조회
public class BookingShowDTO {
	
	private int hall_num;
	private String show_name;
	
}
