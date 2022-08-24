package com.moaplace.vo;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MemberVO {
	
	private int member_num;
	private String member_id;
	private String member_pwd;
	private String member_email;
	private String member_name;
	private String member_gender;
	private String member_birth;
	private String member_phone;
	private String member_address;
	private Date regdate;
	private int member_point;
	private String authority;
	private int enabled;
}
