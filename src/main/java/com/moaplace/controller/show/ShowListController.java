package com.moaplace.controller.show;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.moaplace.dto.ShowDTO;
import com.moaplace.service.ShowService;
import com.moaplace.util.PageUtil;

@RestController
@RequestMapping("/preview")
@CrossOrigin("*")
public class ShowListController {
	
	@Autowired 
	private ShowService service;
	
	@GetMapping(value = "/{pagenum}/{start_date}/{end_date}", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<ShowDTO> list(
			@PathVariable(required = true) Integer pagenum,
			@PathVariable(required = true) String start_date,
			@PathVariable(required = true) String end_date){
		
		if(pagenum == null) pagenum=1;
		
		HashMap<String, Object> map=new HashMap<String, Object>();
		
		int totalRowCount=service.count();
		PageUtil pu=new PageUtil(pagenum,8, 5, totalRowCount);
		int startRow=pu.getStartRow();//시작행번호
		int endRow=pu.getEndRow();//끝행번호
		
		map.put("start_date", start_date);
		map.put("end_date", end_date);
		map.put("startRow", startRow);
		map.put("endRow", endRow);
		
		return service.list(map);
	}
}
