package com.hiit.api.repository.document.member;

import static java.util.stream.Collectors.toMap;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
public class ItWithStats implements Serializable {

	private List<ItWithStat> itWithCountStats;

	public ItWithStats(Map<Long, Long> itWithCountStats) {
		this.itWithCountStats =
				new ArrayList<>(
						itWithCountStats.entrySet().stream()
								.map(
										entry ->
												ItWithStat.builder()
														.itId(entry.getKey())
														.withCount(entry.getValue())
														.build())
								.collect(toMap(ItWithStat::getItId, itWithStat -> itWithStat))
								.values());
	}
}
