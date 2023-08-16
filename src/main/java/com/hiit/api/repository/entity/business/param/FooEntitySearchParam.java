package com.hiit.api.repository.entity.business.param;

import java.util.Optional;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class FooEntitySearchParam {
	private Long id;
	private String name;

	public Optional<Long> getId() {
		return Optional.ofNullable(id);
	}

	public Optional<String> getName() {
		return Optional.ofNullable(name);
	}
}
