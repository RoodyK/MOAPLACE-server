package com.moaplace.controller.admin;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.moaplace.dto.admin.show.ScheduleInsertRequestDTO;
import com.moaplace.service.AdminTicketService;
import com.moaplace.service.ShowService;

import lombok.extern.log4j.Log4j;


@CrossOrigin("*")
@RequestMapping("/admin/show/schedule")
@Log4j
@RestController
public class AdminScheduleController {
	@Autowired
	private AdminTicketService service;

	@GetMapping( 
			value = {"/viewshow", "/viewshow/{showTitle}"},
			produces = MediaType.APPLICATION_JSON_VALUE)
	public HashMap<String, Object> viewShowData(@PathVariable String showTitle) {
		
		
		HashMap<String, Object> map = new HashMap<String, Object>();
		
		map.put("showList",service.searchShow(showTitle));
		
		return map;
	}
	
	@PostMapping(
			value = "/insert", 
			consumes= {MediaType.APPLICATION_JSON_VALUE})
	public int insertSchedule(
			@RequestBody ScheduleInsertRequestDTO dto) {
		
		return service.insertSchedule(dto);
	}
}
