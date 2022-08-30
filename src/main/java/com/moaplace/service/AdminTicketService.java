package com.moaplace.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.moaplace.dto.admin.show.ScheduleInsertRequestDTO;
import com.moaplace.dto.admin.show.ScheduleListDTO;
import com.moaplace.dto.admin.show.ShowPartInfo;
import com.moaplace.mapper.ScheduleMapper;
import com.moaplace.mapper.ShowMapper;
import com.moaplace.vo.ScheduleVO;

import lombok.extern.log4j.Log4j;

@Service
@Log4j
public class AdminTicketService {
	
	@Autowired 
	private ShowMapper showMapper;
	
	@Autowired
	private ScheduleMapper scheduleMapper;
	
	public List<ShowPartInfo> searchShow(String title){
		
		return showMapper.searchShow(title);
	}
	
	@Transactional
	public int insertSchedule(ScheduleInsertRequestDTO dto) {
		
		int result = 0;
		for(int i = 0; i< dto.getShowTime().length; i++) {
			ScheduleVO vo = new ScheduleVO(
					0,
					dto.getShowNum(),
					dto.getShowTime()[i],
					dto.getShowDate(),"Y");
			result += scheduleMapper.scheduleInsert(vo);
		}
		
		return result;
	}
	
	public List<ScheduleListDTO> sheduleList(){
		
		return scheduleMapper.scheduleList();
	}
	
}
