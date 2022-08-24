package com.moaplace.service;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.moaplace.vo.ShowVO;

import lombok.extern.log4j.Log4j;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/root-context.xml")
@Log4j
public class ShowServiceTest {
	@Autowired private ShowService service;
	@Value("${oracle.download}")
	private String savePath;
	
	@Test
	public void test() {
//		List<ShowVO> list=service.selectName();
//		log.info(list);
//		log.info(savePath);
	}

	
}
