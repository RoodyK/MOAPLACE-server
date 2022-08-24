package com.moaplace.controller.mypage;

import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.moaplace.dto.MyBookingDTO;
import com.moaplace.dto.MyRentalDTO;
import com.moaplace.service.BookingService;
import com.moaplace.service.RentalService;
import com.moaplace.util.PageUtil;

import lombok.extern.log4j.Log4j;

@CrossOrigin("*")
@RestController
@RequestMapping("/users")
@Log4j
public class MypageController {

	@Autowired
	private BookingService bookingService;
	@Autowired
	private RentalService rentalService;
	
	/* 로그인한 회원의 최근 예매내역 1건 + 최근 대관내역 1건 조회 */
	@RequestMapping(value = "/mypage/{member_num}", produces = MediaType.APPLICATION_JSON_VALUE)
	public Map<String, Object> mypage(@PathVariable("member_num") int member_num) {
		
		try {
			log.info(member_num);
			
			HashMap<String, Object> map = new HashMap<String, Object>();
			
			// 1. 예매내역, 대관내역 유무 조회하고 존재하지 않으면 bkExist false, 존재하면 true put
			boolean bkExist = bookingService.bookingExist(member_num); // 예매내역 존재여부
			boolean rtExist = rentalService.rentalExist(member_num); // 대관내역 존재여부
			map.put("bkExist", bkExist);
			map.put("rtExist", rtExist);
			
			// 2. 예매내역 존재하면 MyBookingDTO도 put
			if(bkExist) {
				MyBookingDTO bkDto = bookingService.recentBooking(member_num);
				map.put("bkDto", bkDto);
			}
			
			// 3. 대관내역 존재하면 MyRentalDTO도 put
			if(rtExist) {
				MyRentalDTO rtDto = rentalService.recentRental(member_num);
				map.put("rtDto", rtDto);
			}
			
			return map;
			
		} catch (Exception e) {
			log.info(e.getMessage());
			return null;
		}
	}
	
	@RequestMapping(value = {"/ticket/list/{member_num}/{startdate}/{enddate}", 
			"/ticket/list/{member_num}/{startdate}/{enddate}/{pageNum}"},
			produces = MediaType.APPLICATION_JSON_VALUE)
	public Map<String, Object> ticketList(
			@PathVariable("member_num") int member_num,
			@PathVariable("startdate") String startdate,
			@PathVariable("enddate") String enddate,
			@PathVariable("pageNum") Integer pageNum) 
	{
		try {
			
			SimpleDateFormat dtFormat = new SimpleDateFormat("yyyy-MM-dd");
			Date cstartdate = dtFormat.parse(startdate); // 시작날짜 String -> Date
			Date cenddate = dtFormat.parse(enddate); // 끝날짜 String -> Date
			
			HashMap<String, Object> map = new HashMap<String, Object>();
			
			boolean bkExist = bookingService.bookingExist(member_num); // 예매내역 존재여부
			map.put("bkExist", bkExist);
			
			// 페이지번호 없으면 1로 초기화
			if(pageNum == null) pageNum = 1;
			
			map.put("member_num", member_num); // 회원번호
			map.put("startdate", cstartdate); // 시작날짜
			map.put("enddate", cenddate); // 끝날짜
			
			int totalRowCount = bookingService.listCount(map); // 전체 결과 개수

			PageUtil pageUtil = new PageUtil(pageNum, 3, 5, totalRowCount); // 한페이지 3개, 한페이지당 페이지개수 5개

			map.put("startRow", pageUtil.getStartRow()); // 시작행번호
			map.put("endRow", pageUtil.getStartRow()); // 끝행번호
			
			List<MyBookingDTO> list = bookingService.list(map); // 예매내역 리스트
			map.put("list", list);
			
			map.put("listCnt", totalRowCount); // 전체 결과 개수
			map.put("pageNum", pageNum); // 페이지번호
			map.put("startPage", pageUtil.getStartPageNum()); // 페이지시작번호
			map.put("endPage", pageUtil.getEndPageNum()); // 페이지끝번호
			map.put("pageCnt", pageUtil.getTotalPageCount()); // 전체 페이지수
			
			return map;
			
		} catch (Exception e) {
			log.info(e.getMessage());
			return null;
		}
	}
}
