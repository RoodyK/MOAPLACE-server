package com.moaplace.dto;

import java.util.Date;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class AdminNoticeDTO {
	private int notice_num;
	private int member_num;
	private int sort_num;
	private String notice_title;
	private String notice_content;
	private Date regdate;
	private int notice_hit;
}
