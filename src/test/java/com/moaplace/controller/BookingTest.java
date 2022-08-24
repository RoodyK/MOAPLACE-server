package com.moaplace.controller;

import java.sql.Blob;
import java.util.Map;

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
	public void recentBooking() {
		MyBookingDTO dto = service.recentBooking(1);
		log.info("recentBooking : " + dto);
		
		String img = new String(dto.getShow_thumbnail());
		log.info("img : " + img);
	}
	
}
