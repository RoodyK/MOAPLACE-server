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

import com.moaplace.dto.MemberJoinRequestDTO;
import com.moaplace.vo.MemberVO;

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
		List<MemberVO> vo = memberService.selectAll();
		for( MemberVO m : vo) {
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
	
	// 아이디 찾기 테스트
	@Test
	public void findByIdTest() {
		Map<String, Object> map = new HashMap<>();
		map.put("name", "관리자");
		map.put("email", "admin@gmail.com");
		
		String id = memberService.findById(map);
		
		assertNotNull(id);
	}
}
