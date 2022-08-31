package com.moaplace.service;


import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.moaplace.dto.MyBookingCancleRequestDTO;
import com.moaplace.dto.MyInfoEditDTO;
import com.moaplace.dto.member.ApiLoginDTO;
import com.moaplace.dto.member.MemberInfoResponseDTO;
import com.moaplace.dto.member.MemberJoinRequestDTO;
import com.moaplace.dto.member.MemberLoginRequestDTO;
import com.moaplace.dto.member.MemberLoginResponseDTO;
import com.moaplace.exception.MemberNotJoinException;
import com.moaplace.exception.WrongIdPasswordException;
import com.moaplace.mapper.ApiAuthMapper;
import com.moaplace.mapper.MemberMapper;
import com.moaplace.vo.ApiAuthVO;
import com.moaplace.vo.MemberVO;

import lombok.extern.log4j.Log4j;

@Service
@Log4j
public class MemberService {

	@Autowired
	private MemberMapper mapper;
	@Autowired
	private ApiAuthMapper apiMapper;
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
	
	@Transactional(rollbackFor = Exception.class)
	public int join(MemberJoinRequestDTO dto) {
		// 비밀번호 암호화
		String password = passwordEncoder.encode(dto.getMember_pwd());
		log.info(password);
		dto.setMember_pwd(password);
		
		int n = mapper.join(dto);
		
		if(n > 0 && dto.getApi().equals("using")) {
			ApiAuthVO vo = new ApiAuthVO(
					0, dto.getMember_num(), dto.getMember_email(), "kakao");
			n = apiMapper.apiJoin(vo);
		}else {
			throw new MemberNotJoinException();
		}
		
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
	
	/* (예매취소용)입력값과 비밀번호 일치 체크하기 */
	public boolean pwdCheck(MyBookingCancleRequestDTO dto) {
		
		String memberPwd = mapper.findByPassword(dto.getMember_id());
		boolean isPassword = passwordEncoder.matches(dto.getMember_pwd(), memberPwd);
		
		return isPassword;
	}
	
	/* 회원 정보 수정 */
	public int myInfoEdit(MyInfoEditDTO dto) {
		// 비밀번호 암호화
		String password = passwordEncoder.encode(dto.getMember_pwd());
		log.info(password);
		
		dto.setMember_pwd(password);
		
		int n = mapper.myInfoEdit(dto);
		
		if(n > 0) return n;
		return -1;

	// 아이디 찾기/비밀번호 재설정
	public String findById(Map<String, Object> map) {
		String id = mapper.findById(map);
		
		return id;
	}
	
	// 새비밀번호로 변경
	public int newPassword(MemberLoginRequestDTO dto) {
		
		String pwd = passwordEncoder.encode(dto.getMember_pwd());
		dto.setMember_pwd(pwd);
		
		int n = mapper.newPassword(dto);
		
		return n;
	}
	
	// 회원탈퇴
	public int withdrawal(String id) {
		
		return mapper.withdrawal(id);
	}
	
	// 카카오api 가입된 이메일이 있는지 확인
	public ApiLoginDTO apiCheck(String email) {
		return mapper.apiCheck(email);
	}
	
	// 카카오 로그인
	public MemberLoginResponseDTO apiLogin(ApiLoginDTO dto) {
		return mapper.apiLogin(dto);

	}
}
