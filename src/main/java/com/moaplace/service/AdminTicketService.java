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
import com.moaplace.dto.admin.show.ScheduleInsertRequestDTO;
import com.moaplace.dto.admin.show.ScheduleListDTO;
import com.moaplace.dto.admin.show.ScheduleUpdateRequestDTO;
import com.moaplace.dto.admin.show.ShowPartInfoDTO;
import com.moaplace.mapper.ScheduleMapper;
import com.moaplace.mapper.ShowMapper;
import com.moaplace.vo.ScheduleVO;


@Service
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
	public HashMap<String, Object> showDetail(int showNum, String showDate) {
		
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("showNum",showNum);
		map.put("showDate",showDate);
		
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
			 timeDto.setScheduleNum(reqDto.getScheduleNum());
			 timeDto.setDateTime(reqDto.getDateTime());
			 timeDto.setDateStatus(reqDto.getDateStatus());
			arrTime.add(timeDto);
		}
		HashMap<String, Object> listMap = new HashMap<String, Object>();
		listMap.put("detailInfoDTO",detailInfoDTO);
		listMap.put("arrTime",arrTime);

		return listMap;
	}
	// 업데이트하기 위해 조인된 정보 받아오기
	public HashMap<String, Object> selectUpdate(int showNum, String showDate) {
		
		
		ShowPartInfoDTO dto= showMapper.searchShowNumber(showNum);

		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("showNum",showNum);
		map.put("showDate",showDate);
		
		List<AdminScheduleDetailDTO> list = scheduleMapper.scheduleDetail(map);
		
		ArrayList<AdminScheduleDetailTimeDTO> arrTime = new ArrayList<>();
		
		for(int i=0; i < list.size(); i++) {
			
			AdminScheduleDetailDTO reqDto = list.get(i);
			AdminScheduleDetailTimeDTO timeDto = new AdminScheduleDetailTimeDTO();
			
			 timeDto.setTimeRow(reqDto.getTimeRow());
			 timeDto.setScheduleNum(reqDto.getScheduleNum());
			 timeDto.setDateTime(reqDto.getDateTime());
			 timeDto.setDateStatus(reqDto.getDateStatus());
			 arrTime.add(timeDto);
		}
		
		HashMap<String, Object> listMap = new HashMap<String, Object>();
		listMap.put("showNum",showNum);
		listMap.put("showtitle",dto.getTitle());
		listMap.put("runningTime",dto.getRunningTime());
		listMap.put("intermission",dto.getIntermission());
		listMap.put("showDate",showDate);
		listMap.put("showStatus",dto.getStatus());
		listMap.put("arrTime",arrTime);

			return listMap;
	}
	
	// 일정 수정, 기존에 있는 건 수정하고 없는 건 인서트하기
	@Transactional(rollbackFor = {Exception.class})
	public int scheduleUpdate(ScheduleUpdateRequestDTO dto){
		
		HashMap<String, Object> selMap = new HashMap<String, Object>();
		selMap.put("showNum",dto.getShowNum());
		selMap.put("showDate",dto.getShowDate());
		
		int result = 0;
		//수정할건 수정
		for(int i = 0; i < dto.getList().size(); i++) {
			AdminScheduleDetailTimeDTO timeDto = dto.getList().get(i);
			ScheduleVO vo = new ScheduleVO(
					timeDto.getScheduleNum(),
					dto.getShowNum(),
					timeDto.getDateTime(),
					dto.getShowDate(), 
					timeDto.getDateStatus());
			result += scheduleMapper.scheduleUpdate(vo);
		}
		//추가할건 추가
		for(int i = 0; i < dto.getAddList().size(); i++) {
			AdminScheduleDetailTimeDTO timeDto = dto.getAddList().get(i);
			ScheduleVO vo = new ScheduleVO(
					0,dto.getShowNum(),
					timeDto.getDateTime(),dto.getShowDate(), timeDto.getDateStatus());
			result += scheduleMapper.scheduleInsert(vo);
		}
		
		return result;
	}
	
	//공연일정 공연번호와 날짜로 공연일정 삭제
	public int deleteSchedule(int showNum, String scheduleDate) {
		
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put( "showNum", showNum);
		map.put( "showDate", scheduleDate);
		
		return scheduleMapper.deleteSchedule(map);
	}
}
	

	

