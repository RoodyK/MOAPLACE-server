package com.moaplace.dto;

import java.sql.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
public class RentalListDTO {
	private int rental_num;
	private String hall_name;
	private String rental_name;
	private String rental_title;
	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd")
	private Date rental_date;
	private String rental_time;
	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd")
	private Date regdate;
	private String rental_state;
}
