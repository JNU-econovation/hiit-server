package com.hiit.api.repository.document.member;

import static java.util.stream.Collectors.toMap;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
public class ItWithStats implements Serializable {

	private List<ItWithStat> itWithCountStats = new ArrayList<>();

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

	public void startIt(Long itId, String type) {
		itWithCountStats.add(ItWithStat.builder().itId(itId).type(type).withCount(0L).build());
	}

	public void endIt(Long itId) {
		itWithCountStats.removeIf(itWithStat -> itWithStat.getItId().equals(itId));
	}

	public void createWith(Long inItId) {
		Optional<ItWithStat> itWithStat =
				itWithCountStats.stream().filter(s -> s.getItId().equals(inItId)).findFirst();
		itWithStat.ifPresent(ItWithStat::increaseWithCount);
	}

	public void deleteWith(Long inItId) {
		Optional<ItWithStat> itWithStat =
				itWithCountStats.stream().filter(s -> s.getItId().equals(inItId)).findFirst();
		itWithStat.ifPresent(ItWithStat::decreaseWithCount);
	}
}
