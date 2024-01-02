package com.hiit.api.repository.dao.bussiness.custom;

import com.hiit.api.repository.entity.business.hit.HitEntity;
import com.hiit.api.repository.entity.business.hit.HitStatus;
import com.hiit.api.repository.entity.business.hit.Hitter;
import com.hiit.api.repository.entity.business.hit.QHitEntity;
import com.hiit.api.repository.entity.business.it.InItEntity;
import com.hiit.api.repository.entity.business.it.QInItEntity;
import com.hiit.api.repository.entity.business.with.WithEntity;
import com.hiit.api.repository.exception.InvalidParamException;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

public class HitCustomRepositoryImpl extends QuerydslRepositorySupport
		implements HitCustomRepository {

	public HitCustomRepositoryImpl() {
		super(HitEntity.class);
	}

	@Override
	public List<HitEntity> findAllByWithEntityAndHitterAndStatusAndCreateAtBetween(
			WithEntity with,
			Hitter hitter,
			HitStatus status,
			LocalDateTime startTime,
			LocalDateTime endTime) {

		if (hitter.equals(Hitter.anonymous())) {
			throw new InvalidParamException("익명 사용자는 조회할 수 없습니다.");
		}

		QHitEntity hitEntity = QHitEntity.hitEntity;

		return from(hitEntity)
				.where(
						hitEntity
								.withEntity
								.id
								.eq(with.getId())
								.and(hitEntity.hitter.eq(hitter))
								.and(hitEntity.status.eq(status))
								.and(hitEntity.createAt.between(startTime, endTime)))
				.orderBy(hitEntity.id.desc())
				.fetch();
	}

	@Override
	public Long countByInItEntityAndStatusAndCreateAtBetween(
			InItEntity inIt, HitStatus status, LocalDateTime startTime, LocalDateTime endTime) {
		QHitEntity hitEntity = QHitEntity.hitEntity;
		QInItEntity inItEntity = QInItEntity.inItEntity;

		return from(hitEntity)
				.join(hitEntity.withEntity.inIt, inItEntity)
				.fetchJoin()
				.where(
						hitEntity
								.status
								.eq(status)
								.and(hitEntity.createAt.between(startTime, endTime))
								.and(inItEntity.eq(inIt)))
				.fetchCount();
	}
}
