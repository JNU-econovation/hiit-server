package com.hiit.api.domain.dao.it.relation;

import com.hiit.api.domain.dao.JpaDao;
import com.hiit.api.repository.entity.business.it.ItRelationEntity;

public interface ItRelationDao extends JpaDao<ItRelationEntity, Long> {

	Long countByTargetItId(Long targetItId);

	ItRelationEntity findByInItId(Long inItId);
}
