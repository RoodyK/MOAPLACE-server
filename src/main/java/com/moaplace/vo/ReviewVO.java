package com.moaplace.vo;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ReviewVO {
	
	private int review_num;
	private int show_num;
	private int member_num;
	private String review_content;
	private int review_grade;
	private Date review_regdate;
}
