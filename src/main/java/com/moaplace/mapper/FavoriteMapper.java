package com.moaplace.mapper;

import java.util.HashMap;
import java.util.List;

import com.moaplace.dto.MyFavoriteDTO;

public interface FavoriteMapper {

	// member_num으로 관심공연 조회 + 인덱스 + 페이징
	List<MyFavoriteDTO> myList(HashMap<String, Object> map);
	
	// member_num으로 관심공연 개수 조회
	int listCount(int member_num);
}
