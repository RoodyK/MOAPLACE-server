package com.moaplace.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.moaplace.mapper.QnaMapper;
import com.moaplace.vo.QnaVO;

@Service
public class QnaService {

	@Autowired private QnaMapper mapper;
	
	public int insert(QnaVO vo){
		return mapper.insert(vo);
	}
	
}
