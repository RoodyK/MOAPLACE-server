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

import com.moaplace.dto.BookingShowDTO;
import com.moaplace.dto.GradePriceDTO;
import com.moaplace.dto.HallSeatDTO;
import com.moaplace.dto.MyBookingDetailDTO;
import com.moaplace.dto.TicketGradeDTO;
import com.moaplace.service.BookingService;
import com.moaplace.service.ScheduleService;
import com.moaplace.service.ShowService;

import lombok.extern.log4j.Log4j;

@CrossOrigin("*")
@RestController
@RequestMapping("/booking")
@Log4j
public class BookingController {
	
	@Autowired
	private BookingService service;
	@Autowired
	private ScheduleService schedule_service;
	@Autowired
	private ShowService show_service;
	
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
	(value = "/getHallInfo/{show_num}",
	produces = {MediaType.APPLICATION_JSON_VALUE})
	public HashMap<String, Object> getHallInfo(
			@PathVariable Integer show_num)
	{
		BookingShowDTO dto = service.getBookingShow(show_num);
		
		HashMap<String, Object> hallInfo = new HashMap<String, Object>();
		
		//공연명
		hallInfo.put("show_name", dto.getShow_name());
		
		//공연장명
		String hall_name = service.getHallName(dto.getHall_num());
		hallInfo.put("hall_name", hall_name);
		
		//좌석 수 (cols, rows)
		HallSeatDTO seats = service.getHallSeats(dto.getHall_num());
		hallInfo.put("seats", seats);
		
		//등급별 행수
		List<TicketGradeDTO> gradeSeats = service.getTicketGrade(dto.getHall_num());
		hallInfo.put("gradeSeats", gradeSeats);
		
		//가격
		List<GradePriceDTO> gradePrice = service.getGradePrice(show_num);
		hallInfo.put("gradePrice", gradePrice);
		
		return hallInfo;
	}
	
	@GetMapping
	(value = "/done/{booking_num}",
	produces = {MediaType.APPLICATION_JSON_VALUE})
	public MyBookingDetailDTO doneInfo(
			@PathVariable Integer booking_num)
	{
		
		return service.detail(booking_num);
	}
	
	@GetMapping
	(value = "/done/{show_num}/{schedule_date}/{schedule_time}",
	produces = {MediaType.APPLICATION_JSON_VALUE})
	public HashMap<String, Object> rounds(
			@PathVariable Integer show_num,
			@PathVariable String schedule_date,
			@PathVariable String schedule_time)
	{
		
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("show_num", show_num);
		map.put("schedule_date", schedule_date);
		map.put("schedule_time", schedule_time);
		
		int Rounds = schedule_service.Rounds(map);
		
		String returnThumb = show_service.returnThumb(show_num);
		
		HashMap<String, Object> data = new HashMap<String, Object>();
		data.put("Rounds", Rounds);
		data.put("returnThumb", returnThumb);
		
		return data;
	}
	
}
