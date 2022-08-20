package com.moaplace.vo;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MemberVO {
	
	private int memberNum;
	private String memberId;
	private String memberEmail;
	private String memberName;
	private String memberGender;
	private String memberBirth;
	private String memberPhone;
	private String memberAddress;
	private Date regdate;
	private String memberPoint;
	private String authority;
	private String enabled;
}
