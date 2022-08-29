package com.moaplace.controller.board;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.moaplace.controller.admin.AdminFaqController;
import com.moaplace.vo.FaqVO;

import lombok.extern.log4j.Log4j;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"file:src/main/webapp/WEB-INF/spring/appServlet/servlet-context.xml",
					   "file:src/main/webapp/WEB-INF/spring/root-context.xml",
					   "file:src/main/webapp/WEB-INF/spring/mail-context.xml"})
@Log4j
public class AdminFaqControllerTest {

	@Autowired
	private AdminFaqController controller;
	
//	@Test
//	public void insert() {
//		
//		FaqVO vo = new FaqVO(0,27,2,"대관","자주 묻는 질문 테스트 제목","자주 묻는 질문 테스트 관련 내용~~");
//		String result = controller.insert(vo);
//		assertEquals(result, "success");
//		
//	}
	
	@Test
	public void update() {
		String result = controller.update(new FaqVO(2,0,2,null,"자주 묻는 질문 수정","수정 테스트 내요오옹"));
		assertEquals(result, "success");
	}
	
//	@Test
//	public void delete() {
//		String result = controller.delete(22);
//		assertEquals(result, "success");
//	}	
//	
}
