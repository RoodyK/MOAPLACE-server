package com.moaplace.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.moaplace.vo.MemberVO;

@Mapper
public interface MemberMapper {
	
	// 멤버번호로 로그인한 회원의 정보를 불러온다
	MemberVO findLoginUser(int memberNum);
	
}
