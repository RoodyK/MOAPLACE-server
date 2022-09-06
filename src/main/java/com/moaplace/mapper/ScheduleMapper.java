package com.moaplace.mapper;

import java.util.HashMap;
import java.util.List;

import com.moaplace.dto.ResidualDTO;
import com.moaplace.dto.ScheduleDTO;
import com.moaplace.dto.admin.show.AdminScheduleDetailDTO;
import com.moaplace.dto.admin.show.ScheduleListDTO;
import com.moaplace.vo.ScheduleVO;

public interface ScheduleMapper {
	
	int scheduleInsert (ScheduleVO vo);
	
	List<ScheduleListDTO> scheduleList(HashMap<String, Object> map);
	
	int scheduleAllCount();
	
	int currentCount(HashMap<String, Object> map);
	
	List<AdminScheduleDetailDTO> scheduleDetail(HashMap<String, Object> map);
	
	int deleteSchedule (HashMap<String, Object> map);
	
	int scheduleUpdate (ScheduleVO vo);
	
	int bookingSeatCheck(HashMap<String, Object> map);
  
	List<ScheduleDTO> schedule(int show_num);
	
	int Rounds(HashMap<String, Object> map);
  
	List<ResidualDTO> selectlist(int show_num);
	
	List<Integer> rowinfo(int show_num);
	
}			