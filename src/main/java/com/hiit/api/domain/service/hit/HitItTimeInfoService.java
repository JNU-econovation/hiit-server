package com.hiit.api.domain.service.hit;

import com.hiit.api.domain.dao.it.relation.ItRelationDao;
import com.hiit.api.domain.dao.it.relation.ItRelationData;
import com.hiit.api.domain.dao.it.relation.TargetItTypeInfo;
import com.hiit.api.domain.dao.with.WithData;
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
public class HitItTimeInfoService {

	private final ItRelationDao itRelationDao;
	private final ItTimeInfoExecutorManager itTimeInfoExecutorManager;

	public HitItTimeInfo read(WithData with) {
		Long inItId = with.getInItId();
		ItRelationData itRelation = itRelationDao.findByInItId(inItId);
		return this.read(itRelation.getId());
	}

	public HitItTimeInfo read(Long relationId) {
		ItRelationData itRelation = getRelation(relationId);
		Long targetId = itRelation.getTargetId();
		TargetItTypeInfo targetType = itRelation.getTargetType();
		ItTimeInfoExecutor executor = itTimeInfoExecutorManager.getExecutor(targetType);
		ItTimeInfo source = executor.execute(targetId);
		return HitItTimeInfo.builder()
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
