package com.moaplace.service;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.moaplace.dto.AdminBookingDetailDTO;
import com.moaplace.dto.AdminBookingDetailResponseDTO;
import com.moaplace.dto.AdminBookingListDTO;
import com.moaplace.mapper.BookingMapper;


@Service
public class AdminBookingService {
	
	@Autowired
	private BookingMapper mapper;
	
	//관리자 예매리스트 조회
	public List<AdminBookingListDTO> adminBookingList(HashMap<String, Object> map){
		
		return mapper.adminBookingList(map);
	}
	
	//총 예매 행수
	public int adminAllBookingCnt() {
		
		return mapper.adminAllBookingCnt();
	}
	
	//관리자 예매목록 검색, 모아보기 후의 총 행수
	public int currentAdminBookingCnt(HashMap<String, Object> map) {
		
		return mapper.currentAdminBookingCnt(map);
	}
	
	//관리자 예매 상세조회
	public AdminBookingDetailResponseDTO selectAdminBookingDetail(int bookingNum) {
		
		List<AdminBookingDetailDTO> reqDto= mapper.selectAdminBookingDetail(bookingNum);
		
		AdminBookingDetailResponseDTO dto = new AdminBookingDetailResponseDTO();
		dto.setScheduleDate(reqDto.get(0).getScheduleDate());
		dto.setScheduleTime(reqDto.get(0).getScheduleTime());
		dto.setBookingCount(reqDto.get(0).getBookingCount());
		String ticket ="";
		int allPrice = 0;
		for( int i = 0; i <reqDto.size(); i++) {
			
			ticket +=
					reqDto.get(i).getTicketAge() + " " +
					reqDto.get(i).getTicketGrade() + "석 " + 
					reqDto.get(i).getTicket_count() + "장";
			if(i != reqDto.size()-1) ticket += ", ";
			allPrice += reqDto.get(i).getTicketPrice();
		}
		dto.setTicketDetail(ticket);
		dto.setBookingSeat(reqDto.get(0).getBookingSeat());
		dto.setPaymentDate(reqDto.get(0).getPaymentDate());
		dto.setAllticketPrice(allPrice);
		dto.setUsePoint(reqDto.get(0).getUsePoint());
		dto.setPaymentMethod(reqDto.get(0).getPaymentMethod());
		dto.setBookingPrice(reqDto.get(0).getBookingPrice());
		dto.setPaymentStatus(reqDto.get(0).getPaymentStatus());
		dto.setMerchantUid(reqDto.get(0).getMerchantUid());
		dto.setImpUid(reqDto.get(0).getImpUid());
		
		return dto;
	}

}
