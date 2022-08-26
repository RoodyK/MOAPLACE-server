package com.moaplace.service;


import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.moaplace.dto.MemberInfoResponseDTO;
import com.moaplace.dto.MemberJoinRequestDTO;
import com.moaplace.dto.MemberLoginRequestDTO;
import com.moaplace.dto.MemberLoginResponseDTO;
import com.moaplace.exception.WrongIdPasswordException;
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
		String id = mapper.checkId(reqId); // 아이디 중복 검사
		
		if(id != null) return true;
		return false;
	}
	
	public int join(MemberJoinRequestDTO dto) {
		// 비밀번호 암호화
		String password = passwordEncoder.encode(dto.getMember_pwd());
		log.info(password);
		
		dto.setMember_pwd(password);
		
		int n = mapper.join(dto);
		
		if(n > 0) return n;
		return -1;
	}
	
	public MemberLoginResponseDTO login(MemberLoginRequestDTO dto) {
		// 비밀번호 꺼내오기
		String memberPwd = mapper.findByPassword(dto.getMember_id());
		
		if(memberPwd == null) { // 아이디와 일치하는 비밀번호가 없으면
			throw new WrongIdPasswordException();
		}
		
		boolean isPassword = 
				passwordEncoder.matches(dto.getMember_pwd(), memberPwd);
		if(!isPassword) { // 입력한 비밀번호와 암호화된 비밀번호가 일치하지 않으면
			throw new WrongIdPasswordException();
		}
		
		dto.setMember_pwd(memberPwd);
		
		MemberLoginResponseDTO member = mapper.login(dto);
		
		if(member == null) {
			throw new WrongIdPasswordException();
		}
		return member;
	}
	
	// 회원정보 얻기
	public MemberInfoResponseDTO getMemberInfo(String id) {
		
		return mapper.memberInfo(id);
	}
	
	// 아이디 찾기/비밀번호 재설정
	public String findById(Map<String, Object> map) {
		String id = mapper.findById(map);
		
		return id;
	}
	
	public int newPassword(MemberLoginRequestDTO dto) {
		
		String pwd = passwordEncoder.encode(dto.getMember_pwd());
		dto.setMember_pwd(pwd);
		
		int n = mapper.newPassword(dto);
		
		return n;
	}
	
}
