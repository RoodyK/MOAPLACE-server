package com.moaplace.controller.show;

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

import com.moaplace.dto.ReviewDTO;
import com.moaplace.service.ReviewService;
import com.moaplace.util.PageUtil;
import com.moaplace.vo.FavoriteVO;
import com.moaplace.vo.ReviewVO;

import lombok.extern.log4j.Log4j;

@RestController
@RequestMapping("/show/review")
@CrossOrigin("*")
@Log4j
public class ReviewController {
	
	@Autowired 
	private ReviewService review_service;
	
	@GetMapping(value = "/list/{show_num}/{pagenum}", 
			produces = MediaType.APPLICATION_JSON_VALUE)
	public HashMap<String, Object> list(
			@PathVariable Integer show_num,
			@PathVariable Integer pagenum){
		
		
		int reviewCount = review_service.reviewCount(show_num);
		PageUtil pu=new PageUtil(pagenum,20, 5, reviewCount);
		int startRow=pu.getStartRow();//시작행번호
		int endRow=pu.getEndRow();//끝행번호
		
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("show_num", show_num);
		map.put("startRow", startRow);
		map.put("endRow", endRow);
		
		List<ReviewDTO> reviewList = review_service.reviewList(map);
		
		HashMap<String, Object> data = new HashMap<String, Object>();
		data.put("reviewCount", reviewCount);
		data.put("reviewList", reviewList);
		data.put("pageUtil", pu);
		
		return data;
	}
	
	@PostMapping(value= "/insert",
			 consumes = MediaType.APPLICATION_JSON_VALUE)
	public String insert(@RequestBody ReviewVO vo) {
		
		try {			
			review_service.reviewInsert(vo);
			
			return "success";
		} catch (Exception e) {
			
			return "fail";		
		}
	}
	
	@PostMapping(value= "/delete",
			 consumes = MediaType.APPLICATION_JSON_VALUE)
	public String delete(@RequestBody int review_num) {
		
		try {			
			review_service.reviewDelete(review_num);
			return "success";
		} catch (Exception e) {
			return "fail";		
		}
	}
	
	@GetMapping(value = "/select/{review_num}", 
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ReviewVO select(
			@PathVariable Integer review_num){
		
		return review_service.reviewSelect(review_num);
	}
	
	@PostMapping(value= "/edit",
			 consumes = MediaType.APPLICATION_JSON_VALUE,
			 produces = MediaType.APPLICATION_JSON_VALUE)
	public String update(@RequestBody ReviewVO vo) {
		
		try {			
			log.info(vo);
			review_service.reviewUpdate(vo);
			
			return "success";
		} catch (Exception e) {
			log.info(e.getMessage());
			return "fail";		
		}
	}
}
