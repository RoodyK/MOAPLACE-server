package com.moaplace.mapper;

import java.util.List;

import com.moaplace.dto.ShowImgDTO;
import com.moaplace.vo.ShowImgVO;

public interface ShowImgMapper {
	
	int showImgInsert(ShowImgVO vo);
  
	int showImgUpDel(int showNum);

	List<ShowImgDTO> detailimg(int show_num);
	
	//공연 삭제될때 같이 삭제
	int deleteShowImg(int num);

}
