package com.moaplace.mapper;

import java.util.HashMap;
import java.util.List;

import com.moaplace.dto.MyReviewDTO;
import com.moaplace.dto.ReviewDTO;
import com.moaplace.vo.ReviewVO;

public interface ReviewMapper {

	// member_num + startRow + endRow로 관람후기 리스트 불러오기 + 페이징
	List<MyReviewDTO> myList(HashMap<String, Object> map);
	
	// member_num으로 관람후기 내역 개수 조회
	int myListCount(int member_num);
	
	// show_num으로 관람후기 내역 개수 조회
	int reviewCount(int show_num);
	
	// show_num으로 관람후기 내역 조회
	List<ReviewDTO> reviewList(HashMap<String, Object> map);
	
	// 관람후기 등록
	int reviewInsert(ReviewVO vo);
	
	// 후기 삭제
	int reviewDelete(int review_num);
	
	// 후기 조회
	ReviewVO reviewSelect(int review_num);
	
	// 후기 수정
	int reviewUpdate(ReviewVO vo);
}
