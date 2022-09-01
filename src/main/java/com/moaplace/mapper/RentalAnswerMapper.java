package com.moaplace.mapper;


import com.moaplace.vo.RentalAnswerVO;

public interface RentalAnswerMapper {

	int insert(RentalAnswerVO vo);
	RentalAnswerVO getAnswer(int rental_num);
	int update(RentalAnswerVO vo);
	int delete(int rental_num);
}
