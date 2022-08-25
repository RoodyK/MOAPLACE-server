package com.moaplace.dto;

import java.sql.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
public class RentalCalendarDTO {
	private int rental_num;
	private int hall_num;
	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd")
	private Date rental_date;
	private String rental_time;
	private String rental_title;
}
