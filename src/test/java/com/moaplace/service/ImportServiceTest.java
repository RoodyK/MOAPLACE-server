package com.moaplace.service;

import static org.junit.Assert.assertTrue; 

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.moaplace.dto.BookingCancleDTO;

import lombok.extern.log4j.Log4j;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({
	"file:src/main/webapp/WEB-INF/spring/root-context.xml",
	"file:src/main/webapp/WEB-INF/spring/mail-context.xml"
})
@Log4j
public class ImportServiceTest {
	
	@Autowired
	private ImportService importService;
	
//	@Test
//	public void getToken() {
//		try {
//			
//			String token = importService.getToken();
//			log.info("아임포트 토큰: " + token);
//			
//		} catch (Exception e) {
//			e.getMessage();
//		}
//	}
	
//	@Test
//	public void cancle() {
//		try {
//		
//			BookingCancleDTO dto = new BookingCancleDTO("imp_935850897886", "관리자 테스트 취소", 1000, 1);
//			Boolean response = importService.cancle(importService.getToken(), dto.getImp_uid(), dto.getReason(), dto.getAmount());
//			log.info("결제취소 테스트 결과: " + response);
//			assertTrue(response);
//			
//		} catch (Exception e) {
//			e.getMessage();
//		}
//	}
}