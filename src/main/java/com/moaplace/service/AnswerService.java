package com.moaplace.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.moaplace.dto.QnaAnswerDTO;
import com.moaplace.mapper.AnswerMapper;

@Service
public class AnswerService {

	@Autowired private AnswerMapper mapper;
	
	public QnaAnswerDTO select(int qna_num){
		return mapper.select(qna_num);
	}
	public int delete(int qna_num) {
		return mapper.delete(qna_num);
	}	
}
