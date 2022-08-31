package com.moaplace.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApiAuthVO {

	private int api_num;
	private int member_num;
	private String member_email;
	private String api_name;
}
