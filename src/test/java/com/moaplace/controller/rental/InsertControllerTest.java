package com.moaplace.controller.rental;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Date;

import org.apache.commons.dbcp2.BasicDataSource;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.moaplace.service.RentalService;
import com.moaplace.vo.RentalVO;

import lombok.extern.log4j.Log4j;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"file:src/main/webapp/WEB-INF/spring/root-context.xml", "file:src/main/webapp/WEB-INF/spring/appServlet/servlet-context.xml"})
@Log4j
@WebAppConfiguration
public class InsertControllerTest {
	@Autowired
	private RentalService service;
	
	@Test
	public void controllerTest() {
		RentalVO vo = new RentalVO(1, 1, 1, "rental_name", "010-0000-0000", "rental_email", "rental_title", 
				"연극", null, "rental_time", "rental_savefilename", "rental_originfilename", 100, 
				"rental_ownsname", "010-0000-0000", "rental_ownemail", "rental_content", null, "tmp");
		Date date = new Date();
		long timeInMilliSeconds = date.getTime();
		vo.setRental_date(new java.sql.Date(timeInMilliSeconds));
		int n = service.insert(vo);
		assertEquals(1,n);
	}
	
}
