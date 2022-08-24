package com.moaplace.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.dbcp2.BasicDataSource;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.moaplace.dto.MyBookingDTO;
import com.moaplace.service.BookingService;

import lombok.extern.log4j.Log4j;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/root-context.xml")
@Log4j
public class BookingTest {
	
	@Autowired
	private BasicDataSource dataSource;
	@Autowired
	private BookingService service;
	
	@Test
	public void test() {		
		if(dataSource != null) {
			log.info("널 아님");
		}
	}
	
	@Test
	public void bookingExist() {
		boolean exist = service.bookingExist(1);
		log.info("bookingExist : " + exist);
	}
	
	@Test
	public void bookingCount() {
		int n = service.bookingCount(1);
		log.info("bookingCount : " + n);
	}
	
	@Test
	public void recentBooking() {
		MyBookingDTO dto = service.recentBooking(1);
		log.info("recentBooking : " + dto);
		
		String img = new String(dto.getShow_thumbnail());
		log.info("img : " + img);
	}
	
	@Test
	public void list() {
		
		SimpleDateFormat dtFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date startdate = null;
		Date enddate = null;
		
		try {
			
			startdate = dtFormat.parse("2022-08-17");
			log.info("startdate : " + startdate);
			enddate = dtFormat.parse("2022-08-24");
			log.info("enddate : " + enddate);
			
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		HashMap<String, Object> map = new HashMap<String, Object>();
		
		map.put("member_num", 1);
		map.put("startdate", startdate);
		map.put("enddate", enddate);
		map.put("startRow", 1);
		map.put("endRow", 3);
		
		List<MyBookingDTO> list = service.list(map);
		
		log.info("list : " + list);
	}
	
	@Test
	public void listCount() {
		
		SimpleDateFormat dtFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date startdate = null;
		Date enddate = null;
		
		try {
			
			startdate = dtFormat.parse("2022-08-17");
			log.info("startdate : " + startdate);
			enddate = dtFormat.parse("2022-08-24");
			log.info("enddate : " + enddate);
			
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		HashMap<String, Object> map = new HashMap<String, Object>();
		
		map.put("member_num", 1);
		map.put("startdate", startdate);
		map.put("enddate", enddate);
		
		int n = service.listCount(map);
		
		log.info("listCount : " + n);
	}
}
