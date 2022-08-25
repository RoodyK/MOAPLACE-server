package com.moaplace.mapper;

import java.util.HashMap;
import java.util.List;

import com.moaplace.vo.ShowVO;

public interface ShowMapper {
	List<ShowVO> list(HashMap<String, Object> map);
	int count();
}
