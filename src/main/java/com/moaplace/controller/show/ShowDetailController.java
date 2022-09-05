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

import com.moaplace.dto.GradeDTO;
import com.moaplace.dto.ResidualDTO;
import com.moaplace.dto.ScheduleDTO;
import com.moaplace.dto.ShowDetailDTO;
import com.moaplace.dto.ShowImgDTO;
import com.moaplace.service.FavoriteService;
import com.moaplace.service.GradeService;
import com.moaplace.service.ReviewService;
import com.moaplace.service.ScheduleService;
import com.moaplace.service.ShowImgService;
import com.moaplace.service.ShowService;
import com.moaplace.vo.FavoriteVO;

import lombok.extern.log4j.Log4j;

@RestController
@RequestMapping("/show")
@CrossOrigin("*")
@Log4j
public class ShowDetailController {
	
	@Autowired 
	private ShowService show_service;
	@Autowired 
	private ShowImgService show_img_service;
	@Autowired 
	private GradeService grade_service;
	@Autowired 
	private ScheduleService schedule_service;
	@Autowired 
	private FavoriteService favorite_service;
	
	@GetMapping(value = "/showdetail/{show_num}", 
			produces = MediaType.APPLICATION_JSON_VALUE)
	public HashMap<String, Object> detail(
			@PathVariable Integer show_num){
		
		List<ShowDetailDTO> detail = show_service.detail(show_num);
		List<ScheduleDTO> schedule = schedule_service.schedule(show_num);
		List<GradeDTO> grade = grade_service.grade(show_num);
		List<ShowImgDTO> detailimg = show_img_service.detailimg(show_num);
		
		HashMap<String, Object> data = new HashMap<String, Object>();
		data.put("detail", detail);
		data.put("schedule", schedule);
		data.put("grade", grade);
		data.put("detailimg", detailimg);
		
		return data;
	}
	
	@PostMapping(value= "/inter/insert",
			 consumes = MediaType.APPLICATION_JSON_VALUE)
	public String insert(@RequestBody FavoriteVO vo) {
		
		try {			
			log.info(vo);
			favorite_service.insert(vo);
			
			return "success";
		} catch (Exception e) {
			log.info(e.getMessage());
			return "fail";		
		}
	}
	
	@GetMapping(value = "/residualseats/{show_num}",
			produces = MediaType.APPLICATION_JSON_VALUE)
	public HashMap<String, Object> residualseats(
			@PathVariable Integer show_num){
		
		List<ShowDetailDTO> detail = show_service.detail(show_num);
		List<ResidualDTO> schedule = schedule_service.selectlist(show_num);
		List<Integer> rowinfo = schedule_service.rowinfo(show_num);
		
		HashMap<String, Object> data = new HashMap<String, Object>();
		data.put("detail", detail);
		data.put("schedule", schedule);
		data.put("rowinfo", rowinfo);

		return data;
	}
	
}
