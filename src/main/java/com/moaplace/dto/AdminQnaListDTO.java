package com.moaplace.dto;

import java.sql.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
public class AdminQnaListDTO {
	
	private int rnum;
	private int sort_num;
	private String sort_name;
	private String member_id;
	private String member_name;
	private int qna_num;
	private String qna_title;
	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd")
	private Date qna_regdate;
	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd")
	private Date answer_regdate;
	private String qna_state;

}
