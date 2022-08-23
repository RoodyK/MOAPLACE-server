package com.moaplace.mapper;

import com.moaplace.dto.QnaAnswerDTO;

public interface AnswerMapper {

	QnaAnswerDTO select(int qna_num);
	int delete(int qna_num);
	
}
