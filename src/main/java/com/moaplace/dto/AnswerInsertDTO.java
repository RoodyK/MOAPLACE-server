package com.moaplace.dto;

import lombok.Data;

@Data
public class AnswerInsertDTO {

	private int qna_num;
	private String answer_title;
	private String answer_content;
	private String email;

}