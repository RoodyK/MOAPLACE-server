package com.moaplace.controller.rental;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.moaplace.service.RentalService;
import com.moaplace.vo.RentalAnswerVO;

import lombok.extern.log4j.Log4j;

@CrossOrigin("*")
@RestController
@RequestMapping("/admin/rental/answer")
@Log4j
public class RentalAnswerController {
	
	@Autowired
	RentalService service;
	
	
	@PostMapping
	(value = "/insert")
	public String insert(
			@RequestBody RentalAnswerVO data)
	{
		log.info(data);
			
		int n = service.answerInsert(data);
		
		if(n > 0) {
			return "success";
		}else {
			return "fail";
		}
	}
	
	@GetMapping
	(value = "/getAnswer/{rental_num}",
	produces = {MediaType.APPLICATION_JSON_VALUE})
	public HashMap<String, Object> getAnswer(
			@PathVariable Integer rental_num)
	{
		log.info(rental_num);
		
		RentalAnswerVO vo = service.getAnswer(rental_num);
		
		HashMap<String, Object> map = new HashMap<String, Object>();

		if(vo != null) {
			map.put("result", "success");
			map.put("cont", vo);
		}else{
			map.put("result", "fail");
		}
		
		return map;
	}
	
	
	@PostMapping
	(value = "/update")
	public String update(
			@RequestBody RentalAnswerVO data)
	{
		int n = service.answerUpdate(data);
		
		if(n > 0) {
			return "success";
		}else {
			return "fail";
		}
	}
	
	
	@DeleteMapping
	(value = "/delete/{rental_num}")
	public String delete(
			@PathVariable int rental_num)
	{
		
		int n = service.answerDelete(rental_num);
		
		if(n > 0) {
			return "success";
		}else {
			return "fail";
		}
	}
}
