package com.moaplace.dto;

import java.sql.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
public class QnaListDTO {
	
	private int rnum;
	private int qna_num;
	private String sort_name;
	private String qna_title;
	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd")
	private Date qna_regdate;
	private String qna_state;
	
}
