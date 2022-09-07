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
import com.moaplace.dto.admin.show.ScheduleUpdateRequestDTO;
import com.moaplace.service.AdminTicketService;
import com.moaplace.util.PageUtil;

import lombok.extern.log4j.Log4j;


@CrossOrigin("*")
@RequestMapping("/admin/show/schedule")
@RestController
@Log4j
public class AdminScheduleController {
	@Autowired
	private AdminTicketService service;
	
	//일정 등록할 때 일정을 등록할 공연을 검색해서 리스트 받아오기
	@GetMapping( 
			value = {"/viewshow/", "/viewshow/{showTitle}"},
			produces = MediaType.APPLICATION_JSON_VALUE)
	public HashMap<String, Object> viewShowData(@PathVariable (required = false) String showTitle) {
		
		log.info(showTitle);
		HashMap<String, Object> map = new HashMap<String, Object>();
		
		map.put("showList",service.searchShow(showTitle));
		
		return map;
	}
	
	//일정 등록
	@PostMapping(
			value = "/insert", 
			consumes= {MediaType.APPLICATION_JSON_VALUE})
	
	public int insertSchedule(
			@RequestBody ScheduleInsertRequestDTO dto) {
		
		return service.insertSchedule(dto);
	}
	
	//일정 리스트 데이터 전송
	@GetMapping(
			value = {"/list",
					"/list/{pageNum}",
					"/list//{status}",
					"/list/{pageNum}/{status}",
					"/list/{pageNum}/{status}//{field}",
					"/list/{pageNum}/{status}/{selectDate}/{field}",
					"/list/{pageNum}/{status}/{selectDate}/{field}/{search}",
					"/list/{pageNum}/{status}//{field}/{search}"},
			produces = MediaType.APPLICATION_JSON_VALUE)
	
	public HashMap<String, Object> list(
			@PathVariable ( required = false ) Integer pageNum,
			@PathVariable ( required = false ) String status,
			@PathVariable ( required = false ) String selectDate,
			@PathVariable ( required = false ) String field,
			@PathVariable ( required = false ) String search){		

		//페이지번호, 공연상태, 필드가 null값일 때(페이지에 처음 가거나 새로고침했을때) 기본값으로 넣기
		if(pageNum==null)pageNum=1;
		if(status==null)status="all";
		if(field==null)field="title";
		
		// 페이징처리하는 클래스 유틸 받아서 페이징 데이터 생성
		PageUtil pu = new PageUtil(pageNum,5,5,service.sheduleCount());
		
		 //db로 조회할 정보를 해시맵에 담기 (시작/끝행,검색조건)
		HashMap<String, Object> sList = new HashMap<String, Object>();
		sList.put( "startRow", pu.getStartRow());
		sList.put( "endRow", pu.getEndRow());
		sList.put( "showCheck", status);
		sList.put("selectDate", selectDate);
		sList.put( "field", field);
		sList.put( "search", search);
		
		// 공연일정 조회해서 받아올 DTO 새로 만들어서 매퍼 리턴 정보 리스트에 담기
		List<ScheduleListDTO> list=service.sheduleList(sList);
				
		// 검색조건이나 모아보기가 선택됐을 때 다시 조회된 행 번호 카운트해서 페이징처리 유틸에 새로 덮어씌우기 
		if(search!=null || !status.equals("all") || selectDate!=null) {
			pu = new PageUtil(
					pageNum,5,5,service.currentCount(sList));
		};
		
		// 해시맵에 조회된 공연목록, 페이지번호, 페이징유틸 정보 담아서 클라이언트로 보내기
		// 해시맵을 produces = MediaType.APPLICATION_JSON_VALUE 으로 제이슨형태로 변환해서 전송
		if(search==null)search="";
		if(selectDate==null)selectDate="";
		HashMap<String, Object> map=new HashMap<String, Object>();
		map.put( "list",list);
		map.put( "pageNum", pageNum);
		map.put( "pageInfo", pu);
		map.put( "status", status);
		map.put( "selectDate", selectDate);
		map.put( "selectField", field);
		map.put( "search", search);
		return map;
	}
	
	//공연일정상세페이지 조회 - 해당 일자 데이터 전부 뿌려주기
	@GetMapping( 
			value = {"/detail", 
					"/detail/{showNum}",
					"/detail/{showNum}/{showDate}"}, 
			produces = MediaType.APPLICATION_JSON_VALUE)

	public HashMap<String, Object> showDetail(
			@PathVariable ( required = false ) int showNum,
			@PathVariable ( required = false ) String showDate){
		
		HashMap<String, Object> map=service.showDetail(showNum, showDate);
		
		return map;
		
	}
		
	//공연일정 수정전 데이터 보여주기
	@GetMapping( 
			value = {"/updateView", 
					"/updateView/{showNum}",
					"/updateView/{showNum}/{showDate}"}, 
			produces = MediaType.APPLICATION_JSON_VALUE)
	
	public HashMap<String, Object> viewSchedule(
			@PathVariable ( required = false ) int showNum,
			@PathVariable ( required = false ) String showDate){
		
		HashMap<String, Object> map=service.selectUpdate(showNum, showDate);
		
		return map;
	}
		
	//공연일정 수정
	@PostMapping(
			value = "/update", 
			consumes= {MediaType.APPLICATION_JSON_VALUE})
	
	public int showUpdate(@RequestBody ScheduleUpdateRequestDTO dto) {
		
		int result = service.scheduleUpdate(dto);
		
		return result;
	}
	
	//공연일정 삭제	
	@GetMapping
		( value = "/delete/{showNum}/{scheduleDate}")
	public int deleteSchedule(
			@PathVariable int showNum,
			@PathVariable String scheduleDate) {
		
		return service.deleteSchedule(showNum, scheduleDate);
	}
}
