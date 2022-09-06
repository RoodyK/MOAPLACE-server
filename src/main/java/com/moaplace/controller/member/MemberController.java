package com.moaplace.controller.member;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.moaplace.authentication.AuthorizationType;
import com.moaplace.dto.member.ApiLoginDTO;
import com.moaplace.dto.member.MemberInfoResponseDTO;
import com.moaplace.dto.member.MemberJoinRequestDTO;
import com.moaplace.dto.member.MemberLoginRequestDTO;
import com.moaplace.dto.member.MemberLoginResponseDTO;
import com.moaplace.exception.WrongIdPasswordException;
import com.moaplace.service.JWTService;
import com.moaplace.service.MailSendService;
import com.moaplace.service.MemberService;

@RestController
@CrossOrigin("*")
@RequestMapping("/users")
	public class MemberController {

	@Autowired
	private MemberService memberService;
	@Autowired
	private MailSendService mailService;
	@Autowired
	private JWTService tokenService;

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
			@RequestBody Map<String, Object> data) {
		
		String email = (String) data.get("email");
		String authNumber = mailService.joinEmail(email);
		
		return new ResponseEntity<>(authNumber, HttpStatus.OK);
	}

	// 회원가입
	@PostMapping(value = "/join/result",
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> joinMember(
			@RequestBody MemberJoinRequestDTO dto) {

		int n = memberService.join(dto);
		
		return n == 1 
				? new ResponseEntity<>("success", HttpStatus.OK)
				: new ResponseEntity<>("fail", HttpStatus.BAD_REQUEST);
	}
	
	// 로그인
	@PostMapping(value = "/login/result", 
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Map<String, Object>> login(
			@RequestBody MemberLoginRequestDTO dto) {
		Map<String, Object> responseToken = new HashMap<>();
		
		try {
			MemberLoginResponseDTO member = memberService.login(dto);
			String token = tokenService.createToken(member.getMember_id(), member.getAuthority());
			responseToken.put("token", AuthorizationType.BEARER.thisName() + " " + token);
			
			return new ResponseEntity<>(responseToken, HttpStatus.OK);
		}catch(WrongIdPasswordException e) {
			responseToken.put("fail", "fail");
			return new ResponseEntity<>(responseToken, HttpStatus.BAD_REQUEST);
		}
	}
	
	// 회원 정보
	@GetMapping(value = "/login/member/info", 
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<MemberInfoResponseDTO> getMemberInfo(
			HttpServletRequest request) {
		
		String token = request.getHeader("Authorization");
		String jwtToken = token.split(" ")[1];
		String id = tokenService.getUserId(jwtToken);
		
		MemberInfoResponseDTO info = memberService.getMemberInfo(id);
		
		return ResponseEntity.ok().body(info);
	}
	
	// 회원 권한
	@GetMapping(value = "/login/member/role",
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Map<String, Object>> getMemberRoles(
			HttpServletRequest request) {
		
		Map<String, Object> map = new HashMap<>();
		String token = request.getHeader("Authorization");
		String jwtToken = token.split(" ")[1];
		String roles = (String) tokenService.getClaims(jwtToken).get("roles");
		
		map.put("roles", roles);
		
		return ResponseEntity.ok().body(map); 
	}
	
	// 아이디 찾기
	@PostMapping(value = "/login/find/id",
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> findMemberId(
			@RequestBody Map<String, Object> reqInfo) {
		
		String id = memberService.findById(reqInfo);
		if(id != null) {
			String email = (String) reqInfo.get("email");
			mailService.findById(email, id);
		}
		
		return id != null
				? ResponseEntity.ok().body("success")
				: ResponseEntity.badRequest().body("fail");
	}
	
	// 비밀번호 재설정 이메일 발송
	@PostMapping(value = "/login/find/pwd",
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> resetPassword(
			@RequestBody Map<String, Object> reqInfo) {
		
		String id = memberService.findById(reqInfo);
		if(id != null) {
			String email = (String) reqInfo.get("email");
			mailService.resetPassword(email, id);
		}
		
		return id != null
				? ResponseEntity.ok().body("success")
				: ResponseEntity.badRequest().body("fail");
	}
	
	// 비밀번호 재설정
	@PostMapping(value = "/login/reset/password",
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> newPassword(
			@RequestBody MemberLoginRequestDTO dto) {
		
		int n = memberService.newPassword(dto);
		
		return n > 0
				? ResponseEntity.ok().body("success")
				: ResponseEntity.badRequest().body("fail");
	}
	
	// 회원탈퇴
	@GetMapping(value = "/mypage/withdrawal",
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> withdrawal(HttpServletRequest request) {
		String token = request.getHeader("Authorization");
		String jwtToken = token.split(" ")[1];
		String id = tokenService.getUserId(jwtToken);
		
		int n = memberService.withdrawal(id);
		
		return n > 0
				? ResponseEntity.ok().body("success")
				: ResponseEntity.badRequest().body("fail");
	}
	
	// api가입 되어있는지 체크
	@PostMapping(value = "/login/api/kakao",
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Map<String, Object>> apiCheck(@RequestBody Map<String, Object> map) {
		
		String email = (String) map.get("email");
		ApiLoginDTO dto = memberService.apiCheck(email);
		Map<String, Object> response = new HashMap<>();
		
		if(dto != null) {
			response.put("result", dto);
		}else {
			response.put("result", "using");
		}
		
		return ResponseEntity.ok().body(response);
	}
	
	// api 로그인
	@PostMapping(value = "/login/api/result",
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Map<String, Object>> login(
			@RequestBody ApiLoginDTO dto) {
		Map<String, Object> responseToken = new HashMap<>();
		
		try {
			MemberLoginResponseDTO member = memberService.apiLogin(dto);
			String token = tokenService.createToken(member.getMember_id(), member.getAuthority());
			responseToken.put("token", AuthorizationType.BEARER.thisName() + " " + token);
			
			return new ResponseEntity<>(responseToken, HttpStatus.OK);
		}catch(WrongIdPasswordException e) {
			responseToken.put("fail", "fail");
			return new ResponseEntity<>(responseToken, HttpStatus.BAD_REQUEST);
		}
	}
}
