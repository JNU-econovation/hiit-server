package com.hiit.api.domain.dao.it.relation;

import com.hiit.api.domain.dao.JpaDao;
import com.hiit.api.repository.entity.business.it.ItRelationEntity;
import java.util.Optional;

public interface ItRelationDao extends JpaDao<ItRelationEntity, Long> {

	Long countByItId(Long itId);

	Optional<ItRelationEntity> findByInItId(Long inItId);
}
