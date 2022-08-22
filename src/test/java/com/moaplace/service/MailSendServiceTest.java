package com.moaplace.service;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.moaplace.vo.MemberVO;

import lombok.extern.log4j.Log4j;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({
	"file:src/main/webapp/WEB-INF/spring/root-context.xml",
	"file:src/main/webapp/WEB-INF/spring/mail-context.xml"
})
@Log4j
public class MailSendServiceTest {

	@Autowired
	private MailSendService service;
	
	@Autowired
	private MemberService memberService;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Test
	public void test() {
		
		String email = "pps8853@naver.com";
		
		String code = service.joinEmail(email);
		
		if(code != null) {
			log.info(code);
		}
	}
	
	@Test
	public void selectListTest() {
		List<MemberVO> vo = memberService.selectAll();
		for( MemberVO m : vo) {
			System.out.println(m);
		}
	}
	
	@Test
	public void passwordEncoderTest() {
		log.info(passwordEncoder.encode("1234"));
	}
}
