package com.moaplace.service;


import java.util.HashMap;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.moaplace.dto.admin.show.ShowListDTO;
import com.moaplace.mapper.ShowMapper;

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
	
	@Autowired
	private ShowMapper mapper;

	@Autowired
	private ShowImgService show_img_service;
	
	@Autowired
	private FavoriteService favorite_service;
	
	@Autowired
	private ScheduleService schedule_service;
	
	@Autowired
	private ReviewService review_service;
	
	@Value("${oracle.download}")
	private String savePath;
	
	@Test
	public void test() {
		
		HashMap<String, Object> sList = new HashMap<String, Object>();
		
		sList.put( "startRow", 1);
		sList.put( "endRow", 5);
		sList.put( "showCheck", "Y");
		sList.put( "field", "hall");
		sList.put( "search", "모던홀");
		
		List<ShowListDTO> list=mapper.showList(sList);
		log.info(list);
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
	
//	@Test
//	public void test4() {
//		int show_num=1;
//		ShowDetailDTO list = service.detail(show_num);
//		log.info(list);
//	}
	
//	@Test
//	public void test3() {
//		int pagenum=1;
//		String start_date = "2022-01-01";
//		String end_date = "2022-12-31";
//		ArrayList<String> hall_chk=new ArrayList<String>();
//		hall_chk.add("1");
//		hall_chk.add("3");
//		log.info(hall_chk);
//		
//		ArrayList<String> genre_chk=new ArrayList<String>();
//		genre_chk.add("1");
//		genre_chk.add("2");
//		log.info(genre_chk);
//		
//		HashMap<String, Object> map1=new HashMap<String, Object>();
//		map1.put("start_date", start_date);
//		map1.put("end_date", end_date);
//		
//		int totalRowCount=service.count(map1);
//		log.info(totalRowCount);
//		log.info(map1);
//		
//		HashMap<String, Object> map2=new HashMap<String, Object>();
//		
//		PageUtil pu=new PageUtil(pagenum,8, 5, totalRowCount);
//		log.info(pu);
//		int startRow=pu.getStartRow();//시작행번호
//		int endRow=pu.getEndRow();//끝행번호
//		
//		map2.put("start_date", start_date);
//		map2.put("end_date", end_date);
//		map2.put("startRow", startRow);
//		map2.put("endRow", endRow);
//		map2.put("hall_chk", hall_chk);
//		
//		log.info(map2);
//		
//		List<ShowDTO> list = service.list(map2);
//		log.info(list);
//
//	}
	
//	@Test
//	public void test5() {
//		int show_num=1;
//		List<ShowImgDTO> list = show_img_service.detailimg(show_num);
//		log.info(list);
//	}
	
//	@Test
//	public void test6() {
//		int show_num=2;
//		int member_num=2;
//		FavoriteVO vo = new FavoriteVO(show_num, member_num);
//		log.info(favorite_service.insert(vo));
//	}
//	
//	@Test
//	public void test7() {
//		int show_num=2;
//		int member_num=2;
//		FavoriteVO vo = new FavoriteVO(show_num, member_num);
//		log.info(favorite_mapper.insert(vo));
//	}
	
	@Test
	public void test8() {
		int show_num=1;
		log.info(schedule_service.selectlist(show_num));
	}
	
//	@Test
//	public void test9() {
//		HashMap<String, Object> map = new HashMap<String, Object>();
//		map.put("show_num", 1);
//		map.put("schedule_num", 3);
////		int show_num=1;
////		int schedule_num=1;
//		log.info(all_seat_service.seatcount(map));
//	}
	
	@Test
	public void test10() {
		int show_num=1;
		log.info(schedule_service.rowinfo(show_num));
	}
	
//	@Test
//	public void test11() {
//		int show_num=1;
//		int schedule_num=1;
//		
//		HashMap<String, Object> map=new HashMap<String, Object>();
//		map.put("show_num", show_num);
//		map.put("schedule_num", schedule_num);
//		
//		log.info(schedule_service.seatcnt(map));
//	}
	
	
	@Test
	public void test12() {
		int show_num=1;
		log.info(review_service.reviewCount(show_num));
	}
}
