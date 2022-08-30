package com.moaplace.service;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.moaplace.dto.AdminQnaListDTO;
import com.moaplace.dto.QnaListDTO;
import com.moaplace.dto.QnaMemberDTO;
import com.moaplace.mapper.MemberMapper;
import com.moaplace.mapper.QnaMapper;
import com.moaplace.vo.QnaVO;

@Service
public class QnaService {

	@Autowired 
	private QnaMapper qnaMapper;	
	@Autowired 
	private MemberMapper memberMapper;

	public int insert(QnaVO vo){
		return qnaMapper.insert(vo);
	}	
	
	public List<QnaListDTO> list(HashMap<String, Object> map){
		return qnaMapper.list(map);
	}	
	
	public int listCnt(HashMap<String, Object> map) {
		return qnaMapper.listCnt(map);
	}
	
	public QnaVO detail(int qna_num) {
		return qnaMapper.detail(qna_num);
	}
	
	public int update(QnaVO vo) {
		return qnaMapper.update(vo);
	}
	
	public int delete(int qna_num) {
		return qnaMapper.delete(qna_num);
	}
	
	public List<AdminQnaListDTO> adminList(HashMap<String, Object> map){
		return qnaMapper.adminList(map);
	}	
	
	public int adminListCnt(HashMap<String, Object> map) {
		return qnaMapper.adminListCnt(map);
	}
	
	public int changeState(HashMap<String, Object> map) {
		return qnaMapper.changeState(map);
	}
	
	public QnaMemberDTO qnaMember(int member_num) {
		return memberMapper.qnaMember(member_num);
	}
	
}
