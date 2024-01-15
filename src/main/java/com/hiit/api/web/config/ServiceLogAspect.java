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
public class ServiceLogAspect {
	@Pointcut("execution(* com.hiit.api.domain.service..*.*(..))")
	public void serviceAdvice() {}

	@Before("serviceAdvice()")
	public void requestLogging(JoinPoint joinPoint) {
		MDC.put("s_startTime", System.currentTimeMillis());
		log.info("execute {}", joinPoint.getSignature().getDeclaringTypeName());
	}

	@AfterReturning(pointcut = "serviceAdvice()", returning = "returnValue")
	public void requestLogging(JoinPoint joinPoint, Object returnValue) {
		MDC.put("s_endTime", System.currentTimeMillis());
		MDC.put(
				"s_elapsedTime",
				Long.parseLong(MDC.get("s_endTime").toString())
						- Long.parseLong(MDC.get("s_startTime").toString()));
		log.info("end {}", joinPoint.getSignature().getDeclaringTypeName());
	}
}
