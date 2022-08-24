package com.moaplace.mapper;

import java.util.HashMap;
import java.util.List;

import com.moaplace.vo.RentalVO;

public interface RentalMapper {
	
	int insert(RentalVO vo);
	int getCount(HashMap<String, Object> map);
	List<RentalVO> list(HashMap<String, Object> map);
	int updateState(HashMap<String, Object> data);
	
}
