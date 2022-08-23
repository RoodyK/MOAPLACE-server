package com.moaplace.service;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.moaplace.dto.QnaListDTO;
import com.moaplace.mapper.QnaMapper;
import com.moaplace.vo.QnaVO;

@Service
public class QnaService {

	@Autowired private QnaMapper mapper;
	
	public int insert(QnaVO vo){
		return mapper.insert(vo);
	}	
	public List<QnaListDTO> list(HashMap<String, Object> map){
		return mapper.list(map);
	}	
	public int listCnt(HashMap<String, Object> map) {
		return mapper.listCnt(map);
	}
	public QnaVO detail(int qna_num) {
		return mapper.detail(qna_num);
	}
	public int update(QnaVO vo) {
		return mapper.update(vo);
	}
	public int delete(int qna_num) {
		return mapper.delete(qna_num);
	}
}
