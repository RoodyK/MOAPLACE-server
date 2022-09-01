package com.moaplace.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.moaplace.dto.admin.show.AdminScheduleDetailDTO;
import com.moaplace.dto.admin.show.AdminScheduleDetailInfoDTO;
import com.moaplace.dto.admin.show.AdminScheduleDetailTimeDTO;
import com.moaplace.dto.admin.show.MapperDetailDTO;
import com.moaplace.dto.admin.show.ScheduleInsertRequestDTO;
import com.moaplace.dto.admin.show.ScheduleListDTO;
import com.moaplace.dto.admin.show.ScheduleUpdateRequestDTO;
import com.moaplace.dto.admin.show.ShowDetailViewDTO;
import com.moaplace.dto.admin.show.ShowPartInfoDTO;
import com.moaplace.dto.admin.show.ShowUpdateDTO;
import com.moaplace.mapper.ScheduleMapper;
import com.moaplace.mapper.ShowMapper;
import com.moaplace.vo.GradeVO;
import com.moaplace.vo.ScheduleVO;
import com.moaplace.vo.ShowImgVO;
import com.moaplace.vo.ShowVO;

import lombok.extern.log4j.Log4j;

@Service
@Log4j
public class AdminTicketService {
	
	@Autowired 
	private ShowMapper showMapper;
	
	@Autowired
	private ScheduleMapper scheduleMapper;
	
	//등록, 수정할 때 공연정보 검색해서 리스트 받아오기
	public List<ShowPartInfoDTO> searchShow(String title){
		
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
	
	//페이징처리 위해 검색 후 총 공연일정 행수 구하기 
	public int currentCount(HashMap<String, Object> map) {
		
		return scheduleMapper.currentCount(map);
	}
	
	//일정상세정보 조회하기
	public HashMap<String, Object> showDetail(HashMap<String, Object> map) {
		
		List<AdminScheduleDetailDTO> list = scheduleMapper.scheduleDetail(map);

		ArrayList<AdminScheduleDetailTimeDTO> arrTime = new ArrayList<>();
		
		AdminScheduleDetailInfoDTO detailInfoDTO = new AdminScheduleDetailInfoDTO();
		detailInfoDTO.setNum(list.get(0).getNum());
		detailInfoDTO.setTitle(list.get(0).getTitle()); 
		detailInfoDTO.setShowDate(list.get(0).getShowDate()); 
		
		for(int i=0; i < list.size(); i++) {
			AdminScheduleDetailDTO reqDto = list.get(i);
			AdminScheduleDetailTimeDTO timeDto = new AdminScheduleDetailTimeDTO();
			 timeDto.setTimeRow(reqDto.getTimeRow());
			 timeDto.setDateTime(reqDto.getDateTime());
			 timeDto.setDateStatus(reqDto.getDateStatus());
			arrTime.add(timeDto);
		}
		HashMap<String, Object> listMap = new HashMap<String, Object>();
		listMap.put("detailInfoDTO",detailInfoDTO);
		listMap.put("arrTime",arrTime);

		return listMap;
	}
	
	public HashMap<String, Object> selectUpdate(int showNum, String showDate) {
		
		
		ShowPartInfoDTO dto= showMapper.searchShowNumber(showNum);
		log.info("-----------"+dto);
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("showNum",showNum);
		map.put("showDate",showDate);

		int bookingSeat = scheduleMapper.bookingSeatCheck(map);
		
		boolean using =false;
		
		List<AdminScheduleDetailDTO> list = scheduleMapper.scheduleDetail(map);
		
		ArrayList<AdminScheduleDetailTimeDTO> arrTime = new ArrayList<>();
		for(int i=0; i < list.size(); i++) {
			AdminScheduleDetailDTO reqDto = list.get(i);
			AdminScheduleDetailTimeDTO timeDto = new AdminScheduleDetailTimeDTO();
			 timeDto.setTimeRow(reqDto.getTimeRow());
			 timeDto.setDateTime(reqDto.getDateTime());
			 timeDto.setDateStatus(reqDto.getDateStatus());
			arrTime.add(timeDto);
		}
		
		if(bookingSeat > 0) {
			using = true;
		}
		
		HashMap<String, Object> listMap = new HashMap<String, Object>();
		map.put("showNum",showNum);
		map.put("showtitle",dto.getTitle());
		map.put("runningTime",dto.getRunningTime());
		map.put("intermission",dto.getIntermission());
		map.put("showDate",showDate);
		map.put("showStatus",dto.getStatus());
		map.put("arrTime",arrTime);
		map.put("using",using);

			return map;
	}
	
	@Transactional(rollbackFor = {Exception.class})
	public int scheduleUpdate(ScheduleUpdateRequestDTO dto){
		
		HashMap<String, Object> selMap = new HashMap<String, Object>();
		selMap.put("showNum",dto.getShowNum());
		selMap.put("showDate",dto.getShowDate());
		
//		int bookingSeat = scheduleMapper.bookingSeatCheck(selMap);
		int result = 0;
		
		for(int i = 0; i < dto.getList().size(); i++) {
			AdminScheduleDetailTimeDTO timeDto = dto.getList().get(i);
			ScheduleVO vo = new ScheduleVO(
					0,dto.getShowNum(),
					timeDto.getDateTime(),dto.getShowDate(), timeDto.getDateStatus());
			result += scheduleMapper.scheduleUpdate(vo);
		
//		if(bookingSeat>0) {
//			
//			scheduleMapper.deleteSchedule(selMap);
//			
//			for(int i = 0; i < dto.getList().size(); i++) {
//				AdminScheduleDetailTimeDTO timeDto = dto.getList().get(i);
//				ScheduleVO vo = new ScheduleVO(
//						0,dto.getShowNum(),
//						timeDto.getDateTime(),dto.getShowDate(), timeDto.getDateStatus());
//				result += scheduleMapper.scheduleInsert(vo);
//			}
//			
//		}else {
			

//			}
		}
		
		
		return result;
	}
}
	

	

