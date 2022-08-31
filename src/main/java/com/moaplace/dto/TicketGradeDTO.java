package com.moaplace.dto;

import lombok.Data;

@Data
//좌석 선택 페이지 좌석 등급별 행수 조회
public class TicketGradeDTO {
	
	private String grade_seat;
	private int seat_line;
	
}
