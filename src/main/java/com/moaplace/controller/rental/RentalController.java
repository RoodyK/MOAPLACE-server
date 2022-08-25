package com.moaplace.controller.rental;

import java.util.HashMap;
import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.moaplace.dto.RentalCalendarDTO;
import com.moaplace.dto.RentalInsertDTO;
import com.moaplace.service.RentalService;
import com.moaplace.util.FileUtil;
import com.moaplace.util.PageUtil;
import com.moaplace.vo.RentalVO;

import lombok.extern.log4j.Log4j;

@CrossOrigin("*")
@RestController
@RequestMapping("/rental")
@Log4j
public class RentalController {
	@Autowired
	private RentalService service;
	@Autowired
	private FileUtil fileutil;
	
	@GetMapping
	(value = "/calendar/{year}/{month}/{endOfDay}",
	produces= {MediaType.APPLICATION_JSON_VALUE})
	public HashMap<String, Object> calendar(
			@PathVariable String year,
			@PathVariable String month,
			@PathVariable String endOfDay)
	{
		
		String startDate = year + "-" + month + "-" + 1;
		String endDate = year + "-" + month + "-" + endOfDay;
		
		HashMap<String, String> map = new HashMap<String, String>();
		map.put( "startDate" , startDate );
		map.put( "endDate" , endDate );
		
		
		List<RentalCalendarDTO> list = service.getSchedules(map);
		
		HashMap<String, Object> data = new HashMap<String, Object>();
		data.put("year" , year);
		data.put("month" , month);
		data.put("schedule" , list);
		
		return data;
	}
	
	@GetMapping
	(value = "/update/{num}/{state}")
	public String updateState(
			@PathVariable Integer num,//대관신청번호
			@PathVariable String state)//진행상태
	{
		log.info("num:" + num);
		log.info("state:" + state);
		
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("rental_num", num);
		map.put("rental_state", state);
		
		int n = service.updateState(map);
		
		if( n < 0 ) return "fail";
		
		return "success";
	}
	
	
	@GetMapping
	(value= {"/list","/list/{pagenum}","/list/{sort}/{keyword}","/list/{pagenum}/{sort}/{keyword}"},
	produces= {MediaType.APPLICATION_JSON_VALUE})
	public HashMap<String, Object> list(
			@PathVariable(required=false) Integer pagenum,
			@PathVariable(required=false) String sort,
			@PathVariable(required=false) String keyword)
	{
		log.info("pagenum : " + pagenum);
		log.info("sort : " + sort);
		log.info("keyword : " + keyword);
		
		if(pagenum == null) {
			pagenum = 1;
		}
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("pagenum" , pagenum);
		map.put("sort" , sort);
		map.put("keyword" , keyword);
		int totalRowCount = service.getCount(map);
		
		PageUtil pageutil = new PageUtil(pagenum, 5, 5, totalRowCount);
		int startRow = pageutil.getStartRow();
		int endRow = pageutil.getEndRow();
		map.put("startRow", startRow);
		map.put("endRow", endRow);
		List<RentalVO> list = service.list(map);
		
		HashMap<String, Object> data = new HashMap<String, Object>();
		data.put("list", list);
		data.put("pageUtil", pageutil);
		data.put("sort", sort);
		data.put("keyword", keyword);
		
		return data;
	}
	
	
	@PostMapping
	(value = "/insert",
	consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
	public String insert(@RequestParam String data, @RequestPart MultipartFile file)
	{
		ObjectMapper mapper = new ObjectMapper();
		RentalInsertDTO dto = new RentalInsertDTO();
		try {
			dto = mapper.readValue(data, RentalInsertDTO.class);
		} catch (Exception e) {
			 e.printStackTrace();
		}
		HashMap<String, Object> fileinfo = fileutil.upload(file, "rental");
		//파일 저장이 성공했을 때
		if(fileinfo.get("result").equals("success")) 
		{
			try {
				//vo에 데이터 주입
				RentalVO vo = new RentalVO();
				vo.setHall_num(dto.getHall_num());
				vo.setMember_num(dto.getMember_num());
				vo.setRental_name(dto.getRental_name());
				vo.setRental_phone(dto.getRental_phone());
				vo.setRental_email(dto.getRental_email());
				vo.setRental_title(dto.getRental_title());
				vo.setRental_genre(dto.getRental_genre());
				vo.setRental_date(dto.getRental_date());
				vo.setRental_time(dto.getRental_time());
				vo.setRental_originfilename(String.valueOf(fileinfo.get("orgfilename")));
				vo.setRental_savefilename(String.valueOf(fileinfo.get("savefilename")));
				vo.setRental_filesize(Long.valueOf(String.valueOf(fileinfo.get("filesize"))));
				vo.setRental_ownsname(dto.getRental_ownsname());
				vo.setRental_ownsphone(dto.getRental_ownsphone());
				vo.setRental_ownemail(dto.getRental_ownemail());
				vo.setRental_content(dto.getRental_content());
				
				int n = service.insert(vo);
				if(n < 0) new Exception();
				return "success";
			} catch (Exception e) {
				log.error(e.getMessage());
				return "fail";		
			}
		}else {
			return "fail";
		}
	}
}
