package com.hiit.api.domain.service.it;

import com.hiit.api.domain.model.it.BasicIt;
import com.hiit.api.domain.model.it.GetItId;
import com.hiit.api.domain.model.it.in.GetInItId;
import com.hiit.api.domain.model.it.relation.ItTypeDetails;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ItTypeQueryManager {

	private final Map<String, ItTypeQuery> services;

	@Transactional(readOnly = true)
	public BasicIt query(ItTypeDetails type, GetItId itId) {
		ItTypeQuery service =
				services.values().stream()
						.filter(s -> s.getType().equals(type))
						.findFirst()
						.orElseThrow(IllegalAccessError::new);
		return service.query(type, itId);
	}

	@Transactional(readOnly = true)
	public BasicIt query(ItTypeDetails type, GetInItId inItId) {
		ItTypeQuery service =
				services.values().stream()
						.filter(s -> s.getType().equals(type))
						.findFirst()
						.orElseThrow(IllegalAccessError::new);
		return service.query(type, inItId);
	}
}
