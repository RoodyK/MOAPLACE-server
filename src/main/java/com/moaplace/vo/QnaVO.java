package com.moaplace.vo;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class QnaVO {
	
	private int qna_num;
	private int member_num;
	private int sort_num;
	private String qna_title;
	private String qna_content;
	private Date qna_regdate;
	private String qna_state;
	
}
