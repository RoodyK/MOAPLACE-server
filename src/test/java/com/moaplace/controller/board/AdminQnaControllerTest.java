package com.moaplace.controller.board;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.moaplace.service.AnswerService;
import com.moaplace.service.QnaService;

import lombok.extern.log4j.Log4j;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"file:src/main/webapp/WEB-INF/spring/appServlet/servlet-context.xml",
					   "file:src/main/webapp/WEB-INF/spring/root-context.xml"})
@Log4j
public class AdminQnaControllerTest {
	
	@Autowired private QnaService qnaService;
	@Autowired private AnswerService answerService;
	
//	@Test
//	public void insert() {
//		int n = answerService.insert(new AnswerVO(0,65,"답변테스트 제목","답변 테스트 관련 내용~~",null));
//		assertEquals(n, 1);
//	}
//	
//	@Test
//	public void update() {
//		int n = answerService.update(new AnswerVO(4,0,"수정 답변테스트 제목","수정 답변 테스트 관련 내용~~",null));
//		assertEquals(n, 1);
//	}
	
	@Test
	public void delete() {
		int n = answerService.delete(3);
		assertEquals(n, 1);
	}	

//	@Test
//	public void changeState() {
//		
//		HashMap<String, Object> map = new HashMap<String, Object>();
//		map.put("qna_state", "처리중");
//		map.put("qna_num", 89);
//		log.info(map);
//		
//		int n = qnaService.changeState(map);
//		assertEquals(n, 1);
//	}
	
}
