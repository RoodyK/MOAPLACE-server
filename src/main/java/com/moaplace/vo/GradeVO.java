package com.moaplace.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class GradeVO {
	private String grade_seat;		//좌석등급
	private int show_num;			//공연번호
	private int grade_price;		//좌석가격
}
