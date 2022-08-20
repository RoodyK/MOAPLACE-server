package com.moaplace.controller.board;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.moaplace.service.SortService;
import com.moaplace.vo.SortVO;

@CrossOrigin("*")
@RestController
@RequestMapping("/sort")
public class SortListController {	
	@Autowired private SortService sortService;
	
	@GetMapping(value="/list",
				produces = MediaType.APPLICATION_JSON_VALUE)
	public List<SortVO> list(){
		return sortService.select();
	}

}
