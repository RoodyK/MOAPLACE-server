package com.moaplace.service;

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
public class MemberTest {
	
	@Autowired
	private BasicDataSource dataSource;
	@Autowired
	private MemberService service;
	
	@Test
	public void test() {		
		if(dataSource != null) {
			log.info("널 아님");
		}
	}
	
//	@Test
//	public void findLoginUser() {
//		MemberVO vo = service.findLoginUser(1);
//		log.info("조회결과 : " + vo);
//	}
}
