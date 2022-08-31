package com.moaplace.dto;

import java.sql.Date;

import lombok.Data;

@Data
public class ShowDetailDTO {
	
	private int show_num;
	private String show_thumbnail;
	private String show_name;
	private Date show_start;
	private Date show_end;
	private int hall_num;
	private String show_age;
	private String show_check;
}
