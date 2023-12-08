package com.hiit.api.domain.dao.hit;

import java.util.Optional;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@EqualsAndHashCode
@RequiredArgsConstructor
@Builder(toBuilder = true)
public class HitterInfo {

	private final Long id;

	public static HitterInfo anonymous() {
		return HitterInfo.builder().build();
	}

	public static HitterInfo of(Long id) {
		return HitterInfo.builder().id(id).build();
	}

	public Optional<Long> getId() {
		return Optional.ofNullable(id);
	}
}
