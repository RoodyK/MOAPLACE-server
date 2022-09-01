package com.moaplace.controller.rental;

import java.util.Date;
import java.util.HashMap;
import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import com.moaplace.dto.RentalCalendarDTO;
import com.moaplace.service.RentalService;
import com.moaplace.util.FileUtil;
import com.moaplace.util.PageUtil;
import com.moaplace.vo.RentalVO;

import lombok.extern.log4j.Log4j;

@CrossOrigin("*")
@RestController
@RequestMapping("/admin/rental")
@Log4j
public class RentalController {
	@Autowired
	private RentalService service;
	@Autowired
	private FileUtil fileutil;
	
	@GetMapping
	(value = "/detail/{num}",
	produces = {MediaType.APPLICATION_JSON_VALUE})
	public HashMap<String, Object> detail(
			@PathVariable Integer num)
	{
		log.info("num:"+num);
		
		RentalVO vo = service.detail(num);
		
		HashMap<String, Object> map = new HashMap<String, Object>();
		
		if(vo != null) {
			map.put("result","success");
			map.put("vo", vo);
		}
		else {
			map.put("result","fail");
		}
		log.info("map" + map);
		return map;
	}
	
	@GetMapping
	(value = "/calendar/{year}/{month}/{endOfDay}",
	produces= {MediaType.APPLICATION_JSON_VALUE})
	public HashMap<String, Object> calendar(
			@PathVariable String year,
			@PathVariable String month,
			@PathVariable String endOfDay)
	{
		
		String startDate = year + "-" + month + "-" + 1;
		String endDate = year + "-" + month + "-" + endOfDay;
		
		HashMap<String, String> map = new HashMap<String, String>();
		map.put( "startDate" , startDate );
		map.put( "endDate" , endDate );
		
		
		List<RentalCalendarDTO> list = service.getSchedules(map);
		
		HashMap<String, Object> data = new HashMap<String, Object>();
		data.put("year" , year);
		data.put("month" , month);
		data.put("schedule" , list);
		
		return data;
	}
	
	// 진행상태, 대관희망시간, 대관일자 수정
	@GetMapping
	(value = "/update/{column}/{num}/{state}")
	public String updateState(
			@PathVariable String column, //업데이트할 컬럼명
			@PathVariable Integer num,//대관신청번호
			@PathVariable String state)//진행상태
	{
		
		switch (column) {
			case "state" : column = "rental_state"; break;
			case "date" : column = "rental_date"; break;
			case "time" : column = "rental_time"; break;
		}
		
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("column", column);
		map.put("rental_num", num);
		map.put("state", state);
		
		int n = service.updateState(map);
		
		if( n < 0 ) return "fail";
		
		return "success";
	}
	
	
	@GetMapping
	(value= {"/list","/list/{pagenum}","/list/{sort}/{keyword}","/list/{pagenum}/{sort}/{keyword}"},
	produces= {MediaType.APPLICATION_JSON_VALUE})
	public HashMap<String, Object> list(
			@PathVariable(required=false) Integer pagenum,
			@PathVariable(required=false) String sort,
			@PathVariable(required=false) String keyword)
	{
		log.info("pagenum : " + pagenum);
		log.info("sort : " + sort);
		log.info("keyword : " + keyword);
		
		if(pagenum == null) {
			pagenum = 1;
		}
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("pagenum" , pagenum);
		map.put("sort" , sort);
		map.put("keyword" , keyword);
		int totalRowCount = service.getCount(map);
		
		PageUtil pageutil = new PageUtil(pagenum, 5, 5, totalRowCount);
		int startRow = pageutil.getStartRow();
		int endRow = pageutil.getEndRow();
		map.put("startRow", startRow);
		map.put("endRow", endRow);
		List<RentalVO> list = service.list(map);
		
		HashMap<String, Object> data = new HashMap<String, Object>();
		data.put("list", list);
		data.put("pageUtil", pageutil);
		data.put("sort", sort);
		data.put("keyword", keyword);
		
		return data;
	}
	
}
