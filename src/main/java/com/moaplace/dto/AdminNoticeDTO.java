package com.moaplace.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

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
	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd")
	private Date notice_regdate;
	private int notice_hit;
}
