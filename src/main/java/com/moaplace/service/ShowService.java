package com.moaplace.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.moaplace.dto.showDTO;
import com.moaplace.mapper.ShowMapper;
import com.moaplace.vo.ShowVO;

import lombok.extern.log4j.Log4j;

@Log4j
@Service
public class ShowService {
	
	@Autowired private ShowMapper dao;
	
	public List<showDTO> list(HashMap<String, Object> map) {

		
		List<ShowVO> list = dao.list(map);
		
		List<showDTO> list2 = new ArrayList<>();
		
		for(ShowVO vo : list) {
			log.info(vo.getShow_thumbnail());
			list2.add(new showDTO(vo.getShow_num(), vo.getGenre_num(), vo.getHall_num(), vo.getShow_name(), vo.getShow_start(), vo.getShow_end(), vo.getShow_check(), new String(vo.getShow_thumbnail())));
		}
		
		return list2;
	}
	
	public int count() {
		return dao.count();
	}
}
