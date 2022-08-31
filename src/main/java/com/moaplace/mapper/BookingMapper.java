package com.moaplace.mapper;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.moaplace.dto.MyBookingDTO;
import com.moaplace.dto.MyBookingDetailDTO;
import com.moaplace.vo.BookingVO;

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
	
	// booking_num으로 예매상세내역 조회
	MyBookingDetailDTO detail(int booking_num);
	
	// booking_num으로 공연일 조회(예매취소여부 조회용)
	String getScheduleDate(int booking_num);
	
	// booking_num으로 member_num 조회
	int cancleInfoCheck(int booking_num);
}
