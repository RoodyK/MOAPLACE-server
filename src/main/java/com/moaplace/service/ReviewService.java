package com.moaplace.service;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.moaplace.dto.MyReviewDTO;
import com.moaplace.mapper.ReviewMapper;

@Service
public class ReviewService {

	@Autowired
	private ReviewMapper mapper;
	
	// member_num + startRow + endRow로 관람후기 리스트 불러오기 + 페이징
	public List<MyReviewDTO> myList(HashMap<String, Object> map) {
		return mapper.myList(map);
	};
		
	// member_num으로 관람후기 내역 개수 조회
	public int myListCount(int member_num) {
		return mapper.myListCount(member_num);
	};
}
