package com.moaplace.controller.mypage;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.moaplace.dto.MyBookingDTO;
import com.moaplace.dto.MyRentalDTO;
import com.moaplace.service.BookingService;
import com.moaplace.service.RentalService;

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
	
}
