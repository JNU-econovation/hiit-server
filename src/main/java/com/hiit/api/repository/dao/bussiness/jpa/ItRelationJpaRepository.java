package com.hiit.api.repository.dao.bussiness.jpa;

import com.hiit.api.repository.entity.business.it.ItRelationEntity;
import com.hiit.api.repository.entity.business.it.ItStatus;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface ItRelationJpaRepository extends JpaRepository<ItRelationEntity, Long> {

	/** 특정 타켓 잇에 대해 참여 중인 잇의 개수를 반환한다. */
	Long countByItIdAndStatus(Long itId, ItStatus status);

	/** 참여 중인 잇의 잇 참여 정보를 조회한다. */
	Optional<ItRelationEntity> findByInItIdAndStatus(Long inItId, ItStatus status);

	Optional<ItRelationEntity> findByInItId(Long inItId);

	Optional<ItRelationEntity> findByItIdAndStatus(Long itId, ItStatus status);
}
