package com.moaplace.dto;

import java.sql.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
public class MemberInfoResponseDTO {
	
	private int member_num;
	private String member_id;
	private String member_email;
	private String member_name;
	private String member_gender;
	private String membeR_birth;
	private String member_phone;
	private String member_address;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	private Date regDate;
	private int member_point;
}
