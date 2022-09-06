package com.moaplace.service;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.moaplace.dto.MyFavoriteDTO;
import com.moaplace.mapper.FavoriteMapper;
import com.moaplace.vo.FavoriteVO;

@Service
public class FavoriteService {
	
	@Autowired
	private FavoriteMapper mapper;

	// member_num으로 관심공연 조회 + 인덱스 + 페이징
	public List<MyFavoriteDTO> myList(HashMap<String, Object> map) {
		return mapper.myList(map);
	}
	
	// member_num으로 관심공연 개수 조회
	public int listCount(int member_num) {
		return mapper.listCount(member_num);
	}
	
	// 등록한 관심공연 삭제
	public int delete(HashMap<String, Object> map) {
		return mapper.delete(map);
	}
	
	// 관심공연 등록
	public int insert(FavoriteVO vo) {
		return mapper.insert(vo);
	}
}
