package com.hiit.api.domain.service.it;

import com.hiit.api.domain.model.it.BasicIt;
import com.hiit.api.domain.model.it.relation.TargetItTypeInfo;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class BrowseTargetItServiceManager {

	private final Map<String, BrowseTargetItService> browseTargetItServiceMap;

	@Transactional
	public BasicIt browse(TargetItTypeInfo type, Long itId) {
		BrowseTargetItService service =
				browseTargetItServiceMap.values().stream()
						.filter(s -> s.getType().equals(type))
						.findFirst()
						.orElseThrow(() -> new IllegalArgumentException("Invalid type: " + type));
		return service.browse(type, itId);
	}
}
