package com.moaplace.controller.rental;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.moaplace.dto.RentalInsertDTO;
import com.moaplace.service.RentalService;
import com.moaplace.util.FileUtil;
import com.moaplace.vo.RentalVO;

import lombok.extern.log4j.Log4j;

@CrossOrigin("*")
@RestController
@RequestMapping("/rental")
@Log4j
public class InsertController {
	@Autowired
	private RentalService service;
	@Autowired
	private FileUtil fileutil;
	
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
				SimpleDateFormat formatter = new SimpleDateFormat("yyyy-mm-dd");
				vo.setRental_date(formatter.parse(dto.getRental_date()));
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
