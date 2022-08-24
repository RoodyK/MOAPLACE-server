package com.moaplace.service;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.moaplace.dto.MyBookingDTO;
import com.moaplace.dto.MyBookingDetailDTO;
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
	
	// member_num으로 회원의 예매내역 개수 확인 (필요없을듯)
	public int bookingCount(int member_num) {
		return mapper.bookingExist(member_num);
	}
	
	// member_num으로 회원의 가장 최근 예매내역 1건 조회
	public MyBookingDTO recentBooking(int member_num) {
		return mapper.recentBooking(member_num);
	}
	
	// member_num + startdate + enddate + startrow + endrow 받아서 기간설정 + 페이징 조회
	public List<MyBookingDTO> list(HashMap<String, Object> map) {
		return mapper.list(map);
	}
	
	// 기간설정  + 페이징 조회된 내역 개수
	public int listCount(HashMap<String, Object> map) {
		return mapper.listCount(map);
	}
	
	// booking_num으로 예매상세내역 조회
	public MyBookingDetailDTO detail(int booking_num) {
		return mapper.detail(booking_num);
	}
	
}
