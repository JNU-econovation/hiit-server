package com.hiit.api.repository.dao.bussiness.custom;

import com.hiit.api.repository.entity.business.it.InItEntity;
import com.hiit.api.repository.entity.business.member.HiitMemberEntity;
import com.hiit.api.repository.entity.business.with.QWithEntity;
import com.hiit.api.repository.entity.business.with.WithEntity;
import com.querydsl.core.types.dsl.BooleanExpression;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

public class WithCustomRepositoryImpl extends QuerydslRepositorySupport
		implements WithCustomRepository {
	public WithCustomRepositoryImpl() {
		super(WithCustomRepositoryImpl.class);
	}

	@Override
	public Page<WithEntity> findAllByInIt(
			InItEntity init, Pageable pageable, HiitMemberEntity member) {
		QWithEntity with = QWithEntity.withEntity;

		List<WithEntity> withs =
				from(with)
						.where(with.inIt.eq(init), memberEqual(member))
						.offset(pageable.getOffset())
						.limit(pageable.getPageSize())
						.fetch();

		long total = from(with).where(with.inIt.eq(init), memberEqual(member)).fetchCount();

		return new PageImpl<>(withs, pageable, total);
	}

	private BooleanExpression memberEqual(HiitMemberEntity member) {
		if (Objects.isNull(member)) {
			return null;
		}
		return QWithEntity.withEntity.inIt.hiitMember.eq(member);
	}

	@Override
	public Optional<WithEntity> findByInItEntityAndHiitMemberAndCreateAtBetween(
			InItEntity inIt,
			HiitMemberEntity hiitMemberEntity,
			LocalDateTime startTime,
			LocalDateTime endTime) {
		QWithEntity with = QWithEntity.withEntity;

		return Optional.ofNullable(
				from(with)
						.where(
								with.inIt.eq(inIt),
								with.inIt.hiitMember.eq(hiitMemberEntity),
								with.createAt.between(startTime, endTime))
						.fetchOne());
	}
}
