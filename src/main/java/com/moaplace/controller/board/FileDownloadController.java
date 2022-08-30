package com.moaplace.controller.board;

import java.io.File;
import java.util.regex.Matcher;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

@CrossOrigin("*")
@Controller
@RequestMapping("/admin/news")
public class FileDownloadController {
	@Value("${oracle.download}")
	private String realPath; 
	
	@Autowired
	private AdminNoticeService service;
	
	private Logger log = LoggerFactory.getLogger(this.getClass());
	
	//파일 다운로드
		@GetMapping(value= {"/file/download/{notice_detail_num}"}
		)
	    public String download(@PathVariable(required = false) String notice_detail_num,
	    		Model model) {
			AdminNoticeDetailDTO dto = service.filedown(Integer.parseInt(notice_detail_num));
			String path = realPath;;
			//파일 경로 fileutil 수정 
			File f = new File("C://2203//MoaPlace//upload" + File.separator + dto.getNotice_savefile()); // 파일 객체 생성 	
			
			String name = dto.getNotice_orgfile();
			long filesize = dto.getNotice_filesize();
			log.info("==============================파일 다운로드 log===============================");
//			log.info("reverseSlashPath: " + reverseSlashPath);
			log.info("path: " + path);
			log.info("f: " + f);
			log.info("name :" + name);
			log.info("filesize :" + filesize);
			log.info("==========================================================================");
			model.addAttribute("file", f);
			model.addAttribute("fileName", name);
			model.addAttribute("fileSize", filesize);
			return "fileDownloadView";
		}
}
