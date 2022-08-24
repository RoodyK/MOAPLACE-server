package com.moaplace.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.moaplace.dto.MyRentalDTO;

@Mapper
public interface RentalMapper {
	
	// member_num으로 회원의 대관내역 존재여부 확인
	int rentalExist(int member_num);
	
	// member_num으로 회원의 가장 최근 대관내역 1건 조회
	MyRentalDTO recentRental(int member_num);
}
