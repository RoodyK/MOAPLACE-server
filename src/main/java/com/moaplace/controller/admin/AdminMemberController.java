package com.moaplace.controller.admin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.moaplace.dto.member.AdminMemberInfoResponseDTO;
import com.moaplace.service.MemberService;
import com.moaplace.util.PageUtil;

import lombok.extern.log4j.Log4j;

@RestController
@CrossOrigin("*")
@RequestMapping("/admin")
@Log4j
public class AdminMemberController {

	@Autowired
	private MemberService memberService;
	
	// 회원목록 출력
	@GetMapping(value = {
			"/member/info/{page}/{sorted}",
			"/member/info/{page}/{sorted}/{field}/{word}" }
	, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Map<String ,Object>> allMemberInfo(
			@PathVariable(required = false) Integer page, 
			@PathVariable(required = false) String sorted,
			@PathVariable(required = false) String field,
			@PathVariable(required = false) String word) {
		
		if(page == null) page = 1;

		log.info("field : " + field);
		log.info("word : " + word);
		Map<String, Object> map = new HashMap<>();
		map.put("sorted", sorted);
		map.put("field", field);
		map.put("word", word);
		
		int totalCount = memberService.getCount(map);
		PageUtil pu = new PageUtil(page, 5, 5, totalCount);
		int startRow = pu.getStartRow();
		int endRow = pu.getEndRow();
		
		map.put("startRow", startRow);
		map.put("endRow", endRow);
		
		Map<String, Object> response = new HashMap<>();
		
		List<AdminMemberInfoResponseDTO> list = memberService.selectAll(map);
		log.info("selectAll : " + list);
		
		response.put("list", list);
		response.put("pu", pu);
		response.put("field", field);
		response.put("word", word);
		response.put("sorted", sorted);
		
		return ResponseEntity.ok().body(response);
	}
}
