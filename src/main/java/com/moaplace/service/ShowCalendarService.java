package com.moaplace.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.moaplace.dto.CalendarDateDTO;
import com.moaplace.mapper.ShowMapper;

@Service
public class ShowCalendarService {

	@Autowired
	private ShowMapper mapper;
	
	public List<CalendarDateDTO> viewCalendar(int month) {
		
		return mapper.monthSelect(month);
	}
	
	public String returnThumbnail(int num) {
		
		return mapper.returnThumb(num);
	}
}
