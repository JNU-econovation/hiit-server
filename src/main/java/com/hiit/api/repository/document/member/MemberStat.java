package com.hiit.api.repository.document.member;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@Builder
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class MemberStat implements Serializable {

	private Long id;
	private Long totalItCount;
	private Long totalWithCount;
	private ItWithStats itWithCountStats;

	public Optional<ItWithStat> getItWithCountStats(Long itId) {
		return itWithCountStats.getItWithCountStats().stream()
				.filter(itWithStat -> itWithStat.getItId().equals(itId))
				.findFirst();
	}

	// for convert to json
	public Map<Long, Long> getItWithCountStats() {
		List<ItWithStat> sources = itWithCountStats.getItWithCountStats();
		return sources.stream()
				.collect(java.util.stream.Collectors.toMap(ItWithStat::getItId, ItWithStat::getWithCount));
	}

	// for convert from json
	public void setItWithCountStats(Map<Long, Long> itWithCountStats) {
		this.itWithCountStats = new ItWithStats(itWithCountStats);
	}
}
