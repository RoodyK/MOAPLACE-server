package com.moaplace.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.moaplace.dto.BookingSelectViewDto;
import com.moaplace.mapper.ShowMapper;

@Service
public class BookingSelectService {

	@Autowired
	private ShowMapper mapper;
	
	public List<BookingSelectViewDto> ShowbookingSelect(int num) {
		
		return mapper.ShowbookingSelect(num);
	}
	
	public String returnThumbnail(int num) {
		
		return mapper.returnThumb(num);
	}
	
	
}
