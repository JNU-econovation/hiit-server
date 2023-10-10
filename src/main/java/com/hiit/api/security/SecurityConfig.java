package com.hiit.api.security;

import com.hiit.api.security.authentication.token.TokenAuthProvider;
import com.hiit.api.security.filter.token.TokenAuthenticationFilter;
import com.hiit.api.security.handler.DelegatedAccessDeniedHandler;
import com.hiit.api.security.handler.DelegatedAuthenticationEntryPoint;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter;

/** Spring Security 설정 */
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

	private final DelegatedAuthenticationEntryPoint authenticationEntryPoint;
	private final DelegatedAccessDeniedHandler accessDeniedHandler;
	private final TokenAuthProvider tokenAuthProvider;

	/**
	 * 개발 환경에서 사용할 SecurityFilterChain 빈을 생성합니다.
	 *
	 * @param http HttpSecurity
	 * @return SecurityFilterChain
	 * @throws Exception
	 */
	@Bean
	@Profile("!prod")
	public SecurityFilterChain localSecurityFilterChain(HttpSecurity http) throws Exception {
		http.csrf().disable();
		http.formLogin().disable();
		http.httpBasic().disable();
		http.authorizeRequests()
				.antMatchers(
						HttpMethod.GET,
						"/swagger-ui/*",
						"/api-docs/*",
						"/openapi3.yaml",
						"/actuator/health",
						"/reports/**",
						"/error",
						// todo for mock server
						// todo delete
						"/api/v1/token")
				.permitAll()
				// todo delete
				.antMatchers(HttpMethod.POST, "/api/v1/foo")
				.permitAll()
				.antMatchers("/api/v1/**")
				.authenticated()
				.anyRequest()
				.denyAll();

		http.addFilterAt(
				generateAuthenticationFilter(), AbstractPreAuthenticatedProcessingFilter.class);

		http.exceptionHandling()
				.authenticationEntryPoint(authenticationEntryPoint)
				.accessDeniedHandler(accessDeniedHandler);

		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		return http.build();
	}

	/**
	 * 프로덕션 환경에서 사용할 SecurityFilterChain 빈을 생성합니다.
	 *
	 * @param http HttpSecurity
	 * @return SecurityFilterChain
	 * @throws Exception
	 */
	@Bean
	@Profile(value = "prod")
	public SecurityFilterChain prodSecurityFilterChain(HttpSecurity http) throws Exception {
		http.csrf().disable();
		http.formLogin().disable();
		http.httpBasic().disable();

		http.authorizeRequests()
				.antMatchers(HttpMethod.GET, "/openapi3.yaml", "/actuator/health", "/reports/**", "/error")
				.permitAll()
				.antMatchers(HttpMethod.POST, "/api/v1/foo")
				.permitAll()
				.antMatchers("/api/v1/**")
				.authenticated()
				.anyRequest()
				.denyAll();

		http.addFilterAt(
				generateAuthenticationFilter(), AbstractPreAuthenticatedProcessingFilter.class);

		http.exceptionHandling()
				.authenticationEntryPoint(authenticationEntryPoint)
				.accessDeniedHandler(accessDeniedHandler);

		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		return http.build();
	}

	/**
	 * TokenAuthenticationFilter를 생성합니다.
	 *
	 * @return TokenAuthenticationFilter
	 */
	public TokenAuthenticationFilter generateAuthenticationFilter() {
		TokenAuthenticationFilter tokenAuthenticationFilter = new TokenAuthenticationFilter();
		tokenAuthenticationFilter.setAuthenticationManager(new ProviderManager(tokenAuthProvider));
		return tokenAuthenticationFilter;
	}
}
