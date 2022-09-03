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

import com.moaplace.dto.GradePriceDTO;
import com.moaplace.dto.HallSeatDTO;
import com.moaplace.dto.TicketGradeDTO;
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
	public List<String> getBookingSeat(
			@PathVariable Integer schedule_num)
	{
		List<String> seats = service.getBookingSeat(schedule_num);
		
		return seats;
	}
	
	@GetMapping
	(value = "/getHallInfo/{hall_num}/{show_num}",
	produces = {MediaType.APPLICATION_JSON_VALUE})
	public HashMap<String, Object> getHallInfo(
			@PathVariable Integer hall_num,
			@PathVariable Integer show_num)
	{
		HashMap<String, Object> hallInfo = new HashMap<String, Object>();
		
		//좌석 수 
		HallSeatDTO seats = service.getHallSeats(hall_num);
		hallInfo.put("seats", seats);
		
		//등급
		List<TicketGradeDTO> grades = service.getTicketGrade(hall_num);
		hallInfo.put("grades", grades);
		
		//가격
		List<GradePriceDTO> prices = service.getGradePrice(show_num);
		hallInfo.put("prices", prices);
		
		return hallInfo;
	}
	
}
