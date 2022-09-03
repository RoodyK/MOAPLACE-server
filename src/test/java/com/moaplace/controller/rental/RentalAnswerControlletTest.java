package com.moaplace.controller.rental;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.moaplace.service.RentalService;
import com.moaplace.vo.RentalAnswerVO;

import lombok.extern.log4j.Log4j;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"file:src/main/webapp/WEB-INF/spring/root-context.xml", "file:src/main/webapp/WEB-INF/spring/mail-context.xml"})
@Log4j
@WebAppConfiguration
public class RentalAnswerControlletTest {

	@Autowired
	RentalService service;
	
	@Test
	public void getAnswerTest() {
		RentalAnswerVO vo = service.getAnswer(31);
		log.info("vo : " + vo);
	}
	
	@Test
	public void deleteTest() {
		int n = service.answerDelete(34);
		assertEquals(1, n);
	}
}
