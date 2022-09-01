package com.moaplace.controller.admin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.moaplace.dto.AdminChartDTO;
import com.moaplace.service.BookingService;
import com.moaplace.service.RentalService;

import lombok.extern.log4j.Log4j;

@RestController
@CrossOrigin("*")
@RequestMapping("/admin")
@Log4j
public class AdminChartController {

	@Autowired
	private BookingService bookingService;
	@Autowired
	private RentalService rentalService;
	
	@GetMapping(value = "/rental/chart/{start}/{end}")
	public ResponseEntity<List<AdminChartDTO>> rentalChart(
			@PathVariable String start, @PathVariable String end) {
		log.info("start : " + start + ", end : " + end);
		
		Map<String, Object> map = new HashMap<>();
		map.put("startDate", start);
		map.put("endDate", end);
		
		List<AdminChartDTO> list = rentalService.rentalChart(map);
		
		return ResponseEntity.ok().body(list);
	}
	
	@GetMapping(value = "/show/chart/{start}/{end}")
	public ResponseEntity<List<AdminChartDTO>> bookingChart(
			@PathVariable String start, @PathVariable String end) {
		log.info("start : " + start + ", end : " + end);
		
		Map<String, Object> map = new HashMap<>();
		map.put("startDate", start);
		map.put("endDate", end);
		
		List<AdminChartDTO> list = bookingService.bookingChart(map);
		
		return ResponseEntity.ok().body(list);
	}
}
