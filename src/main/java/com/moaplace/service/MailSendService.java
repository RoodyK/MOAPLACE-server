package com.moaplace.service;

import java.util.Random;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import lombok.extern.log4j.Log4j;

@Log4j
@Service
public class MailSendService {

	@Autowired
	private JavaMailSenderImpl mailSender;
	
	// 인증번호 난수 생성
	private int makeRandom() {
		
		Random rd = new Random();
		int checkNum = rd.nextInt(888888) + 111111; // 6 자리 난수 생성
		log.info("인증번호 : " + checkNum);
		
		return checkNum;
	}
	
	// 회원가입 이메일 인증
	public String joinEmail(String email) {
		
		int n = makeRandom();
		// 보낼 이메일
		String setFrom = "pps8853@naver.com";
		// 받는 이메일
		String toMail = email;
		// 메일 제목
		String title ="회원가입 인증 이메일 입니다.";
		// 이메일 내용 삽입
		String content = 
				"<h1>MOA PLACE를 방문해주셔서 감사합니다.</h1>" + 
				"<br/><br/>" + 
				"인증번호는 " + n + " 입니다." + 
				"<br/>" + 
				"해당 인증번호를 인증번호 확인란에 기입하여 주세요.";
		mailSend(setFrom, toMail, title, content);
		
		return Integer.toString(n);
	}

	// 메일 보내기
	private void mailSend(
			String setFrom, String toMail, String title, String content) {

		// 스프링에서 제공하는 mailAPI
		MimeMessage message = mailSender.createMimeMessage();
		
		try {
			// 단순한 텍스트 형태 전송
			// MimeMessageHelper helper = new MimeMessageHelper(message, "utf-8");
			// true 매개값을 전달하면 multipart 형식의 메시지 전달이 가능, 문자 인코딩 설정도 가능하다.
			MimeMessageHelper helper = new MimeMessageHelper(message, true, "utf-8");
			helper.setSubject("MOA PLACE 회원가입 인증번호 입니다.");
			helper.setFrom(setFrom);
			helper.setTo(toMail);
			helper.setSubject(title);
			// true전달 > html 형식으로 전송, 작성하지 않으면 단순 텍스트로 전달
			helper.setText(content, true);
			mailSender.send(message);
			
		} catch (MessagingException e) {
			e.printStackTrace();
		}
	}
}
