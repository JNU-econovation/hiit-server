package com.hiit.api.domain.dao.hit;

import com.hiit.api.domain.dao.JpaDao;
import com.hiit.api.domain.model.hit.HitterDetail;
import com.hiit.api.domain.support.entity.Period;
import com.hiit.api.repository.entity.business.hit.HitEntity;
import java.util.List;

public interface HitDao extends JpaDao<HitEntity, Long> {

	Long countHitStatusByWithAndPeriod(Long withId, Period period);

	Long countMissStatusByWithAndPeriod(Long withId, Period period);

	List<HitEntity> findHitStatusByWithAndHitterAndPeriod(
			Long withId, HitterDetail hitter, Period period);

	List<HitEntity> findMissStatusByWithAndHitterAndPeriod(
			Long withId, HitterDetail hitter, Period period);

	Long countHitByInItAndPeriod(Long inItId, Period period);

	Long countMissByInItAndPeriod(Long inItId, Period period);
}
