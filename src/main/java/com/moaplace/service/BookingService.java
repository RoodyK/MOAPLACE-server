package com.moaplace.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.moaplace.dto.MyBookingDTO;
import com.moaplace.mapper.BookingMapper;

@Service
public class BookingService {

	@Autowired private BookingMapper mapper;
	
	// member_num으로 회원의 예매내역 존재여부 확인
	public boolean bookingExist(int member_num) {
		boolean exist = false;
		if(mapper.bookingExist(member_num) > 0) {
			exist = true;
		}
		return exist;
	}
	
	// member_num으로 회원의 가장 최근 예매내역 1건 조회
	public MyBookingDTO recentBooking(int member_num) {
		return mapper.recentBooking(member_num);
	}
	
}
