package com.moaplace.dto;

import lombok.Data;

@Data
//좌석 선택 페이지 좌석 등급별 가격 조회
public class GradePriceDTO {

	private String grade_seat;
	private int grade_price;
	
}
