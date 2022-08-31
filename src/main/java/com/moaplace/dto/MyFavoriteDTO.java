package com.moaplace.dto;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MyFavoriteDTO {
	private int show_num;
	private int rnum;
	private int member_num;
	private String show_name;
	private Date show_start;
	private Date show_end;
	private String genre_category;
	private String show_thumbnail;
}
