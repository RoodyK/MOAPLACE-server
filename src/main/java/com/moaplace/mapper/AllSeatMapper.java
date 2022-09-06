package com.moaplace.mapper;

import java.util.List;

public interface AllSeatMapper {

	//좌석 선택 페이지  상연일정(스케줄)번호로 이미 예매된 좌석 불러오기
	List<String> getBookingSeat(int schedule_num);
	
}
