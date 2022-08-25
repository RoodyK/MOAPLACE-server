package com.moaplace.mapper;

import java.util.HashMap;
import java.util.List;

import com.moaplace.dto.AdminQnaListDTO;
import com.moaplace.dto.QnaListDTO;
import com.moaplace.vo.QnaVO;

public interface QnaMapper {

	int insert(QnaVO vo);
	List<QnaListDTO> list(HashMap<String, Object> map);
	int listCnt(HashMap<String, Object> map);
	QnaVO detail(int qna_num);
	int update(QnaVO vo);
	int delete(int qna_num);
	List<AdminQnaListDTO> adminList(HashMap<String, Object> map);
	int adminListCnt(HashMap<String, Object> map);
	int changeState(HashMap<String, Object> map);
	
}
