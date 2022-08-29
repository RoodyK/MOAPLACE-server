package com.moaplace.dto;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MyRentalDTO {
	
	private int rental_num;
	private Date regdate;
	private String hall_name;
	private Date rental_date;
	private String rental_time;
	private String rental_state;
}
