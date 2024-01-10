package com.hiit.api.domain.service.it;

import com.hiit.api.domain.dao.it.relation.ItRelationDao;
import com.hiit.api.domain.model.it.GetItId;
import com.hiit.api.domain.model.it.relation.GetItRelationId;
import com.hiit.api.repository.entity.business.it.InItEntity;
import com.hiit.api.repository.entity.business.it.ItRelationEntity;
import com.hiit.api.repository.entity.business.it.TargetItType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ItRelationCommand {

	private final ItRelationDao itRelationDao;

	@Transactional
	public void delete(GetItRelationId itRelationId) {
		itRelationDao.deleteById(itRelationId.getId());
	}

	@Transactional
	public ItRelationEntity save(GetItId itId, InItEntity inIt) {
		ItRelationEntity relation =
				ItRelationEntity.builder()
						.targetItId(itId.getId())
						.targetItType(TargetItType.REGISTERED_IT)
						.inIt(inIt)
						.build();
		inIt.associate(relation);
		return itRelationDao.save(relation);
	}
}
