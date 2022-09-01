package com.moaplace.controller.mypage;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import lombok.extern.log4j.Log4j;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.moaplace.dto.MyRentalDetailDTO;
import com.moaplace.service.RentalService;
import com.moaplace.util.FileUtil;

@CrossOrigin("*")
@Controller
@RequestMapping("/users/mypage")
@Log4j
public class MyPageFileDownloadController {
	
	@Autowired
	private RentalService rentalService;
	@Autowired
	private FileUtil fileUtil;
	
	/* 대관신청 파일 다운로드 */
	@GetMapping(value = "/file/download/{rental_num}")
	public String download(@PathVariable int rental_num, Model model) {
		
		MyRentalDetailDTO dto = rentalService.myDetail(rental_num);
		log.info(dto);
		
		HashMap<String, Object> fileInfo = fileUtil.download("rental", dto.getRental_savefilename());
		
		String name = dto.getRental_originfilename();
		long fileSize = Long.parseLong(dto.getRental_filesize());
		
		model.addAttribute("file", fileInfo.get("file"));
		model.addAttribute("fileName", name);
		model.addAttribute("fileSize", fileSize);
		
		return "fileDownloadView";
	}
}
