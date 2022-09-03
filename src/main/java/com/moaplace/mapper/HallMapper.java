package com.moaplace.mapper;

import java.util.List;

import com.moaplace.dto.GradePriceDTO;
import com.moaplace.dto.HallSeatDTO;
import com.moaplace.dto.TicketGradeDTO;

public interface HallMapper {

	// 좌석 선택 페이지  공연장 좌석수 불러오기
	HallSeatDTO getHallSeats(int hall_num);
	
	//좌석 선택 페이지 좌석 등급별 행수 조회
	List<TicketGradeDTO> getTicketGrade(int hall_num);
	
	//좌석 등급별 가격 조회
	List<GradePriceDTO> getGradePrice(int show_num);
}
