package com.hiit.api.web.config;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.jboss.logging.MDC;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
public class UseCaseLogAspect {
	@Pointcut("execution(* com.hiit.api.domain.usecase..*.*(..))")
	public void useCaseAdvice() {}

	@Before("useCaseAdvice()")
	public void requestLogging(JoinPoint joinPoint) {
		MDC.put("u_startTime", System.currentTimeMillis());
		log.info("execute {}", joinPoint.getSignature().getDeclaringTypeName());
	}

	@AfterReturning(pointcut = "useCaseAdvice()", returning = "returnValue")
	public void requestLogging(JoinPoint joinPoint, Object returnValue) {
		MDC.put("u_endTime", System.currentTimeMillis());
		MDC.put(
				"u_elapsedTime",
				Long.parseLong(MDC.get("u_endTime").toString())
						- Long.parseLong(MDC.get("u_startTime").toString()));
		log.info("end {}", joinPoint.getSignature().getDeclaringTypeName());
	}
}
