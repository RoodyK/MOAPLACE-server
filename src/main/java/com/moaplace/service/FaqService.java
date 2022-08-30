package com.moaplace.service;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.moaplace.dto.AdminFaqListDTO;
import com.moaplace.mapper.FaqMapper;
import com.moaplace.vo.FaqVO;

@Service
public class FaqService {
	
	@Autowired
	private FaqMapper faqMapper;
	
	public int insert(FaqVO vo) {
		return faqMapper.insert(vo);
	}
	
	public List<AdminFaqListDTO> list(HashMap<String, Object> map){
		return faqMapper.list(map);
	}
	
	public int listCnt(HashMap<String, Object> map) {
		return faqMapper.listCnt(map);
	}
	
	public FaqVO detail(int faq_num) {
		return faqMapper.detail(faq_num);
	}
	
	public int update(FaqVO vo) {
		return faqMapper.update(vo);
	}
	
	public int delete(int faq_num) {
		return faqMapper.delete(faq_num);
	}

}
