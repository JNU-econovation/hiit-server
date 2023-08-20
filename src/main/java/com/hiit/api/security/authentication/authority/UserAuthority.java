package com.hiit.api.security.authentication.authority;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;

/** USER 권한을 정의하는 클래스 */
@Getter
@ToString
@AllArgsConstructor
@Builder(toBuilder = true)
class UserAuthority implements GrantedAuthority {

	@Override
	public String getAuthority() {
		return Roles.USER.getRole();
	}
}
