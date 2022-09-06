package com.moaplace.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.moaplace.vo.AllSeatVO;

@Mapper
public interface AllSeatMapper {

	//좌석 선택 페이지  상연일정(스케줄)번호로 이미 예매된 좌석 불러오기
	List<String> getBookingSeat(int schedule_num);
	
	// 예매취소 좌석 삭제
	int cancleSeat(int booking_num);

	//예매 완료
	int seatInsert(AllSeatVO vo);
	
}