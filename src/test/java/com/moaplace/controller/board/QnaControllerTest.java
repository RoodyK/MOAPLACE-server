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
public class QnaControllerTest {
	@Autowired private QnaService qnaService;
	@Autowired private AnswerService answerService;
	
//	@Test
//	public void insert() {
//		int n = qnaService.insert(new QnaVO(0,2,3,null,"문의사항 제목","문의사항에 대한 내용~~",null,null));
//		assertEquals(n, 1);
//	}
//	
//	@Test
//	public void detail() {
//		QnaVO qDto = qnaService.detail(21);
//		log.info(qDto);
//		
//		QnaAnswerDTO aDto = answerService.select(qDto.getQna_num());
//		log.info(aDto);
//		assertEquals(null, aDto);
//	}
//	
//	@Test
//	public void update() {
//		int n = qnaService.update(new QnaVO(63,0,6,null,"수정 테스트 제목","수정 테스트 내요오오오옹옹옹",null,null));
//		assertEquals(n, 1);
//	}
	
	@Test
	public void delete() {
		int n = qnaService.delete(64);
		assertEquals(n, 1);
	}
}
