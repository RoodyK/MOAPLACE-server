package com.moaplace.controller.board;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.moaplace.dto.AdminNoticeDetailDTO;
import com.moaplace.service.AdminNoticeService;
import com.moaplace.util.FileUtil;

@CrossOrigin("*")
@Controller
@RequestMapping("/admin/news")
public class FileDownloadController {
	
	@Autowired
	private AdminNoticeService service;
	@Autowired
	private FileUtil fileutil;

	
	//파일 다운로드
		@GetMapping(value= {"/file/download/{notice_detail_num}"}
		)
	    public String download(
	    		@PathVariable(required = false) String notice_detail_num,
	    		Model model) 
		{
			AdminNoticeDetailDTO dto = service.filedown(Integer.parseInt(notice_detail_num));
			
			HashMap<String, Object> fileinfo = fileutil.download("notice", dto.getNotice_savefile());
						
			String name = dto.getNotice_orgfile();
			long filesize = dto.getNotice_filesize();

			model.addAttribute("file", fileinfo.get("file"));
			model.addAttribute("fileName", name);
			model.addAttribute("fileSize", filesize);
			return "fileDownloadView";
		}
}
