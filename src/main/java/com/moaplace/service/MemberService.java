package com.moaplace.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.moaplace.mapper.MemberMapper;
import com.moaplace.vo.MemberVO;

@Service
public class MemberService {
	
	@Autowired private MemberMapper mapper;
	
	// 멤버번호로 로그인한 회원의 정보를 불러온다
	public MemberVO findLoginUser(int memberNum) {
		return mapper.findLoginUser(memberNum);
	}
	
}
