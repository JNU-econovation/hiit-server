package com.hiit.api.domain.service.end.it;

import com.hiit.api.domain.dao.it.relation.ItRelationDao;
import com.hiit.api.domain.dao.it.relation.ItRelationData;
import com.hiit.api.domain.dao.it.relation.TargetItTypeInfo;
import com.hiit.api.domain.exception.DataNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ItTimeInfoService {

	private final ItRelationDao itRelationDao;
	private final ItTimeInfoExecutorManager itTimeInfoExecutorManager;

	public ItTimeInfo read(Long relationId) {
		ItRelationData itRelation = getRelation(relationId);
		Long targetId = itRelation.getTargetId();
		TargetItTypeInfo targetType = itRelation.getTargetType();
		ItTimeInfoExecutor executor = itTimeInfoExecutorManager.getExecutor(targetType);
		return executor.execute(targetId);
	}

	private ItRelationData getRelation(Long relationId) {
		return itRelationDao
				.findById(relationId)
				.orElseThrow(() -> new DataNotFoundException("itRelation : " + relationId));
	}
}
