package com.hiit.api.security.authentication.authority;

import lombok.Getter;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;

/** 유저의 권한을 정의하는 Enum Roles 클래스 */
@Getter
@ToString
public enum Roles {
	USER("ROLE_USER") {
		@Override
		public GrantedAuthority getAuthority() {
			return UserAuthority.builder().build();
		}
	};

	Roles(String role) {
		this.role = role;
	}

	/** 권한 */
	private final String role;

	/** 권한을 GrantedAuthority 타입으로 반환 */
	public abstract GrantedAuthority getAuthority();
}
