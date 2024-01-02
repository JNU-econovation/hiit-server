package com.hiit.api.repository.dao.bussiness.custom;

import com.hiit.api.repository.entity.business.hit.HitEntity;
import com.hiit.api.repository.entity.business.hit.HitStatus;
import com.hiit.api.repository.entity.business.hit.Hitter;
import com.hiit.api.repository.entity.business.it.InItEntity;
import com.hiit.api.repository.entity.business.with.WithEntity;
import java.time.LocalDateTime;
import java.util.List;

public interface HitCustomRepository {

	/** 특정 시간 사이에 특정 윗에 대해 특정 힛터가 생성한 특정 상태를 가진 Hit 목록 */
	List<HitEntity> findAllByWithEntityAndHitterAndStatusAndCreateAtBetween(
			WithEntity with,
			Hitter hitter,
			HitStatus status,
			LocalDateTime startTime,
			LocalDateTime endTime);

	/** 특정 시간 사이에 특정 잇에 대해 생성된 특정 상태를 가진 Hit 개수 */
	Long countByInItEntityAndStatusAndCreateAtBetween(
			InItEntity inIt, HitStatus status, LocalDateTime startTime, LocalDateTime endTime);
}
