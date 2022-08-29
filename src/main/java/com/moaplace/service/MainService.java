package com.moaplace.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.moaplace.dto.MainShowDTO;
import com.moaplace.mapper.ShowMapper;

@Service
public class MainService {
	
	@Autowired
	private ShowMapper mapper;
	
	public List<MainShowDTO> getRunningShow(){
		
		return mapper.getRunningShow();
		
	}
}
