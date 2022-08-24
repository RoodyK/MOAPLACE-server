package com.moaplace.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.moaplace.dto.MyBookingDTO;

@Mapper
public interface BookingMapper {
	
	// member_num으로 회원의 예매내역 존재여부 확인
	int bookingExist(int member_num);
	
	// member_num으로 회원의 가장 최근 예매내역 1건 조회
	MyBookingDTO recentBooking(int member_num);
	
}
