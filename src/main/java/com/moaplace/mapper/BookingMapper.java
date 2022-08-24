package com.moaplace.mapper;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.moaplace.dto.MyBookingDTO;

@Mapper
public interface BookingMapper {
	
	// member_num으로 회원의 예매내역 존재여부 + 개수 확인
	int bookingExist(int member_num);
	
	// member_num으로 회원의 가장 최근 예매내역 1건 조회
	MyBookingDTO recentBooking(int member_num);
	
	// member_num + startdate + enddate + startrow + endrow 받아서 기간설정 + 페이징 조회
	List<MyBookingDTO> list(HashMap<String, Object> map);
	
	// 기간설정  + 페이징 조회된 내역 개수
	int listCount(HashMap<String, Object> map);
}
