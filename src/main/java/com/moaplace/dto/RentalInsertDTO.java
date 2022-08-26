package com.moaplace.dto;


import java.sql.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
public class RentalInsertDTO {
	private int hall_num;
	private int member_num;
    private String rental_name; 
    private String rental_phone; 
    private String rental_email;
    private String rental_title;
    private String rental_genre;
    private Date rental_date;
    private String rental_time;
    private String rental_ownsname;
    private String rental_ownsphone;
    private String rental_ownemail;
    private String rental_content;
}
