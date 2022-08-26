package com.moaplace.mapper;


import java.util.List;
import java.util.Map;

import com.moaplace.dto.MemberInfoResponseDTO;
import com.moaplace.dto.MemberJoinRequestDTO;
import com.moaplace.dto.MemberLoginRequestDTO;
import com.moaplace.dto.MemberLoginResponseDTO;
import com.moaplace.dto.QnaMemberDTO;
import com.moaplace.vo.MemberVO;

public interface MemberMapper {
	
	QnaMemberDTO qnaMember(int member_num);

	public String findById(Map<String, Object> map);
	
	// 로그인용 비밀번호 가져오기
	public String findByPassword(String id);
	
	public MemberInfoResponseDTO memberInfo(String member_id);
	
	public MemberLoginResponseDTO login(MemberLoginRequestDTO dto);
	
	public List<MemberVO> selectAll();
	
	public String checkId(String member_id);
	
	public MemberVO findOne();
	
	public int join(MemberJoinRequestDTO dto);
	
	public int newPassword(MemberLoginRequestDTO dto);

}
