package com.moaplace.service;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.moaplace.dto.AdminDetailDTO;
import com.moaplace.dto.AdminListDTO;
import com.moaplace.dto.AdminNoticeDTO;
import com.moaplace.dto.AdminNoticeDetailDTO;
import com.moaplace.mapper.MoaplaceMapper;

@Service
public class MoaplaceService {
	@Autowired
	private MoaplaceMapper mapper;

	public List<AdminListDTO> listAll(HashMap<String, Object> map) {
		return mapper.listAll(map);
	}

	public int getCount(HashMap<String, Object> map) {
		return mapper.getCount(map);
	}

	// 글 상세보기
	public List<AdminDetailDTO> detail(int notice_num) {
		return mapper.detail(notice_num);
	}

	// 글 상세보기 : 파일 없을때
	public List<AdminListDTO> detaillist(int notice_num) {
		return mapper.detaillist(notice_num);
	}

	// 파일 리스트 불러오기
	public List<AdminNoticeDetailDTO> filelist(int notice_num) {
		return mapper.filelist(notice_num);
	}

	// 파일 다운로드
	public AdminNoticeDetailDTO filedown(int notice_detail_num) {
		return mapper.filedown(notice_detail_num);
	}
	
	//조회수 증가
	public int addHit(int notice_num) {
		return mapper.addHit(notice_num);
	}
	
	//다음 글
	public AdminNoticeDTO getNext(int notice_num) {
		return mapper.getNext(notice_num);
	}
	
	//이전글
	public AdminNoticeDTO getPrev(int notice_num) {
		return mapper.getPrev(notice_num);
	}
	
	//상세보기
	public AdminListDTO selectdetail(int notice_num) {
		return mapper.selectdetail(notice_num);
	}
	
	
}
