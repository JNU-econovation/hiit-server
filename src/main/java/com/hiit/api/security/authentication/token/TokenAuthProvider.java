package com.hiit.api.security.authentication.token;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.stereotype.Component;

/** 토큰 인증을 위한 AuthenticationProvider 클래스 */
@Component
@RequiredArgsConstructor
public class TokenAuthProvider implements AuthenticationProvider {

	private final TokenUserDetailsService tokenUserDetailsService;

	/**
	 * 토큰 인증을 수행합니다.
	 *
	 * @param authentication 토큰 인증을 위한 Authentication 객체
	 * @return 인증된 Authentication 객체
	 * @throws AuthenticationException
	 * @throws AccessDeniedException
	 */
	@Override
	public Authentication authenticate(Authentication authentication)
			throws AuthenticationException, AccessDeniedException {

		String token =
				Optional.ofNullable(authentication.getPrincipal())
						.map(String.class::cast)
						.orElseThrow(() -> new IllegalArgumentException("token is missing"));

		UserDetails userDetails = tokenUserDetailsService.loadUserByUsername(token);

		SecurityContextHolder.getContext().setAuthentication(authentication);

		if (authentication instanceof PreAuthenticatedAuthenticationToken) {
			return new PreAuthenticatedAuthenticationToken(
					userDetails, userDetails.getPassword(), userDetails.getAuthorities());
		}
		return null;
	}

	/**
	 * TokenAuthProvider가 지원하는 Authentication Token 객체인지 확인합니다.
	 *
	 * @param authentication 지원 여부를 확인할 Authentication Token 객체
	 * @return 지원 여부
	 */
	@Override
	public boolean supports(Class<?> authentication) {
		return PreAuthenticatedAuthenticationToken.class.isAssignableFrom(authentication);
	}
}
