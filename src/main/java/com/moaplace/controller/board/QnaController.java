package com.moaplace.controller.board;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.moaplace.dto.QnaAnswerDTO;
import com.moaplace.dto.QnaListDTO;
import com.moaplace.service.AnswerService;
import com.moaplace.service.QnaService;
import com.moaplace.util.PageUtil;
import com.moaplace.vo.QnaVO;

import lombok.extern.log4j.Log4j;

@CrossOrigin("*")
@RestController
@RequestMapping("/board/qna")
@Log4j
public class QnaController {

	@Autowired 
	private QnaService qnaService;
	@Autowired 
	private AnswerService answerService;
	
	// 문의글 삭제
	@Transactional(rollbackFor = Exception.class)
	@PostMapping(value="/delete/{qna_num}")
	public String delete(@PathVariable int qna_num){
	
		try {
			log.info(qna_num);						
			QnaAnswerDTO answer = answerService.select(qna_num);
			
			if(answer!=null) { // 답변이 있으면 먼저 삭제
				answerService.delete(qna_num);
			}
			qnaService.delete(qna_num);
			
			return "success";

		} catch (Exception e) {
			log.info(e.getMessage());
			return "fail";		
		}	
	}

	// 문의글 수정
	@PostMapping(value = "/update",
				 consumes = MediaType.APPLICATION_JSON_VALUE,
				 produces = MediaType.APPLICATION_JSON_VALUE)
	public String update(@RequestBody QnaVO vo){
		try {
			log.info(vo);
			qnaService.update(vo);
			return "success";
			
		} catch (Exception e) {
			log.info(e.getMessage());
			return "fail";		
		}
	}
	
	// 수정폼 상세글 조회
	@GetMapping(value="/update",
				produces = MediaType.APPLICATION_JSON_VALUE)
	public QnaVO updateForm(@RequestParam int qna_num){
		try {
			log.info(qna_num);
			QnaVO detail = qnaService.detail(qna_num);
			return detail;
			
		} catch (Exception e) {
			log.info(e.getMessage());
			return null;
		}	
	}
	
	// 상세글 및 답변 조회
	@GetMapping(value="/detail",
				produces = MediaType.APPLICATION_JSON_VALUE)
	public Map<String, Object> detail(@RequestParam int qna_num){
		try {
			log.info(qna_num);
						
			QnaVO detail = qnaService.detail(qna_num);
			QnaAnswerDTO answer = answerService.select(qna_num);
			
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("detail", detail);
			map.put("answer", answer);			
			return map;
			
		} catch (Exception e) {
			log.info(e.getMessage());
			return null;
		}	
	}
	
	// 리스트 조회 (페이지 + 검색)
	@GetMapping(value = "/list",
				produces = MediaType.APPLICATION_JSON_VALUE)
	public Map<String, Object> list(@RequestParam(value="pageNum", defaultValue="1") int pageNum, 
									@RequestParam int member_num,
									@RequestParam(value="field", required=false) String field, 
									@RequestParam(value="keyword", required=false) String keyword){
		try {
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("member_num", member_num); // 회원번호 추가	
						
			// 전체 검색일 때 (검색 필드는 없고 키워드만 있을 때)
			if(StringUtils.isEmpty(field) && !StringUtils.isEmpty(keyword)) {
				field = "all";
			}
			
			// 검색 필드와 키워드 추가
			map.put("field", field);
			map.put("keyword", keyword);
			log.info(map);
			
			int totalRowCount = qnaService.listCnt(map); // 전체 결과 개수 구하기
			PageUtil util = new PageUtil(pageNum, 5, 5, totalRowCount);
			map.put("startRow", util.getStartRow()); // 시작행 번호
			map.put("endRow", util.getEndRow()); // 끝행 번호
			
			List<QnaListDTO> list = qnaService.list(map); // 리스트 불러오기
			map.put("list", list); 
			
			map.put("listCnt", totalRowCount); // 전체 결과 개수
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
	
	// 문의글 등록 
	@Transactional(rollbackFor = Exception.class)
	@PostMapping(value = "/insert",
				consumes = MediaType.APPLICATION_JSON_VALUE,
				produces = MediaType.APPLICATION_JSON_VALUE)
	public String insert(@RequestBody QnaVO vo){
		try {
			log.info(vo);			
			qnaService.insert(vo);
			return "success";
			
		} catch (Exception e) {
			log.info(e.getMessage());
			return "fail";		
		}
	}
}
