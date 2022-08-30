package com.moaplace.controller.admin;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.moaplace.dto.admin.show.ScheduleInsertRequestDTO;
import com.moaplace.dto.admin.show.ScheduleListDTO;
import com.moaplace.service.AdminTicketService;
import com.moaplace.util.PageUtil;
import lombok.extern.log4j.Log4j;


@CrossOrigin("*")
@RequestMapping("/admin/show/schedule")
@Log4j
@RestController
public class AdminScheduleController {
	@Autowired
	private AdminTicketService service;

	@GetMapping( 
			value = {"/viewshow", "/viewshow/{showTitle}"},
			produces = MediaType.APPLICATION_JSON_VALUE)
	public HashMap<String, Object> viewShowData(@PathVariable String showTitle) {
		
		
		HashMap<String, Object> map = new HashMap<String, Object>();
		
		map.put("showList",service.searchShow(showTitle));
		
		return map;
	}
	
	@PostMapping(
			value = "/insert", 
			consumes= {MediaType.APPLICATION_JSON_VALUE})
	public int insertSchedule(
			@RequestBody ScheduleInsertRequestDTO dto) {
		
		return service.insertSchedule(dto);
	}
	@GetMapping(
			value = {"/list",
					"/list/{pageNum}"},
			produces = MediaType.APPLICATION_JSON_VALUE)
	
	public HashMap<String, Object> list(
			@PathVariable ( required = false ) Integer pageNum){		
		
		//페이지번호가 null값일 때(페이지에 처음 가거나 새로고침했을때) 기본값으로 넣기
		if(pageNum==null)pageNum=1;
		
		// 페이징처리하는 클래스 유틸 받아서 페이징 데이터 생성
		PageUtil pu = new PageUtil(pageNum,5,5,service.sheduleCount());
		
		 //db로 조회할 정보를 해시맵에 담기 (시작/끝행,검색조건)
		HashMap<String, Object> sList = new HashMap<String, Object>();
		sList.put( "startRow", pu.getStartRow());
		sList.put( "endRow", pu.getEndRow());
//		sList.put( "showCheck", status);
//		sList.put( "field", field);
//		sList.put( "search", search);
//
		// 공연목록 조회해서 받아올 DTO 새로 만들어서 매퍼 리턴 정보 리스트에 담기
		List<ScheduleListDTO> list=service.sheduleList(sList);
//		
//		// 검색어가 있고, 공연상태별 모아보기가 선택됐을 때 다시 조회된 행 번호 카운트해서 페이징처리 유틸에 새로 덮어씌우기 
//		if(search!=null || !status.equals("all")) {
//			pu = new PageUtil(
//					pageNum,5,5,service.currentListRow(sList));
//		};
		
		log.info(pu.getEndPageNum());
		
		// 해시맵에 조회된 공연목록, 페이지번호, 페이징유틸 정보 담아서 클라이언트로 보내기
		// 해시맵을 produces = MediaType.APPLICATION_JSON_VALUE 으로 제이슨형태로 변환해서 전송
//		if(search==null)search="";
		HashMap<String, Object> map=new HashMap<String, Object>();
		map.put( "list",list);
		map.put( "pageNum", pageNum);
		map.put( "startPageNum", pu.getStartPageNum());
		map.put( "endPageNum", pu.getEndPageNum());
		map.put( "totalPageCount", pu.getTotalPageCount());
//		map.put("status", status);
//		map.put("selectField", field);
//		map.put("search", search);
		
		return map;
	}
}
