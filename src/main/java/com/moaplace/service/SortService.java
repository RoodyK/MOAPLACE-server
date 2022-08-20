package com.moaplace.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.moaplace.mapper.SortMapper;
import com.moaplace.vo.SortVO;

@Service
public class SortService {

	@Autowired private SortMapper mapper;
	
	public List<SortVO> select(){
		return mapper.select();
	}
	
}
