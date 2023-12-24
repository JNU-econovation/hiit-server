package com.hiit.api.domain.service.end.it;

import com.hiit.api.domain.dao.it.relation.ItRelationDao;
import com.hiit.api.domain.dao.it.relation.ItRelationData;
import com.hiit.api.domain.dao.it.relation.TargetItTypeInfo;
import com.hiit.api.domain.exception.DataNotFoundException;
import com.hiit.api.domain.service.manager.it.time.ItTimeInfo;
import com.hiit.api.domain.service.manager.it.time.ItTimeInfoExecutor;
import com.hiit.api.domain.service.manager.it.time.ItTimeInfoExecutorManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class EndItTimeInfoService {

	private final ItRelationDao itRelationDao;
	private final ItTimeInfoExecutorManager itTimeInfoExecutorManager;

	public EndItTimeInfo read(Long relationId) {
		ItRelationData itRelation = getRelation(relationId);
		Long targetId = itRelation.getTargetId();
		TargetItTypeInfo targetType = itRelation.getTargetType();
		ItTimeInfoExecutor executor = itTimeInfoExecutorManager.getExecutor(targetType);
		ItTimeInfo source = executor.execute(targetId);
		return EndItTimeInfo.builder()
				.startTime(source.getStartTime())
				.endTime(source.getEndTime())
				.build();
	}

	private ItRelationData getRelation(Long relationId) {
		return itRelationDao
				.findById(relationId)
				.orElseThrow(() -> new DataNotFoundException("ItRelation id : " + relationId));
	}
}
