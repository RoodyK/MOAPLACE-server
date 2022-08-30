package com.moaplace.controller.booking;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.moaplace.service.BookingService;

import lombok.extern.log4j.Log4j;

@CrossOrigin("*")
@RestController
@RequestMapping("/booking")
@Log4j
public class BookingController {
	
	@Autowired
	private BookingService service;
	
	@GetMapping
	(value = "/getBookingSeat/{schedule_num}",
	produces = {MediaType.APPLICATION_JSON_VALUE})
	public HashMap<String, Object> getBookingSeat(
			@PathVariable Integer schedule_num)
	{
		List<String> seats = service.getBookingSeat(schedule_num);
		
		HashMap<String, Object> result = new HashMap<String, Object>();
		
		if(seats.size() > 0) {
			result.put("result", "success");
			result.put("list", seats);	
		}else {
			result.put("result", "fail");
		}
		
		return result;
	}
	
}
