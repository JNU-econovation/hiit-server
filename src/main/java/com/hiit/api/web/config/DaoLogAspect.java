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
public class DaoLogAspect {
	@Pointcut("execution(* com.hiit.api.domain.dao..*.*(..))")
	public void daoAdvice() {}

	@Before("daoAdvice()")
	public void requestLogging(JoinPoint joinPoint) {
		MDC.put("d_startTime", System.currentTimeMillis());
		log.info("execute {}", joinPoint.getSignature().getDeclaringTypeName());
	}

	@AfterReturning(pointcut = "daoAdvice()", returning = "returnValue")
	public void requestLogging(JoinPoint joinPoint, Object returnValue) {
		MDC.put("d_endTime", System.currentTimeMillis());
		MDC.put(
				"d_elapsedTime",
				Long.parseLong(MDC.get("d_endTime").toString())
						- Long.parseLong(MDC.get("d_startTime").toString()));
		log.info("end {}", joinPoint.getSignature().getDeclaringTypeName());
	}
}
