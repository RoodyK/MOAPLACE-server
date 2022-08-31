package com.moaplace.mapper;

import java.util.HashMap;
import java.util.List;

import com.moaplace.dto.ShowDTO;
import com.moaplace.dto.ShowDetailDTO;
import com.moaplace.dto.admin.show.MapperDetailDTO;
import com.moaplace.dto.admin.show.ShowListDTO;
import com.moaplace.vo.ShowVO;

public interface ShowMapper {
	
	int showInsert(ShowVO vo);
  
	List<ShowListDTO> showList(HashMap<String, Object> map);
  
	List<MapperDetailDTO> showDetail(int num);
  
	int cntRow();




	List<ShowDTO> list(HashMap<String, Object> map);
  
	int count(HashMap<String, Object> map);

	List<ShowDetailDTO> detail(int show_num);
	
}
