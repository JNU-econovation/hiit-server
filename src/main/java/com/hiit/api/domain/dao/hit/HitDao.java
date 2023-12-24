package com.hiit.api.domain.dao.hit;

import com.hiit.api.domain.dao.JpaDao;
import com.hiit.api.domain.dao.support.Period;
import java.util.Optional;

public interface HitDao extends JpaDao<HitData, Long> {

	Long countHitStatusByWithAndPeriod(Long with, Period period);

	Long countMissStatusByWithAndPeriod(Long with, Period period);

	Optional<HitData> findHitStatusByWithAndHitterAndPeriod(
			Long with, HitterInfo hitter, Period period);

	Optional<HitData> findMissStatusByWithAndHitterAndPeriod(
			Long with, HitterInfo hitter, Period period);

	Long countHitByInItAndPeriod(Long inIt, Period period);

	Long countMissByInItAndPeriod(Long inIt, Period period);
}
