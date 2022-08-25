package com.moaplace.controller.moaplace;

import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.moaplace.dto.AdminListDTO;
import com.moaplace.service.AdminNoticeService;
import com.moaplace.util.PageUtil;
import com.moaplace.vo.AdminNoticeVO;

@CrossOrigin("*")
@RestController
@RequestMapping("/admin/news")
public class MoaplaceController {
	@Autowired
	private AdminNoticeService service;
	
	private Logger log = LoggerFactory.getLogger(this.getClass());

	//링크 접속 테스트 용
//	@GetMapping("/test")
//  public String test() {
//	  return "success";
//  }
  
	//공지사항 등록 
	@PostMapping(value = "/insert",
			consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public String newsinsert(@RequestPart("files") List<MultipartFile> multipartFile, 
			@RequestParam String title, @RequestParam String content, @RequestParam String sort_num) {
	  	
	  //받아온 값 확인
	  System.out.println("title="+title);
	  System.out.println("content="+content);
	  System.out.println("file="+multipartFile);
	  System.out.println("sort_num="+sort_num);
	  
	  AdminNoticeVO vo = new AdminNoticeVO(0, Integer.parseInt(sort_num) , 1, title, content, null , 0);
	  int n = service.insert(multipartFile, vo);
	  log.info("n값 : " + n);
	  if(n==0 || n==1) {
		  return "success";
	  }else {
		  return "fail";
	  }  
  }
	 
	//공지사항 리스트 - 리스트 및 검색
	@GetMapping(value= {
			"/list/{sort_num}/{member_num}",
			"/list/{sort_num}/{field}/{keyword}/{member_num}"}, produces = { MediaType.APPLICATION_JSON_VALUE })
	public HashMap<String, Object> list(
			@PathVariable(required = false,value="1") String pageNum,
			@PathVariable(required = false) String sort_num,
			@PathVariable(required = false) String field,
			@PathVariable(required = false) String keyword,
			@PathVariable(required = false) String member_num) {
		log.info("sort_num : " + sort_num);
		log.info("field : " + field);
		log.info("keyword : " + keyword);
		log.info("member_num : " + member_num);
		
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("member_num", Integer.parseInt(member_num));
		map.put("sort_num", Integer.parseInt(sort_num));
		map.put("field",field);
		map.put("keyword",keyword);
		
		int totalRowCount = service.getCount(map);
		PageUtil pu = new PageUtil(Integer.parseInt(pageNum),5,10,totalRowCount);
		int startRow = pu.getStartRow(); //시작행 번호
		int endRow = pu.getEndRow(); //끝행번호
		
		log.info("startRow : " + startRow);
		log.info("endRow : " + endRow);
	
		map.put("startRow" , startRow);
		map.put("endRow", endRow);
		List<AdminListDTO> list= service.listAll(map);
		
        map.put("list",list);
        map.put("pu",pu);
        return map;

	}
}

