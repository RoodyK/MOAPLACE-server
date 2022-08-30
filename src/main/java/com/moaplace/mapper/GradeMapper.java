package com.moaplace.mapper;


import java.util.HashMap;
import java.util.List;

import com.moaplace.vo.GradeVO;


public interface GradeMapper {
	int gradeInsert(HashMap<String, Object> map);
	List<GradeVO> gradeSelect(int num);
	int gradeUpdate(HashMap<String, Object> map);
}
