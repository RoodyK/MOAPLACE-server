package com.moaplace.service;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.moaplace.dto.member.AdminMemberInfoResponseDTO;
import com.moaplace.dto.member.MemberInfoResponseDTO;
import com.moaplace.dto.member.MemberJoinRequestDTO;
import com.moaplace.dto.member.MemberLoginRequestDTO;
import com.moaplace.dto.member.MemberLoginResponseDTO;

import lombok.extern.log4j.Log4j;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({
	"file:src/main/webapp/WEB-INF/spring/root-context.xml",
	"file:src/main/webapp/WEB-INF/spring/mail-context.xml"
})
@Log4j
public class MemberServiceTest {

	@Autowired
	private MemberService memberService;
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	// 패스워드 인코더 테스트
	@Test
	public void passwordEncoderTest() {
		log.info(passwordEncoder.encode("123123"));
		log.info(passwordEncoder.matches("!temp1234", "$2a$10$X5p.Ib1B0X22WGhh78DxA.x/NT.FaW6vKdr4VhYFF.neXOWCgB4o."));
	}
	
	// 회원정보 테스트
	@Test
	public void selectListTest() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("startRow", 1);
		map.put("endRow", 5);
		map.put("sorted", "all");
		List<AdminMemberInfoResponseDTO> vo = memberService.selectAll(map);
		for( AdminMemberInfoResponseDTO m : vo) {
			System.out.println(m);
		}

	}
	
	// 회원가입 테스트
	@Test
	public void insertTest() {
		MemberJoinRequestDTO dto = new MemberJoinRequestDTO();
		dto.setMember_id("abcde");
		String pwd = passwordEncoder.encode("123123");
		dto.setMember_pwd(pwd);
		dto.setMember_email("abcde@gmail.com");
		dto.setMember_name("김관리");
		dto.setMember_gender("male");
		dto.setMember_birth("880215");
		dto.setMember_phone("010-1234-5875");
		dto.setMember_address("13480 경기 성남시 분당구 대왕판교로 477");
		
		int n = memberService.join(dto);
		assertEquals(1, n);
	}
	
	// 로그인 테스트
	@Test
	public void loginTest() {
		MemberLoginRequestDTO dto = new MemberLoginRequestDTO();
		dto.setMember_id("test");
		dto.setMember_pwd("!test123");
		
		MemberLoginResponseDTO member = memberService.login(dto);
		
		assertNotNull(member);
	}
	
	// 회원정보 얻기
	@Test
	public void getMemberInfoTest() {
		String id = "test";
		
		MemberInfoResponseDTO info = memberService.getMemberInfo(id);
		
		assertNotNull(info);
	}
	
	// 아이디 찾기 테스트
	@Test
	public void findByIdTest() {
		Map<String, Object> map = new HashMap<>();
		map.put("name", "관리자");
		map.put("email", "admin@gmail.com");
		
		String id = memberService.findById(map);
		
		assertNotNull(id);
	}
	
	// 새 비밀번호 변경
	@Test
	public void newPasswordTest() {
		MemberLoginRequestDTO dto = new MemberLoginRequestDTO();
		dto.setMember_id("abcd1234");
		dto.setMember_pwd("!abcd1234");
		
		int actual = memberService.newPassword(dto);
		
		assertEquals(1, actual);
	}
	
	// 회원탈퇴
	@Test
	public void withdrawal() {
		String id = "qweqwe123";
		
		int actual = memberService.withdrawal(id);
		
		assertEquals(1, actual);
	}
}
