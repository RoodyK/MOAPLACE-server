package com.moaplace.dto.member;

import lombok.Data;

@Data
public class MemberLoginRequestDTO {

	private String member_id;
	private String member_pwd;
}
