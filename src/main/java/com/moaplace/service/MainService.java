package com.moaplace.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.moaplace.dto.MainNoticeDTO;
import com.moaplace.dto.MainShowDTO;
import com.moaplace.mapper.AdminNoticeMapper;
import com.moaplace.mapper.ShowMapper;

@Service
public class MainService {
	
	@Autowired
	private ShowMapper showMapper;
	@Autowired
	private AdminNoticeMapper noticeMapper;
	
	public List<MainShowDTO> getRunningShow(){
		return showMapper.getRunningShow();
	}
	
	public List<MainNoticeDTO> getNotice(){
		return noticeMapper.getNotice();
	}
}
