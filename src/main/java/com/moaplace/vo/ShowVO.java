package com.moaplace.vo;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ShowVO {

	private int show_num;
	private int genre_num;
	private int hall_num;
	private	String show_name;
	private	Date show_start;
	private	Date show_end;
	private String show_check;
	private Date stop_start;
	private	Date stop_end;
	private String show_age;
	private int intermission;
	private int running_time;
	private String show_thumbnail;
}
