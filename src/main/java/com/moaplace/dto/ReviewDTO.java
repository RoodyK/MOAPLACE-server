package com.moaplace.dto;

import java.sql.Date;

import lombok.Data;

@Data
public class ReviewDTO {
	
	private int review_num;
	private int show_num;
	private int member_num;
	private String review_content;
	private int review_grade;
	private Date review_regdate;
	private String member_id;
}
