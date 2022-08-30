package com.moaplace.mapper;

import java.util.HashMap;
import java.util.List;

import com.moaplace.dto.AdminDetailDTO;
import com.moaplace.dto.AdminListDTO;
import com.moaplace.dto.AdminNoticeDTO;
import com.moaplace.dto.AdminNoticeDetailDTO;

public interface MoaplaceMapper {
	public List<AdminNoticeDTO> listAll(HashMap<String,Object> map);
	public int getCount(HashMap<String,Object> map);
	public List<AdminDetailDTO> detail(int notice_num);
	public List<AdminNoticeDetailDTO> filelist(int notice_num);
	public AdminNoticeDetailDTO filedown(int notice_detail_num);
	public List<AdminListDTO> detaillist(int notice_num);	
	public int addHit(int notice_num);
	public AdminNoticeDTO getNext(int notice_num);
	public AdminNoticeDTO getPrev(int notice_num);
	
}
