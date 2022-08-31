package com.moaplace.mapper;

import java.util.HashMap;
import java.util.List;


import com.moaplace.dto.admin.show.MapperDetailDTO;
import com.moaplace.dto.admin.show.ShowListDTO;
import com.moaplace.dto.admin.show.ShowPartInfoDTO;
import com.moaplace.vo.ShowVO;

public interface ShowMapper {
	
	int showInsert(ShowVO vo);
  
	List<ShowListDTO> showList(HashMap<String, Object> map);
	
	List<MapperDetailDTO> showDetail(int num);
	
	List<ShowPartInfoDTO> searchShow(String title);
	ShowPartInfoDTO searchShowNumber(int num);
	int showUpdate(ShowVO vo);
  
	int firstCntRow();
	
	int currentCount(HashMap<String, Object> map);
	
	List<ShowVO> list(HashMap<String, Object> map);
  
	int count();
	
	ShowVO showSelect(int num);

}
