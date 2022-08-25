package com.moaplace.controller;


import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.moaplace.dto.admin.show.MapperDetailDTO;
import com.moaplace.dto.admin.show.ShowDetailViewDTO;
import com.moaplace.dto.admin.show.ShowInsertRequestDTO;
import com.moaplace.dto.admin.show.ShowListDTO;
import com.moaplace.service.ShowService;
import com.moaplace.util.PageUtil;

import lombok.extern.log4j.Log4j;

@CrossOrigin("*")
@RestController
@RequestMapping("/admin/show")
@Log4j
public class AdminShowController {
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
	@GetMapping(value = "/list/{pageNum}/{status}/{field}/{search}")
	public HashMap<String, Object> list(
			@PathVariable( "pageNum" ) int pageNum,
			@PathVariable( "status" ) String status,
			@PathVariable( "field" ) String field,
			@PathVariable( "search" ) String search){
		
		
		log.info("페이지 요청 들어옴");
		
		if(pageNum==0) { //페이지값이 넘어오지 않았을 때 기본 1로 고정
			pageNum=1;
		}
		
		PageUtil pu = new PageUtil(pageNum,5,5,service.countRow());
		
		HashMap<String, Object> sList = new HashMap<String, Object>();
		
			sList.put( "startRow", pu.getStartRow());
			sList.put( "endRow", pu.getEndRow());
			sList.put( "showCheck", status);
			sList.put( "field", field);
			sList.put( "search", search);

		List<ShowListDTO> list=service.showList(sList);
		
		HashMap<String, Object> map=new HashMap<String, Object>();
		
			map.put( "list", list);
			map.put( "pageNum", pageNum);
			map.put( "pageInfo", pu);
			
		return map;
	}
	
	@GetMapping( value = "/detail/{showNum}/{field}/{search}")
	public HashMap<String, Object> showDetail(
			@PathVariable("showNum") int showNum){
		
		
		ShowDetailViewDTO dto= service.showDetail(showNum);
		HashMap<String, Object> map=new HashMap<String, Object>();
			map.put("list",dto);
		return map;
	}
	
}
