package com.moaplace.mapper;

import java.util.List;

import com.moaplace.dto.MemberJoinRequestDTO;
import com.moaplace.vo.MemberVO;

public interface MemberMapper {

	public String findById();
	
	public String findByPassword();
	
	public String login();
	
	public List<MemberVO> selectAll();
	
	public String checkId(String member_id);
	
	public MemberVO findOne();
	
	public int join(MemberJoinRequestDTO dto);
}
