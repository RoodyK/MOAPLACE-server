package com.moaplace.controller.admin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.moaplace.dto.FaqListDTO;
import com.moaplace.service.FaqService;
import com.moaplace.util.PageUtil;
import com.moaplace.vo.FaqVO;

import lombok.extern.log4j.Log4j;

@CrossOrigin("*")
@RestController
@RequestMapping("/admin/faq")
@Log4j
public class AdminFaqController {

	@Autowired
	private FaqService faqService;
	
	// faq 수정
	@PostMapping(value = "/update",
				 consumes = MediaType.APPLICATION_JSON_VALUE)
	public String update(@RequestBody FaqVO vo){		
		try {			
			
			log.info(vo);
			faqService.update(vo);
			
			return "success";
			
		} catch (Exception e) {
			log.info(e.getMessage());
			return "fail";		
		}
	}
	
	// faq 상세조회
	@GetMapping(value="/detail/{faq_num}",
				produces = MediaType.APPLICATION_JSON_VALUE)
	public Map<String, Object> detail(@PathVariable int faq_num){	
		try {			
			
			log.info(faq_num);						
			FaqVO detail = faqService.detail(faq_num);
			
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("detail", detail);		
			log.info(map);
			
			return map;
			
		} catch (Exception e) {
			log.info(e.getMessage());
			return null;
		}	
	}	
	
	// faq 삭제
	@PostMapping(value= "/delete/{faq_num}")
	public String delete(@PathVariable int faq_num) {		
		try {			
			
			log.info(faq_num);
			faqService.delete(faq_num);
			
			return "success";
			
		} catch (Exception e) {
			log.info(e.getMessage());
			return "fail";		
		}
	}	
		
	// 리스트 조회 (페이지 + 검색)
	@GetMapping(value = "/list",
				produces = MediaType.APPLICATION_JSON_VALUE)
	public Map<String, Object> list(@RequestParam(value="pageNum", defaultValue="1") int pageNum,
									@RequestParam(value="field", required=false) String field, 
									@RequestParam(value="keyword", required=false) String keyword,
									@RequestParam(value="sort_num", defaultValue="0") int sort_num){
		try {
			
			HashMap<String, Object> map = new HashMap<String, Object>();

			// 전체 검색일 때 (검색필드 없이 키워드만 있을 때)
			if(StringUtils.isEmpty(field) && !StringUtils.isEmpty(keyword)) {
				field = "all";
			}
			
			// 검색 필드와 키워드, 구분번호 추가
			map.put("field", field);
			map.put("keyword", keyword);
			map.put("sort_num", sort_num);
			log.info(map);
			
			int totalRowCount = faqService.listCnt(map); // 전체 결과 개수 구하기
			PageUtil util = new PageUtil(pageNum, 5, 5, totalRowCount);
			map.put("startRow", util.getStartRow()); // 시작행 번호
			map.put("endRow", util.getEndRow()); // 끝행 번호
			
			List<FaqListDTO> list = faqService.list(map); // 리스트 불러오기
			map.put("list", list);  			
			map.put("pageNum", pageNum); // 페이지 번호
			map.put("startPage", util.getStartPageNum()); // 페이지 시작번호
			map.put("endPage", util.getEndPageNum()); // 페이지 마지막번호
			map.put("pageCnt", util.getTotalPageCount()); // 전체 페이지수
	
			return map;
						
		} catch (Exception e) {
			log.info(e.getMessage());
			return null;
		}
	}
	
	// faq 등록
	@PostMapping(value= "/insert",
				 consumes = MediaType.APPLICATION_JSON_VALUE)
	public String insert(@RequestBody FaqVO vo) {		
		try {			
			
			log.info(vo);
			faqService.insert(vo);
			
			return "success";
			
		} catch (Exception e) {
			log.info(e.getMessage());
			return "fail";		
		}
	}	
}
