package com.moaplace.authentication;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;

import com.moaplace.exception.AuthenticationException;
import com.moaplace.service.JWTService;

import lombok.extern.log4j.Log4j;

@Log4j
public class BearerTokenAuthenticationFilter implements HandlerInterceptor {
	
	@Autowired
	private JWTService service;
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		// 헤더에서 토큰 추출
		
		String token = request.getHeader("Authorization");				
		
		log.info("======================================");
		log.info("토큰 : " + token);
		log.info("======================================");

//		// 토큰 만료되었는지 확인 true 아직 만료시간 남음 false 만료
		if(token != null) {
			String jwtToken = token.split(" ")[1];
			if(!service.validateToken(jwtToken)) {
				throw new AuthenticationException(); 
			}
		}
		
//		response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
		return true;
	}
}
