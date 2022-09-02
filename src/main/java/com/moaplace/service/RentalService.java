package com.moaplace.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import com.moaplace.dto.AdminChartDTO;
import com.moaplace.dto.MyRentalDTO;
import com.moaplace.dto.MyRentalDetailDTO;
import com.moaplace.dto.RentalCalendarDTO;
import com.moaplace.mapper.RentalAnswerMapper;
import com.moaplace.mapper.RentalMapper;
import com.moaplace.util.FileUtil;
import com.moaplace.vo.RentalAnswerVO;
import com.moaplace.vo.RentalVO;

@Service
public class RentalService {

	@Autowired 
	private RentalMapper mapper;
	@Autowired
	private RentalAnswerMapper answer_mapper;
	@Autowired
	private FileUtil fileUtil;
	@Value("${oracle.download}") 
    private String realPath;
	
	private Logger log = LoggerFactory.getLogger(this.getClass());
	
	// member_num으로 회원의 대관내역 존재여부 확인
	public boolean rentalExist(int member_num) {
		boolean exist = false;
		if(mapper.rentalExist(member_num) > 0) {
			exist = true;
		}
		return exist;
	}
	
	// member_num으로 회원의 가장 최근 대관내역 1건 조회
	public MyRentalDTO recentRental(int member_num) {
		return mapper.recentRental(member_num);
	}
	
	// member_num + startdate + enddate + startrow + endrow 받아서 기간설정 + 페이징 조회
	public List<MyRentalDTO> myList(HashMap<String, Object> map) {
		return mapper.myList(map);
	}
	
	// 기간설정  + 페이징 조회된 내역 개수
	public int listCount(HashMap<String, Object> map) {
		return mapper.listCount(map);
	}
	
	// rental_num으로 대관상세내역 조회
	public MyRentalDetailDTO myDetail(int rental_num) {
		return mapper.myDetail(rental_num);
	}
	
	// RentalVO로 대관상세내역 수정(트랜잭션처리해야함)
	@Transactional(rollbackFor = Exception.class)
	public int myUpdate(MultipartFile file, RentalVO vo) {
		
		try {
			if(file == null) {
				// 1. 파일 첨부 X : db수정
				log.info("multipartFile이 null임");
				
				mapper.myUpdate(vo);
				
			}else {
				// 2. 파일 첨부 O : 원본파일삭제 -> 새파일업로드 -> db수정
				log.info("multipartFile이 null이 아님");
				
				String path = realPath;
				
				// 원본파일 삭제
				String fileName = vo.getRental_savefilename();
				fileUtil.delete(fileName, "rental");
				
				// 새 파일 업로드
				String originFileName = file.getOriginalFilename();
				String saveFileName = UUID.randomUUID() + "_" + originFileName;
				InputStream is = file.getInputStream();
				File f = new File(path + File.separator + "rental" + File.separator + saveFileName);
				FileOutputStream fos = new FileOutputStream(f);
				FileCopyUtils.copy(is, fos);
				is.close();
				fos.close();
				long fileSize = file.getSize();
				mapper.myUpdate(new RentalVO(vo.getRental_num(), 0, vo.getHall_num(), vo.getRental_name(), vo.getRental_phone(), vo.getRental_email(), vo.getRental_title(), vo.getRental_genre(), vo.getRental_date(), vo.getRental_time(), originFileName, saveFileName, fileSize, vo.getRental_ownsname(), vo.getRental_ownsphone(), vo.getRental_ownemail(), vo.getRental_content(), null, null));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}
		
		return 1;
	}
  
	public int insert(RentalVO vo) {
		return mapper.insert(vo);
	}
	
	public int getCount(HashMap<String, Object> map) {
		return mapper.getCount(map);
	}
	
	public List<RentalVO> list(HashMap<String, Object> map){
		return mapper.list(map);
	}
	
	public int updateState (HashMap<String, Object> map) {
		return mapper.updateState(map);
	}
	
	public List<RentalCalendarDTO> getSchedules(HashMap<String, String> map){
		return mapper.getSchedules(map);
	}
	
	public RentalVO detail(int rental_num) {
		return mapper.detail(rental_num);
	}
	
	//대관신청 답변-----------------------------------------
	
	public int answerInsert(RentalAnswerVO vo) {
		return answer_mapper.insert(vo);
	}
	
	public RentalAnswerVO getAnswer(int rental_num) {
		return answer_mapper.getAnswer(rental_num);
	}
	
	public int answerUpdate(RentalAnswerVO vo) {
		return answer_mapper.update(vo);
	}
	
	public int answerDelete(int rental_num) {
		return answer_mapper.delete(rental_num);
	}
	
	public List<AdminChartDTO> rentalChart(Map<String, Object> map) {
		return mapper.rentalChart(map);
	}
}
