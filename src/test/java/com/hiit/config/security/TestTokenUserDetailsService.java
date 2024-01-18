package com.hiit.config.security;

import com.hiit.api.security.authentication.authority.Roles;
import com.hiit.api.security.authentication.token.TokenUserDetails;
import java.util.List;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class TestTokenUserDetailsService implements UserDetailsService {

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		return TokenUserDetails.builder()
				.id("0")
				.authorities(List.of(Roles.ROLE_USER.getAuthority()))
				.build();
	}
}
