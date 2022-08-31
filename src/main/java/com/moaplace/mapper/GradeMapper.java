package com.moaplace.mapper;


import java.util.HashMap;
import java.util.List;

import com.moaplace.dto.GradeDTO;


public interface GradeMapper {
	int gradeInsert(HashMap<String, Object> map);

	List<GradeDTO> grade(int show_num);
}
