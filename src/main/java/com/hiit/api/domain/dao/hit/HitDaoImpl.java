package com.hiit.api.domain.dao.hit;

import com.hiit.api.domain.dao.AbstractDataConverter;
import com.hiit.api.domain.dao.AbstractJpaDao;
import com.hiit.api.domain.dao.support.Period;
import com.hiit.api.repository.dao.bussiness.HitRepository;
import com.hiit.api.repository.entity.business.hit.HitEntity;
import com.hiit.api.repository.entity.business.hit.HitStatus;
import com.hiit.api.repository.entity.business.hit.Hitter;
import com.hiit.api.repository.entity.business.it.InItEntity;
import com.hiit.api.repository.entity.business.with.WithEntity;
import java.time.LocalDateTime;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public class HitDaoImpl extends AbstractJpaDao<HitEntity, Long, HitData> implements HitDao {

	private final HitRepository repository;

	public HitDaoImpl(
			JpaRepository<HitEntity, Long> jpaRepository,
			AbstractDataConverter<HitEntity, HitData> converter,
			HitRepository repository) {
		super(jpaRepository, converter);
		this.repository = repository;
	}

	@Override
	public Long countHitStatusByWithAndStatusAndPeriod(Long with, Period period) {
		return countStatusByWithAndStatusAndPeriodExecute(period, with, HitStatus.HIT);
	}

	@Override
	public Long countMissStatusByWithAndStatusAndPeriod(Long with, Period period) {
		return countStatusByWithAndStatusAndPeriodExecute(period, with, HitStatus.MISS);
	}

	private Long countStatusByWithAndStatusAndPeriodExecute(Period period, Long with, HitStatus hit) {
		LocalDateTime start = period.getStart();
		LocalDateTime end = period.getEnd();
		WithEntity withEntity = WithEntity.builder().id(with).build();
		return repository.countByWithEntityAndStatusAndCreateAtBetween(withEntity, hit, start, end);
	}

	@Override
	public Optional<HitData> findHitStatusByWithAndHitterAndStatusAndPeriod(
			Long with, HitterInfo hitter, Period period) {
		return findStatusByWithAndHitterAndStatusAndPeriodExecute(period, with, HitStatus.HIT, hitter);
	}

	@Override
	public Optional<HitData> findMissStatusByWithAndHitterAndStatusAndPeriod(
			Long with, HitterInfo hitter, Period period) {
		return findStatusByWithAndHitterAndStatusAndPeriodExecute(period, with, HitStatus.MISS, hitter);
	}

	private Optional<HitData> findStatusByWithAndHitterAndStatusAndPeriodExecute(
			Period period, Long with, HitStatus hit, HitterInfo hitter) {
		LocalDateTime start = period.getStart();
		LocalDateTime end = period.getEnd();
		WithEntity withEntity = WithEntity.builder().id(with).build();
		Hitter hitHitter = null;
		if (hitter.getId().isEmpty()) {
			hitHitter = Hitter.anonymous();
		} else {
			hitHitter = Hitter.builder().id(hitter.getId().get()).build();
		}
		assert hitHitter != null;
		return repository
				.findByWithEntityAndHitterAndStatusAndCreateAtBetween(
						withEntity, hitHitter, hit, start, end)
				.map(converter::from);
	}

	@Override
	public Long countHitByInItAndStatusAndPeriod(Long inIt, Period period) {
		return countStatusByInItAndStatusAndPeriodExecute(period, HitStatus.HIT, inIt);
	}

	@Override
	public Long countMissByInItAndStatusAndPeriod(Long inIt, Period period) {
		return countStatusByInItAndStatusAndPeriodExecute(period, HitStatus.MISS, inIt);
	}

	private Long countStatusByInItAndStatusAndPeriodExecute(Period period, HitStatus hit, Long inIt) {
		LocalDateTime start = period.getStart();
		LocalDateTime end = period.getEnd();
		HitStatus hitStatus = hit;
		InItEntity inItEntity = InItEntity.builder().id(inIt).build();
		return repository.countByInItEntityAndStatusAndCreateAtBetween(
				inItEntity, hitStatus, start, end);
	}
}
