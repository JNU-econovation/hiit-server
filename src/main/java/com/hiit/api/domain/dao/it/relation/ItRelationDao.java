package com.hiit.api.domain.dao.it.relation;

import com.hiit.api.domain.dao.JpaDao;
import com.hiit.api.repository.entity.business.it.ItRelationEntity;
import com.hiit.api.repository.entity.business.it.ItStatus;
import java.util.Optional;

public interface ItRelationDao extends JpaDao<ItRelationEntity, Long> {

	Long countByItIdAndStatus(Long itId, ItStatus status);

	Optional<ItRelationEntity> findByInItIdAndStatus(Long inItId, ItStatus status);

	Optional<ItRelationEntity> findByInItId(Long inItId);

	void endById(Long id);

	Optional<ItRelationEntity> findByItIdAndStatus(Long itId, ItStatus status);
}
