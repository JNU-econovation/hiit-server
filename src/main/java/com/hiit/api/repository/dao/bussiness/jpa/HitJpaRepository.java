package com.hiit.api.repository.dao.bussiness.jpa;

import com.hiit.api.repository.entity.business.hit.HitEntity;
import com.hiit.api.repository.entity.business.hit.HitStatus;
import com.hiit.api.repository.entity.business.with.WithEntity;
import java.time.LocalDateTime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface HitJpaRepository extends JpaRepository<HitEntity, Long> {

	/** 특정 시간 사이에 윗에 생성된 Hit 개수 */
	Long countByWithEntityAndStatusAndCreateAtBetween(
			WithEntity with, HitStatus status, LocalDateTime startTime, LocalDateTime endTime);
}
