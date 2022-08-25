package com.moaplace.service;

import java.util.Base64;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import lombok.extern.log4j.Log4j;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({
	"file:src/main/webapp/WEB-INF/spring/root-context.xml",
	"file:src/main/webapp/WEB-INF/spring/mail-context.xml"
})
@Log4j
public class JWTServiceTest {

	@Autowired
	private JWTService service;
	
	@Value("${oracle.username}")
	private String key;
	
	// Base64인코딩 테스트
	@Test
	public void base64Test() {
		String key = "moaplace";
		String encodingKey = Base64.getEncoder().encodeToString(key.getBytes());
		
		log.info("인코딩 키 : " + encodingKey);
		
		log.info(key);
	}
	
	public enum Auth {
		ROLE_MEMBER,
		ROLE_ADMIN
	}
	
	@Test
	public void enumTest() {
		log.info(Auth.ROLE_ADMIN);
		log.info(Auth.ROLE_MEMBER.name().equals("ROLE_MEMBER"));
	}
	
	// 토큰 생성 테스트
	@Test
	public void createTokenTest() {
		String jwtToken = service.createToken("abcd", "ROLE_MEMBER");
		
		log.info("토큰 생성 : " + jwtToken);
		Map<String, Object> map = service.getClaims(jwtToken);
		log.info("============================================");
		log.info("클레임 확인하기");
		for(String s : map.keySet()) {
			log.info("key : " + s);
			log.info("value : " + map.get(s));
		}
		
		log.info("============================================");
		log.info("subject 확인 (아이디)");
		log.info("subject : " + service.getUserId(jwtToken));
		
		log.info("============================================");
		log.info("토큰 유효성 확인 true 사용가능 , false 만료 :  " + service.validateToken(jwtToken));
	}
	
	@Test
	public void tokenRolesTest() {
		String jwtToken = service.createToken("abc", "ROLE_MEMBER");
		log.info("토큰 생성 : " + jwtToken);
		log.info("============================================");
		
		String roles = (String) service.getClaims(jwtToken).get("roles");
		log.info("roles : " + roles);
	}
}
