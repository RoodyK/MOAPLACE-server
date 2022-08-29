package com.moaplace.mapper;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PaymentMapper {
	
	// 예매번호로 조회한 경제상태 결제취소(예매취소)로 update
	int ticketCancle(int booking_num);

}
