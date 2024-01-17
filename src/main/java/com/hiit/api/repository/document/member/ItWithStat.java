package com.hiit.api.repository.document.member;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@EqualsAndHashCode
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ItWithStat implements Serializable {

	private Long inItId;
	private String type;
	private Long withCount;

	public void increaseWithCount() {
		this.withCount++;
	}

	public void decreaseWithCount() {
		this.withCount--;
	}
}
