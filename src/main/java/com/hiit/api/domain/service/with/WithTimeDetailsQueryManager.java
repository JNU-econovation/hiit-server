package com.hiit.api.domain.service.with;

import com.hiit.api.domain.model.it.in.InItStatusDetails;
import com.hiit.api.domain.model.member.GetMemberId;
import com.hiit.api.domain.model.with.GetWithId;
import com.hiit.api.domain.model.with.With;
import java.util.Collection;
import java.util.Map;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class WithTimeDetailsQueryManager {

	private final Map<String, WithTimeDetailsQuery> readInItServiceMap;

	@Transactional(readOnly = true)
	public With query(InItStatusDetails status, GetWithId withId, GetMemberId memberId) {
		Collection<WithTimeDetailsQuery> services = readInItServiceMap.values();
		WithTimeDetailsQuery service =
				services.stream()
						.filter(s -> Objects.equals(s.getStatus(), status))
						.findFirst()
						.orElseThrow();
		return service.query(withId, memberId);
	}
}
