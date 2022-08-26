package com.moaplace.vo;

import java.sql.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class QnaVO {
	
	private int qna_num;
	private int member_num;
	private int sort_num;
	private String sort_name;
	private String qna_title;
	private String qna_content;
	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd")
	private Date qna_regdate;
	private String qna_state;
	
}
