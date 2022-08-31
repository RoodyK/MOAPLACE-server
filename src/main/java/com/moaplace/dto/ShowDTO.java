package com.moaplace.dto;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class ShowDTO {
	
	private int show_num;
	private int genre_num;
	private int hall_num;
	private String show_name;
	private Date show_start;
	private Date show_end;
	private String show_check;
	private String show_thumbnail;
}
