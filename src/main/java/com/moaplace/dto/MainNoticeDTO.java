package com.moaplace.dto;

import lombok.Data;

@Data
//메인페이지 새소식 DTO
public class MainNoticeDTO {
	
	private int notice_num;
	private String notice_title;
	private String notice_content;
	private String notice_regdate;
	private String yearMonth;
	private String day;
}
