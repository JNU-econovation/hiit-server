package com.hiit.api.web.config.filter;

import java.io.IOException;
import java.util.UUID;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.connector.RequestFacade;
import org.jboss.logging.MDC;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class MDCFilter implements Filter {

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		final String trxId = UUID.randomUUID().toString();
		MDC.put("traceId", trxId);
		MDC.put("requestAddress", request.getRemoteAddr());
		MDC.put("referer", ((RequestFacade) request).getHeader("referer"));
		MDC.put("userAgent", ((RequestFacade) request).getHeader("user-agent"));
		MDC.put("startTime", System.currentTimeMillis());
		log.info(
				"{} -> [{}] uri : {}",
				request.getRemoteAddr(),
				((RequestFacade) request).getMethod(),
				((RequestFacade) request).getRequestURI());
		chain.doFilter(request, response);
		MDC.put("endTime", System.currentTimeMillis());
		MDC.put(
				"elapsedTime",
				System.currentTimeMillis() - Long.parseLong(MDC.get("startTime").toString()));
		log.info("requestLogging: {}", MDC.getMap());
		MDC.clear();
	}
}
