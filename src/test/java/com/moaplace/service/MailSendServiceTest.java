package com.moaplace.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import lombok.extern.log4j.Log4j;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({
	"file:src/main/webapp/WEB-INF/spring/root-context.xml",
	"file:src/main/webapp/WEB-INF/spring/mail-context.xml"
})
@Log4j
public class MailSendServiceTest {

	@Autowired
	private MailSendService service;
	
	@Test
	public void test() {
		
		String email = "pps8853@naver.com";
		
		String code = service.joinEmail(email);
		
		if(code != null) {
			log.info(code);
		}
	}
	
}
