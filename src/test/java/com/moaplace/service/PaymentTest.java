package com.moaplace.service;

import static org.junit.Assert.assertEquals;

import org.apache.commons.dbcp2.BasicDataSource;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


import lombok.extern.log4j.Log4j;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/root-context.xml")
@Log4j
public class PaymentTest {
	
	@Autowired
	private BasicDataSource dataSource;
	@Autowired
	private PaymentService service;
	
	@Test
	public void test() {		
		if(dataSource != null) {
			log.info("널 아님");
		}
	}
	
	@Test
	public void ticketCancle() {
		int n = service.ticketCancle(1);
		assertEquals(n, 1);
	}
}
