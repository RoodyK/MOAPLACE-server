package com.moaplace.controller.rental;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.moaplace.dto.AdminNoticeDetailDTO;
import com.moaplace.service.AdminNoticeService;
import com.moaplace.service.RentalService;
import com.moaplace.util.FileUtil;
import com.moaplace.vo.RentalVO;

import lombok.extern.log4j.Log4j;

@CrossOrigin("*")
@Controller
@RequestMapping("/rental/file")
@Log4j
public class RentalFileDownloadController {
	
	@Autowired
	private RentalService service;
	@Autowired
	private FileUtil fileutil;

	
	//파일 다운로드
		@GetMapping(value= {"/download/{num}"}
		)
	    public String download(
	    		@PathVariable int num,
	    		Model model) 
		{
			log.info(num);
			RentalVO vo = service.detail(num);
			
			HashMap<String, Object> fileinfo = fileutil.download("rental", vo.getRental_savefilename());
						
			String name = vo.getRental_originfilename();
			long filesize = vo.getRental_filesize();

			model.addAttribute("file", fileinfo.get("file"));
			model.addAttribute("fileName", name);
			model.addAttribute("fileSize", filesize);
			return "fileDownloadView";
		}
}
