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

	public Optional<ItWithStat> getItWithCountStats(Long inItId) {
		return itWithCountStats.getItWithCountStats().stream()
				.filter(itWithStat -> itWithStat.getInItId().equals(inItId))
				.findFirst();
	}

	// for convert to json
	public Map<Long, Map<String, Long>> getItWithCountStats() {
		List<ItWithStat> sources = itWithCountStats.getItWithCountStats();
		return sources.stream()
				.collect(
						toMap(
								ItWithStat::getInItId,
								(stat) -> {
									return Map.of(stat.getType(), stat.getWithCount());
								}));
	}

	// for convert from json
	public void setItWithCountStats(Map<Long, Map<String, Long>> itWithCountStats) {
		this.itWithCountStats = new ItWithStats(itWithCountStats);
	}

	public void participateIt(Long inItId, String type) {
		totalItCount++;
		itWithCountStats.startIt(inItId, type);
	}

	public void endIt(Long inItId) {
		ItWithStat itWithStat = this.getItWithCountStats(inItId).orElse(null);
		assert itWithStat != null;
		Long withCount = itWithStat.getWithCount();
		totalItCount--;
		totalWithCount -= withCount;
		itWithCountStats.endIt(inItId);
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
