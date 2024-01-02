package com.hiit.api.repository.dao.bussiness.jpa;

import com.hiit.api.repository.dao.UniqueOrOptionalParam;
import com.hiit.api.repository.entity.business.it.ItRelationEntity;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface ItRelationJpaRepository extends JpaRepository<ItRelationEntity, Long> {

	/** 특정 타켓 잇에 대해 참여 중인 잇의 개수를 반환한다. */
	Long countByTargetItId(Long targetItId);

	/** 참여 중인 잇의 잇 참여 정보를 조회한다. */
	Optional<ItRelationEntity> findByInItId(@UniqueOrOptionalParam Long inItId);
}
