package com.moaplace.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.moaplace.dto.MyRentalDTO;
import com.moaplace.mapper.RentalMapper;

@Service
public class RentalService {

	@Autowired private RentalMapper mapper;
	
	// member_num으로 회원의 대관내역 존재여부 확인
	public boolean rentalExist(int member_num) {
		boolean exist = false;
		if(mapper.rentalExist(member_num) > 0) {
			exist = true;
		}
		return exist;
	}
	
	// member_num으로 회원의 가장 최근 대관내역 1건 조회
	public MyRentalDTO recentRental(int member_num) {
		return mapper.recentRental(member_num);
	}
}
