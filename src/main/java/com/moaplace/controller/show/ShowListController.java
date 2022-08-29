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
import com.moaplace.vo.RentalVO;

@RestController
@RequestMapping("/preview")
@CrossOrigin("*")
public class ShowListController {
	
	@Autowired 
	private ShowService service;
	
	@GetMapping(value = "/{pagenum}/{start_date}/{end_date}", produces = MediaType.APPLICATION_JSON_VALUE)
	public HashMap<String, Object> list(
			@PathVariable Integer pagenum,
			@PathVariable String start_date,
			@PathVariable String end_date){
		
		if(pagenum == null) pagenum=1;
		
		HashMap<String, Object> map1=new HashMap<String, Object>();
		map1.put("start_date", start_date);
		map1.put("end_date", end_date);
		
		int totalRowCount=service.count(map1);
		
		HashMap<String, Object> map2=new HashMap<String, Object>();
		
		PageUtil pu=new PageUtil(pagenum,8, 5, totalRowCount);
		int startRow=pu.getStartRow();//시작행번호
		int endRow=pu.getEndRow();//끝행번호
		
		map2.put("start_date", start_date);
		map2.put("end_date", end_date);
		map2.put("startRow", startRow);
		map2.put("endRow", endRow);
		
		List<ShowDTO> list = service.list(map2);
		
		HashMap<String, Object> data = new HashMap<String, Object>();
		data.put("list", list);
		data.put("pageUtil", pu);
		
		return data;
	}
}
