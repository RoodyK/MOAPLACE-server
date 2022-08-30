package com.moaplace.service;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.moaplace.dto.MainNoticeDTO;

import lombok.extern.log4j.Log4j;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"file:src/main/webapp/WEB-INF/spring/root-context.xml", "file:src/main/webapp/WEB-INF/spring/mail-context.xml"})
@Log4j
@WebAppConfiguration
public class MainServiceTest {
	
	@Autowired
	private MainService service;
	
	@Test
	public void getNoticeTest() {
		
		List<MainNoticeDTO> list = service.getNotice();
		
		if(list != null) {
			log.info(list);
		}
	}
}
