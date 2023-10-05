package com.hiit.api.security.authentication.token;

import java.util.Collection;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

/** 토큰 인증을 위한 UserDetails 클래스 */
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class TokenUserDetails implements UserDetails {

	private static final String NOT_USE_PASSWORD_VALUE = "0";

	private List<GrantedAuthority> authorities;
	private String id;

	/**
	 * 유저의 권한을 반환합니다.
	 *
	 * @return 유저의 권한
	 */
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	/** 토큰 인증을 위해 필요한 메소드이지만, 사용하지 않으므로 "0"으로 반환합니다. */
	@Override
	public String getPassword() {
		return NOT_USE_PASSWORD_VALUE;
	}

	/**
	 * 유저의 id를 반환합니다.
	 *
	 * @return 유저의 id
	 */
	@Override
	public String getUsername() {
		return id;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}
}
