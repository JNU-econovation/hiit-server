package com.hiit.api.domain.dao.hit;

import com.hiit.api.domain.dao.JpaDao;
import com.hiit.api.domain.dao.support.Period;
import java.util.Optional;

public interface HitDao extends JpaDao<HitData, Long> {

	Long countHitStatusByWithAndStatusAndPeriod(Long with, Period period);

	Long countMissStatusByWithAndStatusAndPeriod(Long with, Period period);

	Optional<HitData> findHitStatusByWithAndHitterAndStatusAndPeriod(
			Long with, HitterInfo hitter, Period period);

	Optional<HitData> findMissStatusByWithAndHitterAndStatusAndPeriod(
			Long with, HitterInfo hitter, Period period);

	Long countHitByInItAndStatusAndPeriod(Long inIt, Period period);

	Long countMissByInItAndStatusAndPeriod(Long inIt, Period period);
}
