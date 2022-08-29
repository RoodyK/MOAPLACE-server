package com.moaplace.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.moaplace.dto.ShowDTO;
import com.moaplace.dto.admin.show.MapperDetailDTO;
import com.moaplace.dto.admin.show.ShowDetailViewDTO;
import com.moaplace.dto.admin.show.ShowInsertRequestDTO;
import com.moaplace.dto.admin.show.ShowListDTO;
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
	
	@Transactional(rollbackFor = {Exception.class})
	public int showInsert(ShowInsertRequestDTO dto) {
		
		//base64 스트링 인코딩된 썸네일 이미지 데이터 byte로 변환
		byte[] thumb = dto.getShow_thumbnail().getBytes();
		
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
				thumb);
		
		log.info("인서트 전 : " + showVO.getShow_num());
		
		//공연정보 인서트
		int showResult = showMapper.showInsert(showVO);
		
		log.info("인서트 후 : "+showVO.getShow_num());
		
		//상세이미지 개수만큼 반복하면서 상세이미지 테이블에 데이터 입력 
		
		int showImgResult = 0;
		
		for(int i=0; i < dto.getShow_detail_img().length; i++) {
			
			ShowImgVO imgVO = new ShowImgVO(
					
				0, 
				showVO.getShow_num(),
				dto.getShow_detail_img()[i].getBytes()
			);
			
			showImgResult += showImgMapper.showImgInsert(imgVO);
			
		}
		
		//좌석등급별가격 인서트
		
		//해시맵 안에 좌석등급별 GradeVO객체를 담은 arraylist를 저장
		HashMap<String, Object> gradeMap = new HashMap<String, Object>();
		
			ArrayList<GradeVO> gradeList=new ArrayList<GradeVO>();
			
				gradeList.add(new GradeVO("R",showVO.getShow_num(),dto.getRPrice()));
				gradeList.add(new GradeVO("S",showVO.getShow_num(),dto.getSPrice()));
				gradeList.add(new GradeVO("A",showVO.getShow_num(),dto.getAPrice()));
		
			//좌석등급별가격 인서트
			gradeMap.put("list",gradeList);
		
		int gradeResult= gradeMapper.gradeInsert(gradeMap);

		return showResult+showImgResult+gradeResult;
		
	}
	
	
	public List<ShowListDTO> showList(HashMap<String, Object> map){
		
		List<ShowListDTO> list = showMapper.showList(map);
		
		int cntRow = showMapper.cntRow();
		log.info(cntRow);
		
		return list;
	}
	
	public ShowDetailViewDTO showDetail(int num) {
		
		List<MapperDetailDTO> list = showMapper.showDetail(num);
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
		dto.setThumbnail(new String(list.get(0).getThumbnail()));
		
		ArrayList<String> arrList = new ArrayList<>();
		
		for(int i=0; i < list.size(); i++) {
			MapperDetailDTO reqDto =list.get(i);
			arrList.add(new String(reqDto.getDetailImgs()));
		}
		
		dto.setDetailImgs(arrList);
		
		return dto;
	}
	
	public int countRow() {
		
		return showMapper.cntRow();
	}
	
	
	
	public List<ShowDTO> list(HashMap<String, Object> map) {

		
		List<ShowVO> list = showMapper.list(map);
		
		List<ShowDTO> list2 = new ArrayList<>();
		
		for(ShowVO vo : list) {
			log.info(vo.getShow_thumbnail());
			list2.add(new ShowDTO(vo.getShow_num(), vo.getGenre_num(), vo.getHall_num(), vo.getShow_name(), vo.getShow_start(), vo.getShow_end(), vo.getShow_check(), new String(vo.getShow_thumbnail())));
		}
		
		return list2;
	}
	
	public int count() {
		return showMapper.count();

	}
	
}

