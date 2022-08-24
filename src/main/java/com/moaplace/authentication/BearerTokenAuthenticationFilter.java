package com.moaplace.authentication;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;

import com.moaplace.exception.AuthenticationException;
import com.moaplace.service.JWTService;

public class BearerTokenAuthenticationFilter implements HandlerInterceptor {

	@Autowired
	private JWTService service;
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		String token = request.getHeader("Authorization");
		
		if(!service.validateToken(token)) {
			throw new AuthenticationException(); 
		}
		
		return false;
	}
}
