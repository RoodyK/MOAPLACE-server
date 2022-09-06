package com.moaplace.controller.rental;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.moaplace.dto.RentalCalendarDTO;
import com.moaplace.service.RentalService;
import com.moaplace.vo.RentalVO;

import lombok.extern.log4j.Log4j;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"file:src/main/webapp/WEB-INF/spring/root-context.xml", "file:src/main/webapp/WEB-INF/spring/appServlet/servlet-context.xml"})
@Log4j
@WebAppConfiguration
public class RentalControllerTest {
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
	
	
	@Test
	public void updateStateTest() {
		HashMap<String, Object> data = new HashMap<String, Object>();
		data.put("rental_num", 5);
		data.put("rental_state", "사용완료");
		
		int n = service.updateState(data);
		assertEquals(1, n);
	}
	
	@Test
	public void calendarTest() {
		HashMap<String, String> map = new HashMap<String, String>();
		map.put( "startDate" , "2022-08-01" );
		map.put( "endDate" , "2022-08-31" );
		
		List<RentalCalendarDTO> list = service.getSchedules(map);
		
		log.info(list);
		assertNotNull(list);
	}
}
