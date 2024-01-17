package com.hiit.api.repository.document.member;

import static java.util.stream.Collectors.toMap;

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

	private Long memberId;
	@Builder.Default private Long totalItCount = 0L;
	@Builder.Default private Long totalWithCount = 0L;
	@Builder.Default private ItWithStats itWithCountStats = new ItWithStats();

	public Optional<ItWithStat> getItWithCountStats(Long itId) {
		return itWithCountStats.getItWithCountStats().stream()
				.filter(itWithStat -> itWithStat.getItId().equals(itId))
				.findFirst();
	}

	// for convert to json
	public Map<Long, Long> getItWithCountStats() {
		List<ItWithStat> sources = itWithCountStats.getItWithCountStats();
		return sources.stream().collect(toMap(ItWithStat::getItId, ItWithStat::getWithCount));
	}

	// for convert from json
	public void setItWithCountStats(Map<Long, Long> itWithCountStats) {
		this.itWithCountStats = new ItWithStats(itWithCountStats);
	}

	public void participateIt(Long itId, String type) {
		totalItCount++;
		itWithCountStats.startIt(itId, type);
	}

	public void endIt(Long itId) {
		totalItCount--;
		itWithCountStats.endIt(itId);
	}

	public void createWith(Long inItId) {
		totalWithCount++;
		itWithCountStats.createWith(inItId);
	}

	public void deleteWith(Long inItId) {
		totalWithCount--;
		itWithCountStats.deleteWith(inItId);
	}
}
