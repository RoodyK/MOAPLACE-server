package com.moaplace.mapper;

import com.moaplace.dto.QnaAnswerDTO;
import com.moaplace.vo.AnswerVO;

public interface AnswerMapper {

	QnaAnswerDTO select(int qna_num);
	AnswerVO selectAdmin(int qna_num);
	int delete(int qna_num);
	int update(AnswerVO vo);
	int insert(AnswerVO vo);
	
}
