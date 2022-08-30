package com.moaplace.mapper;

import java.util.HashMap;
import java.util.List;

import com.moaplace.dto.admin.show.ScheduleListDTO;
import com.moaplace.vo.ScheduleVO;

public interface ScheduleMapper {
	
	int scheduleInsert (ScheduleVO vo);
	List<ScheduleListDTO> scheduleList(HashMap<String, Object> map);
	int scheduleAllCount();
}			
