package com.moaplace.service;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.moaplace.dto.MyReviewDTO;
import com.moaplace.dto.ReviewDTO;
import com.moaplace.mapper.ReviewMapper;
import com.moaplace.vo.ReviewVO;

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
	
	// show_num으로 관람후기 내역 개수 조회
	public int reviewCount(int show_num) {
		return mapper.reviewCount(show_num);
	};
	
	// show_num으로 관람후기 내역 개수 조회
	public List<ReviewDTO> reviewList(HashMap<String, Object> map) {
		return mapper.reviewList(map);
	};
	
	// 관람후기 등록
	public int reviewInsert(ReviewVO vo) {
		return mapper.reviewInsert(vo);
	};
	
	// 후기 삭제
	public ReviewVO reviewSelect(int review_num) {
		return mapper.reviewSelect(review_num);
	};
	
	// 후기 조회
	public int reviewDelete(int review_num) {
		return mapper.reviewDelete(review_num);
	};
	
	// 후기 수정
	public int reviewUpdate(ReviewVO vo) {
		return mapper.reviewUpdate(vo);
	};
}
