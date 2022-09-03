package com.moaplace.service;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.moaplace.dto.AdminBookingListDTO;
import com.moaplace.mapper.BookingMapper;

import lombok.extern.log4j.Log4j;

@Log4j
@Service
public class AdminBookingService {
	
	@Autowired
	private BookingMapper mapper;
	
	public List<AdminBookingListDTO> adminBookingList(HashMap<String, Object> map){
		
		log.info(map);
		return mapper.adminBookingList(map);
	}
	
	public int adminAllBookingCnt() {
		
		return mapper.adminAllBookingCnt();
	}
	
	public int currentAdminBookingCnt(HashMap<String, Object> map) {
		
		return mapper.currentAdminBookingCnt(map);
	}

}
