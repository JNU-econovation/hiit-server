package com.hiit.api.repository.dao.bussiness.custom;

import com.hiit.api.repository.entity.business.hit.HitEntity;
import com.hiit.api.repository.entity.business.hit.HitStatus;
import com.hiit.api.repository.entity.business.hit.Hitter;
import com.hiit.api.repository.entity.business.it.InItEntity;
import com.hiit.api.repository.entity.business.with.WithEntity;
import java.time.LocalDateTime;
import java.util.Optional;

public interface HitCustomRepository {

	/**
	 * 특정 시간 사이에 특정 윗에 대해 특정 힛터가 생성한 Hit<br>
	 * <br>
	 * 윗에 대한 Hit은 최대 1개이다.<br>
	 * 그리고 하루에 한번만 생성할 수 있다.<br>
	 * 따라서 특정 날짜를 당일로 설정한다면 최대 1개의 Hit을 반하여 Optional로 반환한다.<br>
	 * <br>
	 *
	 * @param hitter 특정 힛터를 통한 조회는 가능하지만 익명 힛터는 가능하지 않다. @Exception InvalidParamException hitter가 익명
	 *     힛터일 경우 발생한다.
	 */
	Optional<HitEntity> findByWithEntityAndHitterAndStatusAndCreateAtBetween(
			WithEntity with,
			Hitter hitter,
			HitStatus status,
			LocalDateTime startTime,
			LocalDateTime endTime);

	/** 특정 시간 사이에 특정 잇에 대해 생성된 Hit 개수 */
	Long countByInItEntityAndStatusAndCreateAtBetween(
			InItEntity inIt, HitStatus status, LocalDateTime startTime, LocalDateTime endTime);
}
