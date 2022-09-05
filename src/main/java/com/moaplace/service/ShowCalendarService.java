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
	
	//연, 월별로 일정 데이터 리스트 가져오기
	public List<CalendarDateDTO> viewCalendar(int month) {
		
		return mapper.monthSelect(month);
	}
	
	//미리보기로 보여줄 썸네일 데이터 가져오기
	public String returnThumbnail(int num) {
		
		return mapper.returnThumb(num);
	}
}
