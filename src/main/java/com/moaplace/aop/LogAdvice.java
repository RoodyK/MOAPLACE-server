package com.moaplace.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;

import lombok.extern.log4j.Log4j;

@Log4j
public class LogAdvice {
	
	public Object invokeService(ProceedingJoinPoint joinPoint) throws Throwable {
		
		Object result = joinPoint.proceed();
		
		Signature sig = joinPoint.getSignature();
		Object[] args = joinPoint.getArgs();
		
		log.info("대상 객체: " + joinPoint.getTarget().getClass().getSimpleName());
		log.info("메소드 이름: " + sig.getName());
		log.info("리턴 값: " + result);
		
		for(Object o : args) {
			log.info("파라미터 : " + o);
		}
		
		return result;
	}
}
