package com.moaplace.mapper;


import java.util.HashMap;
import java.util.List;

import com.moaplace.dto.RentalCalendarDTO;
import com.moaplace.vo.RentalVO;

import org.apache.ibatis.annotations.Mapper;

import com.moaplace.dto.MyRentalDTO;
import com.moaplace.dto.MyRentalDetailDTO;

@Mapper
public interface RentalMapper {
	
  // 혜인
	int insert(RentalVO vo);
	int getCount(HashMap<String, Object> map);
	List<RentalVO> list(HashMap<String, Object> map);
	int updateState(HashMap<String, Object> map);
	List<RentalCalendarDTO> getSchedules(HashMap<String, String> map);
	RentalVO detail(int rental_num);
	RentalVO select();
  
  
  
  
  
	// member_num으로 회원의 대관내역 존재여부 확인
	int rentalExist(int member_num);
	
	// member_num으로 회원의 가장 최근 대관내역 1건 조회
	MyRentalDTO recentRental(int member_num);
	
	// member_num + startdate + enddate + startrow + endrow 받아서 기간설정 + 페이징 조회
	List<MyRentalDTO> myList(HashMap<String, Object> map);
	
	// 기간설정  + 페이징 조회된 내역 개수
	int listCount(HashMap<String, Object> map);
	
	// rental_num으로 대관상세내역 조회
	MyRentalDetailDTO myDetail(int rental_num);

}
