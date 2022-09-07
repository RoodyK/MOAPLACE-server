package com.moaplace.controller.show;

import java.util.ArrayList;
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
	
	@GetMapping(value = {"/{pagenum}/{start_date}/{end_date}/{hall_chk}/{genre_chk}",
			"/{pagenum}/{start_date}/{end_date}/{hall_chk}/{genre_chk}/{keyword}"},
			produces = MediaType.APPLICATION_JSON_VALUE)
	public HashMap<String, Object> list(
			@PathVariable Integer pagenum,
			@PathVariable String start_date,
			@PathVariable String end_date,
			@PathVariable ArrayList<String> hall_chk,
			@PathVariable ArrayList<String> genre_chk,
			@PathVariable(required=false) String keyword){
		
		if(pagenum == null) pagenum=1;
		if(hall_chk.contains("all")) {
			hall_chk.remove("all");
			hall_chk.add("1");
			hall_chk.add("2");
			hall_chk.add("3");
		}
		if(genre_chk.contains("all")) {
			genre_chk.remove("all");
			genre_chk.add("1");
			genre_chk.add("2");
			genre_chk.add("3");
			genre_chk.add("4");
			genre_chk.add("5");
			genre_chk.add("6");
			genre_chk.add("7");
		}
		
		HashMap<String, Object> map1=new HashMap<String, Object>();
		map1.put("start_date", start_date);
		map1.put("end_date", end_date);
		map1.put("hall_chk", hall_chk);
		map1.put("genre_chk", genre_chk);
		map1.put("keyword", keyword);
		
		int totalRowCount=service.count(map1);
		PageUtil pu=new PageUtil(pagenum,8, 5, totalRowCount);
		int startRow=pu.getStartRow();//시작행번호
		int endRow=pu.getEndRow();//끝행번호
		
		HashMap<String, Object> map2=new HashMap<String, Object>();
		map2.put("start_date", start_date);
		map2.put("end_date", end_date);
		map2.put("startRow", startRow);
		map2.put("endRow", endRow);
		map2.put("hall_chk", hall_chk);
		map2.put("genre_chk", genre_chk);
		map2.put("keyword", keyword);
		
		List<ShowDTO> list = service.list(map2);
		
		HashMap<String, Object> data = new HashMap<String, Object>();
		data.put("list", list);
		data.put("pageUtil", pu);
		
		return data;
	}
}
