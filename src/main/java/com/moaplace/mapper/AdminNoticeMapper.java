package com.moaplace.mapper;

import java.util.HashMap;
import java.util.List;

import com.moaplace.dto.AdminDetailDTO;
import com.moaplace.dto.AdminListDTO;
import com.moaplace.dto.AdminNoticeDTO;
import com.moaplace.dto.AdminNoticeDetailDTO;

import com.moaplace.dto.MainNoticeDTO;
import com.moaplace.vo.AdminNoticeVO;

//매핑되는 XML매퍼파일과 동일한 패키지/동일한 인터페이스명으로 만들기
//매퍼 파일의 id 속성과 동일한 이름으로 추상메소드 만들기
public interface AdminNoticeMapper {

	public int insert(AdminNoticeVO vo);
  
	public List<AdminListDTO> listAll(HashMap<String,Object> map);
  
	public int getCount(HashMap<String,Object> map);

	public List<AdminDetailDTO> detail(int notice_num);
  
	public List<AdminNoticeDetailDTO> filelist(int notice_num);
  
	public AdminNoticeDetailDTO filedown(int notice_detail_num);
  
	public List<AdminListDTO> detaillist(int notice_num);
  
	public AdminNoticeDetailDTO selectfile(int notice_detail_num);
  
	public List<Integer> selectnum(int notice_num);
  
	public int delete(int notice_num);
  
	public int alldelete(int notice_num);
  
	public int deletefile(int notice_detail_num);
  
	public AdminListDTO selectdetail(int notice_num); 
	public int update(AdminNoticeVO vo);
	public int deleteone(int notice_num);
	
	//메인페이지 새소식 조회
	List<MainNoticeDTO> getNotice();
}
