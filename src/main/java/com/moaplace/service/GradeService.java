package com.moaplace.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.moaplace.dto.GradeDTO;
import com.moaplace.mapper.GradeMapper;

import lombok.extern.log4j.Log4j;

@Service
@Log4j
public class GradeService {
	
	@Autowired 
	private GradeMapper gradeMapper;
	
	public List<GradeDTO> grade(int show_num) {
		
		return gradeMapper.grade(show_num);
	}
}
