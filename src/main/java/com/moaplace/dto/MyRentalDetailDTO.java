package com.moaplace.dto;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MyRentalDetailDTO {
	
	private int rental_num;
	private Date regdate;
	private String rental_name;
	private String rental_phone;
	private String rental_email;
	private String rental_title;
	private String hall_name;
	private Date rental_date;
	private String rental_time;
	private String rental_genre;
	private String rental_savefilename;
	private String rental_originfilename;
	private String rental_filesize;
	private String rental_ownsname;
	private String rental_ownsphone;
	private String rental_ownemail;
	private String rental_content;
	private String rental_state;
	private String answer_content;
}
