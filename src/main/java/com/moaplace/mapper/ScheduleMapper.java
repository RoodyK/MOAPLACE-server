package com.moaplace.mapper;

import java.util.HashMap;
import java.util.List;

import com.moaplace.dto.ScheduleDTO;
import com.moaplace.dto.admin.show.AdminScheduleDetailDTO;
import com.moaplace.dto.admin.show.ScheduleListDTO;
import com.moaplace.vo.ScheduleVO;

public interface ScheduleMapper {
	
	//공연일정 등록
	int scheduleInsert (ScheduleVO vo);
	
	//공연일정 리스트 조회
	List<ScheduleListDTO> scheduleList(HashMap<String, Object> map);
	
	//모든 공연일정 갯수 조회
	int scheduleAllCount();
	
	//검색, 모아보기 후 공연일정 갯수 조회
	int currentCount(HashMap<String, Object> map);
	
	//공연일정 상세보기
	List<AdminScheduleDetailDTO> scheduleDetail(HashMap<String, Object> map);
	
	//공연일정 수정
	int scheduleUpdate (ScheduleVO vo);
	
	//공연일정 삭제
	int deleteSchedule (HashMap<String, Object> map);
	

  
  List<ScheduleDTO> schedule(int show_num);
	
}			

