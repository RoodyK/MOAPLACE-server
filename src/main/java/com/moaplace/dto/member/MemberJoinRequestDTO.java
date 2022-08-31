package com.moaplace.dto.member;

import lombok.Data;

@Data
public class MemberJoinRequestDTO {

	private int member_num;
	private String member_id;
	private String member_pwd;
	private String member_email;
	private String member_name;
	private String member_gender;
	private String member_birth;
	private String member_phone;
	private String member_address;
	private String api;
}
