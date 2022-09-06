package com.moaplace.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.moaplace.dto.BookingSelectViewDTO;
import com.moaplace.mapper.ShowMapper;

@Service
public class BookingSelectService {

	@Autowired
	private ShowMapper mapper;
	
	public List<BookingSelectViewDTO> ShowbookingSelect(int num) {
		
		return mapper.showBookingSelect(num);
	}
	
	public String returnThumbnail(int num) {
		
		return mapper.returnThumb(num);
	}
	
	
}
