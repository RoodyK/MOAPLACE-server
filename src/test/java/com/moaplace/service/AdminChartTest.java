package com.moaplace.service;

import static org.junit.Assert.assertNotNull;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.moaplace.dto.AdminChartDTO;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({
	"file:src/main/webapp/WEB-INF/spring/root-context.xml",
	"file:src/main/webapp/WEB-INF/spring/mail-context.xml"
})
public class AdminChartTest {

	@Autowired
	private BookingService bookingService;
	@Autowired
	private RentalService rentalService;
	
	@Test
	public void RentalChartTest() {
		Map<String, Object> map = new HashMap<>();
		map.put("startDate", "2022-01-01");
		map.put("endDate", "2022-11-01");
		
		List<AdminChartDTO> list = rentalService.rentalChart(map);
		
		assertNotNull(list);
	}
	
	@Test
	public void bookingChartTest() {
		Map<String, Object> map = new HashMap<>();
		map.put("startDate", "2022-01-01");
		map.put("endDate", "2022-11-01");
		
		List<AdminChartDTO> list = bookingService.bookingChart(map);
		
		assertNotNull(list);
	}
}
