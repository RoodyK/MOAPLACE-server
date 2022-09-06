package com.moaplace.controller.main;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.moaplace.dto.MainNoticeDTO;
import com.moaplace.dto.MainShowDTO;
import com.moaplace.service.MainService;


@CrossOrigin("*")
@RestController
@RequestMapping("/main")
public class MainController {
	
	@Autowired
	private MainService service;
	
	@GetMapping
	(value = "/getRunningShow",
	produces = {MediaType.APPLICATION_JSON_VALUE})
	public HashMap<String, Object> getRunningShow()
	{
		List<MainShowDTO> list = service.getRunningShow();
		
		HashMap<String, Object> map = new HashMap<String, Object>();
		
		if( list.size() > 0 ) {
			map.put("result", "success");
			map.put("list", list);
		}else {
			map.put("result", "fail");
		}
		
		return map;
	}
	
	@GetMapping
	(value = "/getNotice",
	produces = {MediaType.APPLICATION_JSON_VALUE})
	public HashMap<String, Object> getNotice()
	{
		List<MainNoticeDTO> list = service.getNotice();
		
		HashMap<String, Object> map = new HashMap<String, Object>();
		
		if( list.size() > 0 ) {
			map.put("result", "success");
			
			//메인 페이지에서 사용할 형태로 가공
			for(MainNoticeDTO dto : list) 
			{
				String[] regdate = dto.getNotice_regdate().split("-");
				
				//2022.08(년도 + "." + 달)
				String yearMonth = regdate[0] + "." + regdate[1];
				dto.setYearMonth(yearMonth);
				
				//30(일)
				String day = regdate[2].substring(0,2);
				dto.setDay(day);
				
				//html 태그 지우기
				String content = dto.getNotice_content().replaceAll("<(/)?([a-zA-Z]*)(\\s[a-zA-Z]*=[^>]*)?(\\s)*(/)?>", "");
				dto.setNotice_content(content);
			}
			
			map.put("list", list);
			
		}else {
			map.put("result", "fail");
		}
		
		return map;
	}
}
