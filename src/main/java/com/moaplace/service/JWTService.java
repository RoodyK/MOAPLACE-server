package com.moaplace.service;

import java.util.Base64;
import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class JWTService {

	// 시크릿 키
	private String secretKey = "moa-place-secret";
	// 시크릿키 Base64인코딩
	private String SEC_KEY = Base64.getEncoder().encodeToString(secretKey.getBytes()); 
	// 토큰 유효시간 ms 단위
	private long toknValidTime = 30 * 60 * 1000L;
	
	// 토큰 생성
	public String createToken(String id, String roles) {
		
		// 클레임 생성(사용자 정보나 추가 데이터를 담는 조각) 
		Claims claims = Jwts.claims().setSubject(id); // 토큰 제목 id 사용
		claims.put("roles", roles); // 권한
		
		Date now = new Date(); // 현재 토큰 생성시간
		Date validToken = new Date(now.getTime() + toknValidTime); // 만료시간
		
		// Jwts: jwt토큰 생성 객체 
		String jwt = Jwts.builder()
				.setHeaderParam("typ", "JWT")
				.addClaims(claims)
				.setIssuedAt(now)
				.setExpiration(validToken)
				.signWith(SignatureAlgorithm.HS256, SEC_KEY)
				.compact();
		
		return jwt;
	}
	
	// 토큰에서 subject 추출 (회원 아이디)
	public String getUserId(String token) {
		return Jwts.parser()
				.setSigningKey(SEC_KEY) // 암호화에 사용된 시크릿 키
				.parseClaimsJws(token) // 알고리즘으로 암호화 된 토큰 파싱 
				.getBody() // 토큰의 클레임
				.getSubject(); // 클레임의 subject를 가져옴
	}
	
	// Headers에서 토큰값 가져오기
	public String getReuqestToken(HttpServletRequest request) {
		return request.getHeader("Authorization");
	}
	
	// jwt 클레임 얻어오기
	public Map<String, Object> getClaims(String token) {
		Jws<Claims> claims = Jwts.parser()
				.setSigningKey(SEC_KEY)
				.parseClaimsJws(token);
		
		return claims.getBody();
	}
	
	// 토큰의 유효성 확인
	public boolean validateToken(String token) {
		try {
			Jws<Claims> claims = Jwts.parser()
					.setSigningKey(SEC_KEY)
					.parseClaimsJws(token);
			// 클레임에 있는 만료시간이 현재 시간보다 이전인가
			// (!이기 때문에 만료시간이 남아있으면 true리턴)
			return !claims.getBody().getExpiration().before(new Date());
		}catch(Exception e) {
			// 만료시간 끝
			return false;
		}
	}
}
