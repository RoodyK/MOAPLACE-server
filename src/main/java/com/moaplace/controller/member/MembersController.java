package com.moaplace.controller.member;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.moaplace.dto.MemberJoinRequestDTO;
import com.moaplace.dto.MemberLoginRequestDTO;
import com.moaplace.dto.MemberLoginResponseDTO;
import com.moaplace.exception.WrongIdPasswordException;
import com.moaplace.service.JWTService;
import com.moaplace.service.MailSendService;
import com.moaplace.service.MemberService;

import lombok.extern.log4j.Log4j;

@RestController
@CrossOrigin("*")
@RequestMapping("/users")
@Log4j
public class MembersController {

	@Autowired
	private MemberService memberService;
	@Autowired
	private MailSendService mailService;
	@Autowired
	private JWTService JWTservice;

	/*
	 * 회원가입 관련 컨트롤러
	 */
	
	// 아이디 중복 확인
	@GetMapping(value = "/join/checkId/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> checkId(@PathVariable("id") String id) {
		
		boolean code = memberService.checkId(id);

		return code == true
				? new ResponseEntity<>("duple", HttpStatus.OK)
				: new ResponseEntity<>("use", HttpStatus.OK);	
	}
	
	// 이메일 인증번호 보내기
	@PostMapping(value = "/join/email/auth", 
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> emailAuth(
			@RequestBody Map<String, Object> data, @RequestHeader HttpHeaders headers) {
		
		String email = (String) data.get("email");
		String authNumber = mailService.joinEmail(email);

		log.info("헤더 : " + headers.get("Authorization"));
		
		return new ResponseEntity<>(authNumber, HttpStatus.OK);
	}
	
	// 회원가입
	@PostMapping("/join/result")
	public ResponseEntity<String> joinMember(
			@RequestBody MemberJoinRequestDTO dto, @RequestHeader HttpHeaders headers) {
		
		log.info(dto);
		int n = memberService.join(dto);
		
		return n == 1 
				? new ResponseEntity<>("success", HttpStatus.OK)
				: new ResponseEntity<>("fail", HttpStatus.BAD_REQUEST);
	}
	
	/*
	 * 회원 로그인
	 */
	// ResponseEntity<MemberLoginResponseDTO>
	@PostMapping("/login/result")
	public ResponseEntity<String> login(
			@RequestBody MemberLoginRequestDTO dto) {
		log.info("controller dto : " + dto);
		HttpHeaders header = new HttpHeaders();
		
		try {
			MemberLoginResponseDTO member = memberService.login(dto);
			
			String token = JWTservice.createToken(member.getMember_id(), member.getAuthority());
			header.add("Authorization", token);
			
		}catch(WrongIdPasswordException e) {
			return new ResponseEntity<>("fail", HttpStatus.BAD_REQUEST);
		}
		
		return new ResponseEntity<>("success", header, HttpStatus.OK);
	}
}
