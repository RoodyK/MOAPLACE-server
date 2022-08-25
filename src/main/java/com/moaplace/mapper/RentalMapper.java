package com.moaplace.mapper;

import com.moaplace.vo.RentalVO;

public interface RentalMapper {
	int insert(RentalVO vo);
	RentalVO select();
}
