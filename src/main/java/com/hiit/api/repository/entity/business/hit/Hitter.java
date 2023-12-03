package com.hiit.api.repository.entity.business.hit;

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
public class Hitter {

	private final Long id;

	public static Hitter anonymous() {
		return Hitter.builder().build();
	}

	public static Hitter of(Long id) {
		return Hitter.builder().id(id).build();
	}

	public Optional<Long> getId() {
		return Optional.ofNullable(id);
	}
}
