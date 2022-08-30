package com.moaplace.service;

import java.util.HashMap;
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
	
	//등록, 수정할 때 공연정보 검색해서 리스트 받아오기
	public List<ShowPartInfo> searchShow(String title){
		
		return showMapper.searchShow(title);
	}
	
	//공연일정 등록하기 (들어온 공연 시간만큼 순차적으로 vo에 담아 인서트)
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
	
	//페이징처리 위한 총 공연일정 구하기
	public int sheduleCount() {
		
		return scheduleMapper.scheduleAllCount();
	}
	
	
	//공연일정 보여주기 
	public List<ScheduleListDTO> sheduleList(HashMap<String, Object> map){
		
		return scheduleMapper.scheduleList(map);
	}
	
}
