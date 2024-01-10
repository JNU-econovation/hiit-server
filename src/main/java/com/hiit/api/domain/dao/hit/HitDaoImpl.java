package com.hiit.api.domain.dao.hit;

import com.hiit.api.domain.dao.AbstractJpaDao;
import com.hiit.api.domain.model.hit.HitterDetail;
import com.hiit.api.domain.support.entity.Period;
import com.hiit.api.repository.dao.bussiness.HitRepository;
import com.hiit.api.repository.entity.business.hit.HitEntity;
import com.hiit.api.repository.entity.business.hit.HitStatus;
import com.hiit.api.repository.entity.business.hit.Hitter;
import com.hiit.api.repository.entity.business.it.InItEntity;
import com.hiit.api.repository.entity.business.with.WithEntity;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public class HitDaoImpl extends AbstractJpaDao<HitEntity, Long> implements HitDao {

	private final HitRepository repository;

	public HitDaoImpl(JpaRepository<HitEntity, Long> jpaRepository, HitRepository repository) {
		super(jpaRepository);
		this.repository = repository;
	}

	@Override
	public Long countHitStatusByWithAndPeriod(Long withId, Period period) {
		return countStatusByWithAndStatusAndPeriodExecute(period, withId, HitStatus.HIT);
	}

	@Override
	public Long countMissStatusByWithAndPeriod(Long withId, Period period) {
		return countStatusByWithAndStatusAndPeriodExecute(period, withId, HitStatus.MISS);
	}

	private Long countStatusByWithAndStatusAndPeriodExecute(Period period, Long with, HitStatus hit) {
		LocalDateTime start = period.getStart();
		LocalDateTime end = period.getEnd();
		WithEntity withEntity = WithEntity.builder().id(with).build();
		return repository.countByWithEntityAndStatusAndCreateAtBetween(withEntity, hit, start, end);
	}

	@Override
	public List<HitEntity> findHitStatusByWithAndHitterAndPeriod(
			Long withId, HitterDetail hitter, Period period) {
		return findStatusByWithAndHitterAndStatusAndPeriodExecute(
				period, withId, HitStatus.HIT, hitter);
	}

	@Override
	public List<HitEntity> findMissStatusByWithAndHitterAndPeriod(
			Long withId, HitterDetail hitter, Period period) {
		return findStatusByWithAndHitterAndStatusAndPeriodExecute(
				period, withId, HitStatus.MISS, hitter);
	}

	private List<HitEntity> findStatusByWithAndHitterAndStatusAndPeriodExecute(
			Period period, Long with, HitStatus hit, HitterDetail hitter) {
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
		return repository.findAllByWithEntityAndHitterAndStatusAndCreateAtBetween(
				withEntity, hitHitter, hit, start, end);
	}

	@Override
	public Long countHitByInItAndPeriod(Long inItId, Period period) {
		return countStatusByInItAndStatusAndPeriodExecute(period, HitStatus.HIT, inItId);
	}

	@Override
	public Long countMissByInItAndPeriod(Long inItId, Period period) {
		return countStatusByInItAndStatusAndPeriodExecute(period, HitStatus.MISS, inItId);
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
