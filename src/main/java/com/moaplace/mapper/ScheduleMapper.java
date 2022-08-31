package com.moaplace.mapper;

import java.util.List;

import com.moaplace.dto.ScheduleDTO;

public interface ScheduleMapper {
	
	List<ScheduleDTO> schedule(int show_num);
}
