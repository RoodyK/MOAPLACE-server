package com.moaplace.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
//파일 있을 때 list 
public class AdminDetailDTO {
	private int notice_num;
	private String sort_name;
	private String notice_title;
	private String notice_content;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	private Date notice_regdate;
	private int notice_hit;
	private int notice_detail_num;
	private String notice_orgfile;
	private String notice_savefile;
	private long notice_filesize;

}
