package com.moaplace.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.moaplace.dto.MemberJoinRequestDTO;
import com.moaplace.mapper.MemberMapper;
import com.moaplace.vo.MemberVO;

import lombok.extern.log4j.Log4j;

@Service
@Log4j
public class MemberService {

	@Autowired
	private MemberMapper mapper;
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	public List<MemberVO> selectAll() {
		return mapper.selectAll();
	}
	
	public boolean checkId(String reqId) {
		String id = mapper.checkId(reqId);
		
		if(id != null) return true;
		return false;
	}
	
	public int join(MemberJoinRequestDTO dto) {
		
		String password = passwordEncoder.encode(dto.getMember_pwd());
		log.info(password);
		
		dto.setMember_pwd(password);
		
		int n = mapper.join(dto);
		
		if(n > 0) return n;
		return -1;
	}

}