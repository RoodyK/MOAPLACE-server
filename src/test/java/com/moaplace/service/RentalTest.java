package com.moaplace.service;

import org.apache.commons.dbcp2.BasicDataSource;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.moaplace.dto.MyRentalDTO;
import com.moaplace.service.RentalService;

import lombok.extern.log4j.Log4j;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/root-context.xml")
@Log4j
public class RentalTest {

	@Autowired
	private BasicDataSource dataSource;
	@Autowired
	private RentalService service;
	
	@Test
	public void test() {		
		if(dataSource != null) {
			log.info("널 아님");
		}
	}
	
	@Test
	public void rentalExist() {
		boolean exist = service.rentalExist(1);
		log.info("rentalExist : " + exist);
	}
	
	@Test
	public void recentRental() {
		MyRentalDTO dto = service.recentRental(1);
		log.info("recentRental : " + dto);
	}
}
