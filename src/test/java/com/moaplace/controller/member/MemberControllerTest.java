package com.moaplace.controller.member;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.moaplace.authentication.AuthorizationType;
import com.moaplace.dto.member.MemberJoinRequestDTO;
import com.moaplace.dto.member.MemberLoginRequestDTO;
import com.moaplace.service.JWTService;

import lombok.extern.log4j.Log4j;

@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({
	"file:src/main/webapp/WEB-INF/spring/appServlet/servlet-context.xml",
	"file:src/main/webapp/WEB-INF/spring/mail-context.xml",
	"file:src/main/webapp/WEB-INF/spring/root-context.xml"
})
@Log4j
public class MemberControllerTest {

	@Autowired
	private JWTService tokenService;
	@Autowired
	private WebApplicationContext ctx;
	
	private MockMvc mockMvc;
	
	@Before
	public void setup() {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(ctx).build();
	}
	
	// objectMapper 테스트
	@Test
	public void jsonConvertTest() throws JsonProcessingException {
		MemberLoginRequestDTO dto = new MemberLoginRequestDTO();
		dto.setMember_id("test");
		dto.setMember_pwd("!test123");
		
		ObjectMapper mapper = new ObjectMapper();
		String info = mapper.writeValueAsString(dto);
		log.info("[info] : " + info);
		
		MemberLoginRequestDTO convertDTO = 
				mapper.readValue(info, MemberLoginRequestDTO.class);
		log.info("[convertDTO] : " + convertDTO);
	}
	
	// 아이디 중복 확인
	@Test
	public void checkIdTest() throws Exception {
		String id = "test1234";
		
		mockMvc.perform(get("/users/join/checkId/"+id))
			.andExpect(status().isOk())
			.andDo(print());
	}
	
	// 이메일 체크 테스트
	@Test
	public void emailAuthTest() throws Exception {
		Map<String, Object> map = new HashMap<>();
		map.put("email", "pps8853@naver.com");
		
		ObjectMapper mapper = new ObjectMapper();
		String email = mapper.writeValueAsString(map);
		
		mockMvc.perform(post("/users/join/email/auth")
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.content(email))
				.andExpect(status().isOk())
				.andDo(print());
	}
	
	// 회원가입
	@Test
	public void joinMemberTest() throws Exception {
		MemberJoinRequestDTO dto = new MemberJoinRequestDTO();
		dto.setMember_num(0);
		dto.setMember_id("pppp1234");
		dto.setMember_pwd("!pppp1234");
		dto.setMember_email("pppp@pppp.com");
		dto.setMember_name("피삼이");
		dto.setMember_gender("female");
		dto.setMember_birth("900101");
		dto.setMember_phone("010-0101-0101");
		dto.setMember_address("사랑시 고백구 행복동 151");
		dto.setApi("none");
		
		ObjectMapper mapper = new ObjectMapper();
		String info = mapper.writeValueAsString(dto);
		
		mockMvc.perform(post("/users/join/result")
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.content(info))
				.andExpect(status().isOk())
				.andDo(print());
	}
	
	// 로그인
	@Test
	public void loginTest() throws Exception {
		MemberLoginRequestDTO dto = new MemberLoginRequestDTO();
		dto.setMember_id("test1234");
		dto.setMember_pwd("!test1234");
		
		ObjectMapper mapper = new ObjectMapper();
		String info = mapper.writeValueAsString(dto);
		
		mockMvc.perform(post("/users/login/result")
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.content(info))
				.andExpect(status().isOk())
				.andDo(print());
	}
	
	// 회원정보
	@Test
	public void getMemberInfoTest() throws Exception {
		
		String token = tokenService.createToken("test1234", "ROLE_MEMBER");
		String jwtToken = AuthorizationType.BEARER.thisName() + " " + token;
		
		mockMvc.perform(get("/users/login/member/info")
				.header("Authorization", jwtToken))
				.andExpect(status().isOk())
				.andDo(print());
	}
	
	// 회원권한
	@Test
	public void getMemberRolesTest() throws Exception {
		
		String token = tokenService.createToken("test1234", "ROLE_MEMBER");
		String jwtToken = AuthorizationType.BEARER.thisName() + " " + token;
		
		mockMvc.perform(get("/users/login/member/role")
				.header("Authorization", jwtToken))
				.andExpect(status().isOk())
				.andDo(print());
	}
	
	// 회원탈퇴
	@Test
	public void findMemberIdTest() throws Exception {
		String token = tokenService.createToken("test1234", "ROLE_MEMBER");
		String jwtToken = AuthorizationType.BEARER.thisName() + " " + token;
		
		mockMvc.perform(get("users/mypage/withdrawal")
				.header("Authorization", jwtToken))
				.andExpect(status().isOk())
				.andDo(print());
	}
}
