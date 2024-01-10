package com.hiit.api.domain.model.hit;

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
public class HitterDetail {

	private final Long id;

	public static HitterDetail anonymous() {
		return HitterDetail.builder().build();
	}

	public static HitterDetail of(final Long id) {
		return HitterDetail.builder().id(id).build();
	}

	public Optional<Long> getId() {
		return Optional.ofNullable(id);
	}
}
