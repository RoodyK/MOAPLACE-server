package com.moaplace.controller.show;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.moaplace.dto.MainShowDTO;
import com.moaplace.service.MainService;

import lombok.extern.log4j.Log4j;


@CrossOrigin("*")
@RestController
@RequestMapping("/main")
@Log4j
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
}
