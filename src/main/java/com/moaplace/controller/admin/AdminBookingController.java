package com.moaplace.controller.admin;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.moaplace.dto.BookingCancleDTO;
import com.moaplace.service.AdminBookingService;
import com.moaplace.service.BookingService;
import com.moaplace.service.ImportService;
import com.moaplace.service.PaymentService;
import com.moaplace.util.PageUtil;

import lombok.extern.log4j.Log4j;


@CrossOrigin("*")
@RequestMapping("/admin/ticket")
@RestController
@Log4j
public class AdminBookingController {
	
	@Autowired
	private AdminBookingService service;
	@Autowired
	private BookingService bookingService;
	@Autowired
	private ImportService importService;
	@Autowired
	private PaymentService paymentService;

	//관리자 예매리스트 조회
	@GetMapping(
			value = {
					"/list",
					"/list/{pageNum}",
					"/list/{pageNum}/{status}",
					"/list/{pageNum}/{status}/{field}",
					"/list/{pageNum}/{status}/{field}/{search}"},
			produces = MediaType.APPLICATION_JSON_VALUE)
	
	public HashMap<String, Object> adminBookingList(
			@PathVariable ( required = false ) Integer pageNum,
			@PathVariable ( required = false ) String status,
			@PathVariable ( required = false ) String field,
			@PathVariable ( required = false ) String search){
		
		//null값으로 들어왔을 때 기본값 설정
		if(pageNum==null)pageNum=1;
		if(status==null)status="전체";
		if(field==null)field="b.booking_num";
		
		// 페이징처리하는 클래스 유틸 받아서 페이징 데이터 생성
		PageUtil pu = new PageUtil(pageNum,5,5,service.adminAllBookingCnt());
		
		//db로 조회할 정보를 해시맵에 담기 (시작/끝행,검색조건)
		HashMap<String, Object> sList = new HashMap<String, Object>();
		sList.put( "startRow", pu.getStartRow());
		sList.put( "endRow", pu.getEndRow());
		sList.put( "status", status);
		sList.put( "field", field);
		sList.put( "search", search);
		
		// 검색조건이나 모아보기가 선택됐을 때 다시 조회된 행 번호 카운트해서 페이징처리 유틸에 새로 덮어씌우기 
		if(search!=null || !status.equals("all") ) {
			pu = new PageUtil( pageNum,5,5,service.currentAdminBookingCnt(sList));
		};

		// 해시맵에 조회된 공연목록, 페이지번호, 페이징유틸 정보 담아서 클라이언트로 보내기
		// 해시맵을 produces = MediaType.APPLICATION_JSON_VALUE 으로 제이슨형태로 변환해서 전송
		if(search==null)search="";
		
		HashMap<String, Object> map=new HashMap<String, Object>();
		map.put( "list", service.adminBookingList(sList));
		map.put( "pageNum", pageNum);
		map.put( "pageInfo", pu);
		map.put( "status", status);
		map.put( "selectField", field);
		map.put( "search", search);
		
		return map;
	}
	
	//관리자 예매정보 상세조회
	@GetMapping
	( value = "/detail/{bookingNum}")
	public HashMap<String, Object> bookingDetail(
			@PathVariable int bookingNum){
		
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("list",service.selectAdminBookingDetail(bookingNum));
		
		return map;
	}
	
	// 예매취소
	@PostMapping(value="/cancle",
				 consumes = MediaType.APPLICATION_JSON_VALUE)
	public String cancle(@RequestBody BookingCancleDTO dto) {
		try {
			
			log.info(dto);
			
			String token = importService.getToken();
			String imp_uid = dto.getImp_uid();
			String reason = dto.getReason();
			int amount = dto.getAmount();
			boolean importCancle = importService.cancle(token,imp_uid,reason,amount);
			if (!importCancle) return "fail"; // 아임포트 서버 환불요청 실패한 경우 fail
			
			// payment 결제상태 변경
			int bookingNum = dto.getBooking_num();
			paymentService.ticketCancle(bookingNum);
			
			// all_seat 테이블 삭제
			bookingService.cancleSeat(bookingNum);
			
			return "success";
			
		} catch (Exception e) {
			log.info(e.getMessage());
			return "fail";
		}
	}
}
