package com.moaplace.service;


import java.sql.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.moaplace.dto.ShowDTO;
import com.moaplace.util.PageUtil;

import lombok.extern.log4j.Log4j;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({
	"file:src/main/webapp/WEB-INF/spring/root-context.xml",
	"file:src/main/webapp/WEB-INF/spring/mail-context.xml"
})
@Log4j
public class ShowServiceTest {

	@Autowired
  private ShowService service;
	@Value("${oracle.download}")
	private String savePath;
	
	@Test
	public void test() {
//		List<ShowVO> list=service.selectName();
//		log.info(list);
//		log.info(savePath);
	}

	
//	@Test
//	public void test1() {
//		
//		HashMap<String, Object> map=new HashMap<String, Object>();
//		
//		int totalRowCount=service.count();
//		PageUtil sp=new PageUtil(1,8, 5, totalRowCount);
//		int startRow=sp.getStartRow();//시작행번호
//		int endRow=sp.getEndRow();//끝행번호
//		
//		log.info(startRow);
//		log.info(endRow);
//		
//		map.put("startRow", startRow);
//		map.put("endRow", endRow);
//		
//		if(service.list(map)!=null) {
//			log.info("확인");
//			log.info(service.list(map));
//		}
//		List<showDTO> test=service.list();
//		for(showDTO dto : test) {
//			log.info(dto);
//		}
//	}
	
//	@Test
//	public void test2() {
//		if(service.count()>=0) {
//			log.info("확인");
//			log.info(service.count());
//		}
//	}
	
	@Test
	public void test3() {
		int pagenum=1;
		String start_date = "2022-01-01";
		String end_date = "2022-12-31";
		ArrayList<String> hall_chk=new ArrayList<String>();
		hall_chk.add("1");
		hall_chk.add("3");
		log.info(hall_chk);
		
		ArrayList<String> genre_chk=new ArrayList<String>();
		genre_chk.add("1");
		genre_chk.add("2");
		log.info(genre_chk);
		
		HashMap<String, Object> map1=new HashMap<String, Object>();
		map1.put("start_date", start_date);
		map1.put("end_date", end_date);
		
		int totalRowCount=service.count(map1);
		log.info(totalRowCount);
		log.info(map1);
		
		HashMap<String, Object> map2=new HashMap<String, Object>();
		
		PageUtil pu=new PageUtil(pagenum,8, 5, totalRowCount);
		log.info(pu);
		int startRow=pu.getStartRow();//시작행번호
		int endRow=pu.getEndRow();//끝행번호
		
		map2.put("start_date", start_date);
		map2.put("end_date", end_date);
		map2.put("startRow", startRow);
		map2.put("endRow", endRow);
		map2.put("hall_chk", hall_chk);
		
		log.info(map2);
		
		List<ShowDTO> list = service.list(map2);
		log.info(list);

	}
}
