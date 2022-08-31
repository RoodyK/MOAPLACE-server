package com.moaplace.dto.member;

import lombok.Data;

@Data
public class ApiLoginDTO {

	private String member_id;
	private String authority;
	private String member_email;
}
