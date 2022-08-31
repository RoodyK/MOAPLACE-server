package com.moaplace.mapper;

import java.util.HashMap;
import java.util.List;

import com.moaplace.dto.AdminListDTO;
import com.moaplace.vo.AdminNoticeVO;

//매핑되는 XML매퍼파일과 동일한 패키지/동일한 인터페이스명으로 만들기
//매퍼 파일의 id 속성과 동일한 이름으로 추상메소드 만들기
public interface AdminNoticeMapper {
	public int insert(AdminNoticeVO vo);
	public List<AdminListDTO> listAll(HashMap<String,Object> map);
	public int getCount(HashMap<String,Object> map);
}
