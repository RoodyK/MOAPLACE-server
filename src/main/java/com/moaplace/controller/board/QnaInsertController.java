package com.moaplace.controller.board;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.moaplace.service.QnaService;
import com.moaplace.vo.QnaVO;

@CrossOrigin("*")
@RestController
@RequestMapping("/qna")
public class QnaInsertController {	
	@Autowired private QnaService qnaService;
	
	//@PostMapping(value="/insert")
	@PostMapping(value = "/insert",
				consumes = "application/json",
				produces = { MediaType.APPLICATION_JSON_VALUE })
	public String insert(@RequestBody QnaVO vo){
		try {
			qnaService.insert(vo);
			return "success";
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return "fail";		
		}
	}
}
