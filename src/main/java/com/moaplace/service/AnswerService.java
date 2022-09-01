package com.moaplace.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.moaplace.dto.AnswerInsertDTO;
import com.moaplace.dto.QnaAnswerDTO;
import com.moaplace.mapper.AnswerMapper;
import com.moaplace.vo.AnswerVO;

@Service
public class AnswerService {

	@Autowired 
	private AnswerMapper mapper;
	
	public QnaAnswerDTO select(int qna_num){
		return mapper.select(qna_num);
	}
	
	public AnswerVO selectAdmin(int qna_num){
		return mapper.selectAdmin(qna_num);
	}
	
	public int delete(int qna_num) {
		return mapper.delete(qna_num);
	}
	
	public int update(AnswerVO vo) {
		return mapper.update(vo);
	}
	
	public int insert(AnswerInsertDTO dto) {
		return mapper.insert(dto);
	}
	
}
