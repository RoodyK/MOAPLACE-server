package com.moaplace.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.moaplace.dto.ShowDTO;
import com.moaplace.dto.ShowDetailDTO;
import com.moaplace.dto.admin.show.MapperDetailDTO;
import com.moaplace.dto.admin.show.ShowDetailViewDTO;
import com.moaplace.dto.admin.show.ShowInsertRequestDTO;
import com.moaplace.dto.admin.show.ShowListDTO;
import com.moaplace.dto.admin.show.ShowUpdateDTO;
import com.moaplace.mapper.GradeMapper;
import com.moaplace.mapper.ShowImgMapper;
import com.moaplace.mapper.ShowMapper;
import com.moaplace.vo.GradeVO;
import com.moaplace.vo.ShowImgVO;
import com.moaplace.vo.ShowVO;

import lombok.extern.log4j.Log4j;

@Service
@Log4j
public class ShowService {
	
	@Autowired 
	private ShowMapper showMapper;
	@Autowired 
	private ShowImgMapper showImgMapper;
	@Autowired 
	private GradeMapper gradeMapper;
	
	////////////////////////연희 시작/////////////////////////
	
	@Transactional(rollbackFor = {Exception.class})
	public int showInsert(ShowInsertRequestDTO dto) {
		
		//base64 스트링 인코딩된 썸네일 이미지 데이터 byte로 변환
		String thumb = dto.getShow_thumbnail();
		
		//ShowVO에 생성자로 insert요청으로 들어온 DTO에서 뽑아온 정보 삽입 
		ShowVO showVO=new ShowVO(
				0,
				dto.getGenre_num(),
				dto.getHall_num(),
				dto.getShow_name(),
				dto.getShow_start(),
				dto.getShow_end(),
				dto.getShow_check(),
				null,null,
				dto.getShow_age(),
				dto.getIntermission(),
				dto.getRunning_time(),
				dto.getShow_thumbnail());
		
		
		//공연정보 인서트
		int showResult = showMapper.showInsert(showVO);
		
		
		//상세이미지 개수만큼 반복하면서 상세이미지 테이블에 데이터 입력 
		
		int showImgResult = 0;
		
		for(int i=0; i < dto.getShow_detail_img().length; i++) {
			
			ShowImgVO imgVO = new ShowImgVO(
					
				0, 
				showVO.getShow_num(),
				dto.getShow_detail_img()[i]
			);
			showImgResult += showImgMapper.showImgInsert(imgVO);
		}

		//좌석등급별가격 인서트
		//해시맵 안에 좌석등급별 GradeVO객체를 담은 arraylist를 저장
		HashMap<String, Object> gradeMap = new HashMap<String, Object>();
		
			ArrayList<GradeVO> gradeList=new ArrayList<GradeVO>();
			
				gradeList.add(new GradeVO("R",showVO.getShow_num(),dto.getRprice()));
				gradeList.add(new GradeVO("S",showVO.getShow_num(),dto.getSprice()));
				gradeList.add(new GradeVO("A",showVO.getShow_num(),dto.getAprice()));
		
			//좌석등급별가격 인서트
			gradeMap.put("list",gradeList);
		
		int gradeResult= gradeMapper.gradeInsert(gradeMap);

		return showResult+showImgResult+gradeResult;
	}
	
	
	public List<ShowListDTO> showList(HashMap<String, Object> map){
	
		List<ShowListDTO> list = showMapper.showList(map);
		
		return list;
	}
	
	public int currentCnt(HashMap<String, Object> map) {
		
		return showMapper.currentCount(map);
	}
	
	
	public ShowDetailViewDTO showDetail(int num) {

		List<MapperDetailDTO> list = showMapper.showDetail(num);
		List<GradeVO> gradeVOs = gradeMapper.gradeSelect(num);		
		ShowDetailViewDTO dto = new ShowDetailViewDTO();
		
		dto.setNum(list.get(0).getNum());
		dto.setTitle(list.get(0).getTitle());
		dto.setHall(list.get(0).getHall());
		dto.setGenre(list.get(0).getGenre());
		dto.setStatus(list.get(0).getStatus());
		dto.setRunningTime(list.get(0).getRunningTime());
		dto.setIntermission(list.get(0).getIntermission());
		dto.setSeats(list.get(0).getSeats());
		dto.setAge(list.get(0).getAge());
		dto.setStartDate(list.get(0).getStartDate());
		dto.setEndDate(list.get(0).getEndDate());
		dto.setBlockStartDate(list.get(0).getBlockStartDate());
		dto.setBlockEndDate(list.get(0).getBlockEndDate());
		dto.setThumbnail(list.get(0).getThumbnail());
		
		
		ArrayList<String> arrList = new ArrayList<>();
		for(int i=0; i < list.size(); i++) {
			MapperDetailDTO reqDto =list.get(i);
			arrList.add(reqDto.getDetailImgs());
		}
		
		dto.setDetailImgs(arrList);
		
		for(GradeVO gvo : gradeVOs) {
			if(gvo.getGrade_seat().equals("R"))dto.setRprice(gvo.getGrade_price());
			if(gvo.getGrade_seat().equals("S"))dto.setSprice(gvo.getGrade_price());
			if(gvo.getGrade_seat().equals("A"))dto.setAprice(gvo.getGrade_price());
		}
		return dto;
	}
	
	@Transactional(rollbackFor = {Exception.class})
	public int showUpdate(ShowUpdateDTO dto){
		
		int hallNum = 0;
		int genreNum=0;
		
		switch(dto.getHall()) {
		case "모던홀" : hallNum=1;break;
		case "오케스트라홀" : hallNum=2;break;
		case "아트홀" : hallNum=3;break;
		}
		
		switch(dto.getGenre()) {
		case "연극" : genreNum = 1; break;
		case "뮤지컬" : genreNum = 2; break;
		case "대중음악" : genreNum = 3; break;
		case "기악" : genreNum = 4; break;
		case "성악" : genreNum = 5; break;
		case "오페라" : genreNum = 6; break;
		case "무용" : genreNum = 7; break;
		}
		
		//ShowVO에 생성자로 insert요청으로 들어온 DTO에서 뽑아온 정보 삽입 
		ShowVO showVO=new ShowVO(
				dto.getShowNum(),
				genreNum,
				hallNum,
				dto.getTitle(),
				dto.getStartDate(),
				dto.getEndDate(),
				dto.getGoing(),
				dto.getPauseStart(),
				dto.getPauseEnd(),
				dto.getShowAge(),
				dto.getIntermission(),
				dto.getRunningTime(),
				dto.getShowThumbnail());
		
		//공연정보 업데이트
		int showResult = showMapper.showUpdate(showVO);
		
		//해당 공연번호의 상세이미지 모두 삭제 후 다시 상세이미지 개수만큼 반복하면서 상세이미지 테이블에 데이터 입력 
		
		int delImgData = showImgMapper.showImgUpDel(dto.getShowNum());
		int showImgResult = 0;
		
		for(int i=0; i < dto.getImgDetails().length; i++) {
			
			ShowImgVO imgVO = new ShowImgVO(
				0, 
				dto.getShowNum(),
				dto.getImgDetails()[i]
			);
			showImgResult += showImgMapper.showImgInsert(imgVO);
		}
		
		//좌석등급별가격 업데이트
		//해시맵 안에 좌석등급별 GradeVO객체를 담은 arraylist를 저장
		HashMap<String, Object> gradeMap = new HashMap<String, Object>();
		
		ArrayList<GradeVO> gradeList=new ArrayList<GradeVO>();
		gradeList.add(new GradeVO("R",dto.getShowNum(),dto.getRprice()));
		gradeList.add(new GradeVO("S",dto.getShowNum(),dto.getSprice()));
		gradeList.add(new GradeVO("A",dto.getShowNum(),dto.getAprice()));
		
		//좌석등급별가격 해시맵에 담기
		gradeMap.put("list",gradeList);
		int gradeResult= gradeMapper.gradeUpdate(gradeMap);

		return showResult+delImgData+showImgResult+gradeResult;	

	}
	
	
	public int countRow() {
		
		return showMapper.firstCntRow();
	}
	
	@Transactional( rollbackFor = {Exception.class})
	public int deleteShow(int num) {
		
	int n = 0;
		n += gradeMapper.deleteGrade(num);
		n += showImgMapper.deleteShowImg(num);
		n += showMapper.deleteShow(num);	
		
		return n;
	}

	////////////////////////연희 끝/////////////////////////
	
	public List<ShowDTO> list(HashMap<String, Object> map) {

		return showMapper.list(map);
	}

	
	public int count(HashMap<String, Object> map) {
		
		return showMapper.count(map);
	}
	
	public List<ShowDetailDTO> detail(int show_num) {
		
		return showMapper.detail(show_num);
	}
	
}

