package com.hiit.api.domain.dao.it.relation;

import com.hiit.api.domain.dao.JpaDao;

public interface ItRelationDao extends JpaDao<ItRelationData, Long> {

	Long countByTargetItId(Long targetItId);
}
