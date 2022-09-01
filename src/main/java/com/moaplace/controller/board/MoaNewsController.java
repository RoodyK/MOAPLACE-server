package com.moaplace.controller.board;

import java.util.HashMap;  
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.moaplace.dto.AdminDetailDTO;
import com.moaplace.dto.AdminListDTO;
import com.moaplace.dto.AdminNoticeDTO;
import com.moaplace.dto.AdminNoticeDetailDTO;
import com.moaplace.service.MoaplaceService;
import com.moaplace.util.PageUtil;

@CrossOrigin("*")
@RestController
@RequestMapping("/moaplace/news")
public class MoaNewsController {
	@Value("${oracle.download}")
	private String realPath; 
	
	@Autowired
	private MoaplaceService service;
	
	private Logger log = LoggerFactory.getLogger(this.getClass());
	
//	@GetMapping("/test")
//  public String test() {
//	  return "success";
//  }

	
	//공지사항 리스트 - 리스트 및 검색
		@GetMapping(value= {
				"/list/{pageNum}",
				"/list/{field}/{keyword}/{pageNum}"}, produces = { MediaType.APPLICATION_JSON_VALUE })
		public HashMap<String, Object> list(
				@PathVariable(required = false) String pageNum,
				@PathVariable(required = false) String field,
				@PathVariable(required = false) String keyword) {
			log.info("field : " + field);
			log.info("keyword : " + keyword);
			
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("field",field);
			map.put("keyword",keyword);
		
			int totalRowCount = service.getCount(map); //전체 글 개수
			PageUtil pu = new PageUtil(Integer.parseInt(pageNum),5,5,totalRowCount);
			
			int startRow = pu.getStartRow(); //시작행 번호
			int endRow = pu.getEndRow(); //끝행번호
			int startPageNum = pu.getStartPageNum(); //시작 페이지 번호
			int endPageNum = pu.getEndPageNum(); //끝 페이지 번호
			int totalPageCount = pu.getTotalPageCount(); //전체 글 개수

//			log.info("===============================================");
//			log.info("pageNum : " + pageNum);
//			log.info("totalRowCount : " + totalRowCount);
//			log.info("startPageNum : " + startPageNum);
//			log.info("endPageNum : " + endPageNum);
//			log.info("endRow : " + endRow);
//			log.info("startRow : " + startRow);
//			log.info("totalPageCount: " + totalPageCount);
//			log.info("===============================================");
		    
			map.put("startPageNum" , startPageNum);
			map.put("endPageNum" , endPageNum);
			map.put("totalPageCount" , totalPageCount);
			map.put("totalRowCount" , totalRowCount);
			map.put("startRow" , startRow);
			map.put("endRow", endRow);
			List<AdminListDTO> list= service.listAll(map);
			
			log.info("===============================================");
			log.info("새소식 list: " + list);
			log.info("===============================================");
			
	        map.put("list",list);
//	        map.put("pu",pu);// resp.data.pu.startPageNum 
	        return map;

		}
		
		//상세보기 
		@GetMapping(value= {"/detail/{notice_num}"}
		, produces = { MediaType.APPLICATION_JSON_VALUE })
		public HashMap<String, Object> detail(
				@PathVariable(required = false) String notice_num){
			
			service.addHit(Integer.parseInt(notice_num));
			
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("notice_num", Integer.parseInt(notice_num));
			
			List<AdminNoticeDetailDTO> filelist = service.filelist(Integer.parseInt(notice_num));
			AdminListDTO detaillist =  service.selectdetail(Integer.parseInt(notice_num));
			AdminNoticeDTO next = service.getNext(Integer.parseInt(notice_num));
			AdminNoticeDTO prev = service.getPrev(Integer.parseInt(notice_num));
			
			map.put("sort_num",detaillist.getSort_num());
			map.put("sort_name",detaillist.getSort_name());
			map.put("notice_title",detaillist.getNotice_title());
			map.put("notice_content",detaillist.getNotice_content());
			map.put("notice_regdate",detaillist.getNotice_regdate());
			map.put("notice_hit",detaillist.getNotice_hit());
			
			//list는 size로 확인, 배열은 length
			if(filelist.size() >0) { 
			   map.put("filelist",filelist);
			}
			
			map.put("next", next);
			map.put("prev", prev);
			
			//VO,DTO 로 받으면 {} Object 
			//List 받으면 [] array
			
			log.info("=======================================새소식 상세보기 log==============================================");
			log.info("sort_name: " +detaillist.getSort_name());
//			log.info("상세글 :" + service.selectdetail(Integer.parseInt(notice_num)));
//			log.info("다음글 정보 :" + service.getNext(Integer.parseInt(notice_num)));
//			log.info("이전글 정보 :" + service.getPrev(Integer.parseInt(notice_num)));
			log.info("====================================================================================================");
			
			return map;

		}
		
}
