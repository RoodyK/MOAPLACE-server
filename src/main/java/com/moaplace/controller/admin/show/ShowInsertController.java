package com.moaplace.controller.admin.show;


import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.moaplace.dto.admin.show.ShowInsertRequestDTO;
import com.moaplace.service.ShowService;

import lombok.extern.log4j.Log4j;

@CrossOrigin("*")
@RestController
@RequestMapping("/admin/show")
@Log4j
public class ShowInsertController {
	@Autowired private ShowService service;
	
	
	@PostMapping(value = "/insertShow", consumes= {MediaType.APPLICATION_JSON_VALUE})
	public HashMap<String, Object> insert(@RequestBody ShowInsertRequestDTO dto) {		
		
		//@RequestBody로 insert요청 들어온 데이터 전부 DTO로 받아서 insert 서비스로 보내기
		int result= service.showInsert(dto);
		log.info(result);
		
		HashMap<String, Object> map = new HashMap<String, Object>();
		
		//결과값에 따라 데이터 다르게 보내기 
		if(result>4) {
			map.put("result", true);
		}else {
			map.put("result", false);
		}
		
		return map;
		
	}
}
