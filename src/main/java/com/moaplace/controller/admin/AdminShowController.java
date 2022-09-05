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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.moaplace.dto.admin.show.ShowDetailViewDTO;
import com.moaplace.dto.admin.show.ShowInsertRequestDTO;
import com.moaplace.dto.admin.show.ShowListDTO;
import com.moaplace.dto.admin.show.ShowUpdateDTO;
import com.moaplace.service.ShowService;
import com.moaplace.util.PageUtil;

import lombok.extern.log4j.Log4j;

@CrossOrigin("*")
@RestController
@RequestMapping("/admin/show")
@Log4j
public class AdminShowController {
	
	@Autowired 
	private ShowService service;
	
	// 포스트로 보내온 등록 데이터 제이슨으로 받아서 DB에 저장
	@PostMapping(
			value = "/insert", 
			consumes= {MediaType.APPLICATION_JSON_VALUE})
	
	public HashMap<String, Object> showInsert(
			@RequestBody ShowInsertRequestDTO dto) {		
		//@RequestBody로 insert요청 들어온 데이터 전부 DTO로 받아서 insert 서비스로 보내기
		int result= service.showInsert(dto);
		
		//해시맵에 데이터 담아서 결과값을 클라이언트로 보냄
		HashMap<String, Object> map = new HashMap<String, Object>();
		
		
		//결과값에 따라 데이터 다르게 보내기 
		if(result>4) { //데이터 등록하면 최소 5개 이상이 등록되므로(공연정보 1개, 좌석RSA 각각 3개, 상세정보이미지 1개)
			map.put("result", true);
			map.put("pageNum",dto.getPageNum());
			map.put("status",dto.getStatus());
			map.put("selectField",dto.getField());
			map.put("search",dto.getSearch());
		}else {
			map.put("result", false);
		}
		
		return map;
		
	}
	
	// 게시판 목록, 검색 데이터 전부 클라이언트에 제이슨으로 전송
	@GetMapping(
			value = {
					"/list/{pageNum}/{status}/{field}/{search}", 
					"/list/{pageNum}/{status}/{field}",
					"/list/{status}/{field}",
					"/list"},
			produces = MediaType.APPLICATION_JSON_VALUE)
	
	public HashMap<String, Object> list(
			@PathVariable ( required = false ) Integer pageNum,
			@PathVariable ( required = false ) String status,
			@PathVariable ( required = false ) String field,
			@PathVariable ( required = false ) String search){
		
		//페이지번호, 공연상태, 필드가 null값일 때(페이지에 처음 가거나 새로고침했을때) 기본값으로 넣기
		if(pageNum==null)pageNum=1;
		if(status==null)status="all";
		if(field==null)field="title";
		
		
		// 페이징처리하는 클래스 유틸 받아서 페이징 데이터 생성
		PageUtil pu = new PageUtil(pageNum,5,5,service.countRow());
		
		// db로 조회할 정보를 해시맵에 담기 (시작/끝행,검색조건)
		HashMap<String, Object> sList = new HashMap<String, Object>();
		sList.put( "startRow", pu.getStartRow());
		sList.put( "endRow", pu.getEndRow());
		sList.put( "showCheck", status);
		sList.put( "field", field);
		sList.put( "search", search);

		// 공연목록 조회해서 받아올 DTO 새로 만들어서 매퍼 리턴 정보 리스트에 담기
		List<ShowListDTO> list=service.showList(sList);
		
		// 검색어가 있고, 공연상태별 모아보기가 선택됐을 때 다시 조회된 행 번호 카운트해서 페이징처리 유틸에 새로 덮어씌우기 
		if(search!=null || !status.equals("all")) {
			pu = new PageUtil(
					pageNum,5,5,service.currentCnt(sList));
					
		};
		
		// 해시맵에 조회된 공연목록, 페이지번호, 페이징유틸 정보 담아서 클라이언트로 보내기
		// 해시맵을 produces = MediaType.APPLICATION_JSON_VALUE 으로 제이슨형태로 변환해서 전송
		if(search==null)search="";
		HashMap<String, Object> map=new HashMap<String, Object>();
		map.put( "list", list);
		map.put( "pageNum", pageNum);
		map.put( "pageInfo", pu);
		map.put("status", status);
		map.put("selectField", field);
		map.put("search", search);
		
		return map;
	}
	
	//공연상세페이지 조회 - 공연번호 받아옴
	
	@GetMapping( 
			value = {"/detail", 
					"/detail/{showNum}",
					"/detail/{showNum}/{pageNum}",
					"/detail/{showNum}/{pageNum}/{status}",
					"/detail/{showNum}/{pageNum}/{status}/{selectField}",
					"/detail/{showNum}/{pageNum}/{status}/{selectField}/{search}", 
			}, 
			produces = MediaType.APPLICATION_JSON_VALUE)

	public HashMap<String, Object> showDetail(
			@PathVariable ( required = false ) int showNum,
			@PathVariable ( required = false ) int pageNum,
			@PathVariable ( required = false ) String status,
			@PathVariable ( required = false ) String selectField,
			@PathVariable ( required = false ) String search
			){
		
		ShowDetailViewDTO dto= service.showDetail(showNum);
		
		HashMap<String, Object> map=new HashMap<String, Object>();
		map.put("list",dto);
		map.put("pageNum",pageNum);
		map.put("status",status);
		map.put("selectField",selectField);
		map.put("search",search);

		return map;
		
	}
	
	// 공연목록 수정
	@PostMapping(
			value = "/update", 
			consumes= {MediaType.APPLICATION_JSON_VALUE})
	public HashMap<String, Object> showUpdate(@RequestBody ShowUpdateDTO dto) {
		log.info("데이터 받았음");
		
		int result = service.showUpdate(dto);
		
		//해시맵에 데이터 담아서 결과값을 클라이언트로 보냄
		HashMap<String, Object> map = new HashMap<String, Object>();
		
		
		//결과값에 따라 데이터 다르게 보내기 
		if(result>1) {
			map.put("result", true);
		}else {
			map.put("result", false);
		}
		
		return map;
	}
	
	@GetMapping
	( value = "/delete/{showNum}" )
	public int deleteShow(
			@PathVariable Integer showNum) {		
		
		return service.deleteShow(showNum);
	}
	
}
