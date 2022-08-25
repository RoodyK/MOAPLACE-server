package com.moaplace.dto;

import lombok.Data;

@Data
public class MemberLoginRequestDTO {

	private String member_id;
	private String member_pwd;
}
