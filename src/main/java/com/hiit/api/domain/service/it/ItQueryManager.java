package com.hiit.api.domain.service.it;

import com.hiit.api.domain.model.it.BasicIt;
import com.hiit.api.domain.model.it.GetItId;
import com.hiit.api.domain.model.it.relation.ItTypeDetails;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ItQueryManager {

	private final Map<String, ItQuery> services;

	@Transactional(readOnly = true)
	public BasicIt query(ItTypeDetails type, GetItId itId) {
		ItQuery service =
				services.values().stream()
						.filter(s -> s.getType().equals(type))
						.findFirst()
						.orElseThrow(IllegalAccessError::new);
		return service.query(type, itId);
	}
}
