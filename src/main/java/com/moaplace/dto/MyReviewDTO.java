package com.moaplace.dto;

import java.sql.Date;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class MyReviewDTO {
	
	private int review_num;
	private int member_num;
	private int show_num;
	private String show_thumbnail;
	private String show_name;
	private int review_grade;
	private String review_content;
	private Date review_regdate;
}
