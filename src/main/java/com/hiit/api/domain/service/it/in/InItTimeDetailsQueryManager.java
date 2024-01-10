package com.hiit.api.domain.service.it.in;

import com.hiit.api.domain.model.it.in.GetInItId;
import com.hiit.api.domain.model.it.in.InIt;
import com.hiit.api.domain.model.it.in.InItStatusDetails;
import com.hiit.api.domain.model.member.GetMemberId;
import java.util.Collection;
import java.util.Map;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class InItTimeDetailsQueryManager {

	private final Map<String, InItTimeDetailsQuery> readInItServiceMap;

	@Transactional(readOnly = true)
	public InIt query(InItStatusDetails status, GetInItId inItId, GetMemberId memberId) {
		Collection<InItTimeDetailsQuery> services = readInItServiceMap.values();
		InItTimeDetailsQuery service =
				services.stream()
						.filter(s -> Objects.equals(s.getStatus(), status))
						.findFirst()
						.orElseThrow();
		return service.query(inItId, memberId);
	}
}
