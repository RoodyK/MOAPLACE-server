package com.moaplace.mapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.moaplace.dto.AdminBookingDetailDTO;
import com.moaplace.dto.AdminBookingListDTO;
import com.moaplace.dto.AdminChartDTO;
import com.moaplace.dto.MyBookingDTO;
import com.moaplace.dto.MyBookingDetailDTO;
import com.moaplace.dto.payment.BookingDTO;
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
	
  // 관리자 공연 매출 
	public List<AdminChartDTO> bookingChart(Map<String, Object> map);

	// booking_num으로 member_num 조회
	int cancleInfoCheck(int booking_num);
	
	//관리자 예매리스트
	List<AdminBookingListDTO> adminBookingList(HashMap<String, Object> map);
	
	//관리자 예매리스트 총 행 수 조회
	int adminAllBookingCnt();
	
	//검색 후의 관리자 예매리스트 행 수 조회
	int currentAdminBookingCnt(HashMap<String, Object> map);
	
	//관리자 예매상세 조회
	List<AdminBookingDetailDTO>  selectAdminBookingDetail(int bookingNum);

	//예매페이지 결제 완료후 INSERT
	int payInfoInsert(BookingVO vo);

}