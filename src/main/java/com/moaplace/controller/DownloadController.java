//package com.moaplace.controller;
//
//import java.io.File;
//
//import javax.servlet.ServletContext;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//
//
//
//@Controller
//public class DownloadController {
//
//	@Autowired
//	private FileInfoService service;
//	@Autowired
//	private ServletContext sc;
//	
//	@GetMapping("/file/download")
//	public String download(@RequestParam("filenum") int fileNum, Model model) {
//		FileInfoVO vo = service.findOne(fileNum);
//		
//		String path = sc.getRealPath("/resources/upload");
//		File f = new File(path + File.separator + vo.getSaveFileName());
//		String name = vo.getOrgFileName();
//		long fileSize = vo.getFileSize();
//		
//		model.addAttribute("file", f);
//		model.addAttribute("fileName", name);
//		model.addAttribute("fileSize", fileSize);
//		
//		return "fileDownloadView";
//	}
//}
