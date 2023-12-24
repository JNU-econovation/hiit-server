package com.hiit.api.repository.dao.bussiness.jpa;

import com.hiit.api.repository.entity.business.it.ItRelationEntity;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface ItRelationJpaRepository extends JpaRepository<ItRelationEntity, Long> {

	/** 특정 타켓 잇에 대해 참여 중인 잇의 개수를 반환한다. */
	Long countByTargetItId(Long targetItId);

	Optional<ItRelationEntity> findByInItId(Long inItId);
}
