package com.moaplace.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.moaplace.mapper.RentalMapper;
import com.moaplace.vo.RentalVO;

@Service
public class RentalService {
	@Autowired
	private RentalMapper mapper;
	
	public int insert(RentalVO vo) {
		return mapper.insert(vo);
	}
	
	public RentalVO select() {
		return mapper.select();
	}
}
