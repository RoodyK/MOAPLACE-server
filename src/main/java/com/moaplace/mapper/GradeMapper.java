package com.moaplace.mapper;


import java.util.HashMap;
import java.util.List;

import com.moaplace.vo.GradeVO;
import com.moaplace.dto.GradeDTO;


public interface GradeMapper {

	int gradeInsert(HashMap<String, Object> map);
  
	List<GradeVO> gradeSelect(int num);
  
	int gradeUpdate(HashMap<String, Object> map);

	List<GradeDTO> grade(int show_num);
	
	//공연 삭제될때 같이 삭제
	int deleteGrade(int num);
}
