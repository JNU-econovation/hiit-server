package com.hiit.api.security.filter.token;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.servlet.http.HttpServletRequest;
import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter;

/**
 * 토큰 인증을 위한 필터<br>
 * 토큰은 인증된 정보이기에 AbstractPreAuthenticatedProcessingFilter를 상속받아 구현합니다.
 */
public class TokenAuthenticationFilter extends AbstractPreAuthenticatedProcessingFilter {

	private static final Pattern PATTERN_AUTHORIZATION_HEADER = Pattern.compile("^[Bb]earer (.*)$");

	/**
	 * Principal에 저장할 정보를 토큰으로부터 추출하여 반환합니다.
	 *
	 * @param request http 요청
	 * @return 토큰
	 */
	@Override
	protected Object getPreAuthenticatedPrincipal(HttpServletRequest request) {
		return resolveAccessToken(request);
	}

	/**
	 * Credential에 저장할 정보를 토큰으로부터 추출하여 반환합니다.
	 *
	 * @param request http 요청
	 * @return 토큰
	 */
	@Override
	protected Object getPreAuthenticatedCredentials(HttpServletRequest request) {
		return resolveAccessToken(request);
	}

	private String resolveAccessToken(HttpServletRequest request) {
		String authorization = request.getHeader("Authorization");
		if (authorization == null) {
			return null;
		}
		Matcher matcher = PATTERN_AUTHORIZATION_HEADER.matcher(authorization);
		if (!matcher.matches()) {
			return null;
		}
		return matcher.group(1);
	}
}
