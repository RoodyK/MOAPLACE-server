package com.moaplace.mapper;

import java.util.HashMap;
import java.util.List;

import com.moaplace.dto.MyReviewDTO;

public interface ReviewMapper {

	// member_num + startRow + endRow로 관람후기 리스트 불러오기 + 페이징
	List<MyReviewDTO> myList(HashMap<String, Object> map);
	
	// member_num으로 관람후기 내역 개수 조회
	int myListCount(int member_num);
}
