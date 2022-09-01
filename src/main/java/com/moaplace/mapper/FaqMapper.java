package com.moaplace.mapper;

import java.util.HashMap;
import java.util.List;

import com.moaplace.dto.FaqListDTO;
import com.moaplace.vo.FaqVO;

public interface FaqMapper {
	
	int insert(FaqVO vo);
	List<FaqListDTO> list(HashMap<String, Object> map);
	int listCnt(HashMap<String, Object> map);
	FaqVO detail(int faq_num);
	int update(FaqVO vo);
	int delete(int faq_num);
	
}
