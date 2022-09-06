package com.moaplace.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.moaplace.mapper.PaymentMapper;
import com.moaplace.vo.PaymentVO;

@Service
public class PaymentService {
	
	@Autowired private PaymentMapper mapper;
	
	// 예매번호로 조회한 경제상태 결제취소(예매취소)로 update
	public int ticketCancle(int booking_num) {
		return mapper.ticketCancle(booking_num);
	}
	
	

}
