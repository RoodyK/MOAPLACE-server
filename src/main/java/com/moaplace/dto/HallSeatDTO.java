package com.moaplace.dto;

import lombok.Data;

@Data
//좌석 선택 페이지  공연장 좌석수 불러오기
public class HallSeatDTO {
	
	private int hall_cols;
	private int hall_rows;
	
}
