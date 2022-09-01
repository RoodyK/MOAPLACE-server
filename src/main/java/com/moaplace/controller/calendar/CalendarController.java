package com.moaplace.controller.calendar;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.moaplace.service.ShowCalendarService;
import com.moaplace.service.ShowService;

import lombok.extern.log4j.Log4j;
@Log4j
@CrossOrigin("*")
@RestController
@RequestMapping("/show")
public class CalendarController {
	
	@Autowired private ShowCalendarService service;
	
	@GetMapping(
			value = {
				"/calendar",
				"/calendar/{month}"},
			produces = MediaType.APPLICATION_JSON_VALUE)
	
	public HashMap<String, Object> showCalendar(
			@PathVariable ( required = false ) int month) {
		
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("list",service.viewCalendar(month));
			
			log.info(service.viewCalendar(month));
		return map;
	}
	
	@GetMapping( 
			value = {
				"/calendar/thumbnail",
				"/calendar/thumbnail/{num}"} )
	public String showImg(
			@PathVariable ( required = false) int num) {
		
		return service.returnThumbnail(num);
	}
}
