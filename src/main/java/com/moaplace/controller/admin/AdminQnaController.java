package com.moaplace.controller.admin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.moaplace.dto.AdminQnaListDTO;
import com.moaplace.dto.QnaAnswerDTO;
import com.moaplace.dto.QnaMemberDTO;
import com.moaplace.service.AnswerService;
import com.moaplace.service.QnaService;
import com.moaplace.util.PageUtil;
import com.moaplace.vo.AnswerVO;
import com.moaplace.vo.QnaVO;

import lombok.extern.log4j.Log4j;

@CrossOrigin("*")
@RestController
@RequestMapping("/admin/qna")
@Log4j
public class AdminQnaController {

	@Autowired 
	private QnaService qnaService;
	@Autowired
	private AnswerService answerService;
	
	// 답변만 삭제
	@Transactional(rollbackFor = Exception.class)
	@PostMapping(value = "/answer/delete/{qna_num}")
	public String delete(@PathVariable int qna_num){
		try {
			log.info(qna_num);
			
			answerService.delete(qna_num);
			
			// 문의글 상태 변경
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("qna_state", "대기중");
			map.put("qna_num", qna_num);			
			qnaService.changeState(map);
			
			return "success";
			
		} catch (Exception e) {
			log.info(e.getMessage());
			return "fail";		
		}
	}
	
	// 답변 수정
	@PostMapping(value = "/answer/update",
				 consumes = MediaType.APPLICATION_JSON_VALUE)
	public String update(@RequestBody AnswerVO vo){
		try {
			log.info(vo);
			answerService.update(vo);
			return "success";
			
		} catch (Exception e) {
			log.info(e.getMessage());
			return "fail";		
		}
	}
	
	// 답변 등록 
	
	@PostMapping(value = "/answer/insert",
				 consumes = MediaType.APPLICATION_JSON_VALUE)
	public String insert(@RequestBody AnswerVO vo){
		try {
			log.info(vo);			
			answerService.insert(vo); // 답변 등록
			
			// 문의글 상태 변경
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("qna_state", "답변완료");
			map.put("qna_num", vo.getQna_num());
			
			qnaService.changeState(map);
			
			return "success";
			
		} catch (Exception e) {
			log.info(e.getMessage());
			return "fail";		
		}
	}
	
	// 문의 상세글 및 답변 조회
	@GetMapping(value="/detail/{qna_num}",
				produces = MediaType.APPLICATION_JSON_VALUE)
	public Map<String, Object> detail(@PathVariable int qna_num){
		
		try {			
			log.info(qna_num);
						
			QnaVO detail = qnaService.detail(qna_num);
			AnswerVO answer = answerService.selectAdmin(qna_num);
			
			int member_num = detail.getMember_num();
			QnaMemberDTO member = qnaService.qnaMember(member_num);
			
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("detail", detail);
			map.put("answer", answer);			
			map.put("member", member);
			
			log.info(map);
			return map;
			
		} catch (Exception e) {
			log.info(e.getMessage());
			return null;
		}	
	}
	
	// 문의상태 수정
	@PostMapping(value = "/changeState/{qna_state}/{qna_num}")
	public String changeState(@PathVariable String qna_state,
							  @PathVariable int qna_num){		
		try {
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("qna_state", qna_state);
			map.put("qna_num", qna_num);
			log.info(map);
			
			qnaService.changeState(map);
			
			return "success";
			
		} catch (Exception e) {
			log.info(e.getMessage());
			return "fail";		
		}
	}
	
	// 관리자 리스트 조회 (페이지 + 검색)
	@GetMapping(value = "/list",
				produces = MediaType.APPLICATION_JSON_VALUE)
	public Map<String, Object> list(@RequestParam(value="pageNum", defaultValue="1") int pageNum,
									@RequestParam(value="field", required=false) String field, 
									@RequestParam(value="keyword", required=false) String keyword,
									@RequestParam(value="sort_num", defaultValue="0") int sort_num){
		try {
			
			HashMap<String, Object> map = new HashMap<String, Object>();
					
			// 검색 필드와 키워드, 구분번호 추가
			map.put("field", field);
			map.put("keyword", keyword);
			map.put("sort_num", sort_num);
			log.info(map);
			
			int totalRowCount = qnaService.adminListCnt(map); // 전체 결과 개수 구하기
			PageUtil util = new PageUtil(pageNum, 5, 5, totalRowCount);
			map.put("startRow", util.getStartRow()); // 시작행 번호
			map.put("endRow", util.getEndRow()); // 끝행 번호
			
			List<AdminQnaListDTO> list = qnaService.adminList(map); // 리스트 불러오기
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
}
