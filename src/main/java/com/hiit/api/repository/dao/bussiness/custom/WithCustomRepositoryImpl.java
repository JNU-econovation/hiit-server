package com.hiit.api.repository.dao.bussiness.custom;

import com.hiit.api.repository.entity.business.it.InItEntity;
import com.hiit.api.repository.entity.business.member.HiitMemberEntity;
import com.hiit.api.repository.entity.business.with.QWithEntity;
import com.hiit.api.repository.entity.business.with.WithEntity;
import com.querydsl.core.types.dsl.BooleanExpression;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
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
			InItEntity init,
			Pageable pageable,
			HiitMemberEntity member,
			LocalDateTime startTime,
			LocalDateTime endTime) {
		QWithEntity with = QWithEntity.withEntity;

		List<WithEntity> withs =
				from(with)
						.where(
								with.inIt.eq(init), memberEqual(member), with.createAt.between(startTime, endTime))
						.offset(pageable.getOffset())
						.limit(pageable.getPageSize())
						.fetch();

		long total = from(with).where(with.inIt.eq(init), memberEqual(member)).fetchCount();

		return new PageImpl<>(withs, pageable, total);
	}

	@Override
	public List<WithEntity> findAllByInIt(InItEntity init, HiitMemberEntity member) {
		QWithEntity with = QWithEntity.withEntity;

		return from(with).where(with.inIt.eq(init), memberEqual(member)).fetch();
	}

	private BooleanExpression memberEqual(HiitMemberEntity member) {
		if (Objects.isNull(member)) {
			return null;
		}
		return QWithEntity.withEntity.memberId.eq(member.getId());
	}

	@Override
	public List<WithEntity> findAllByInItEntityAndHiitMemberAndCreateAtBetween(
			InItEntity inIt,
			HiitMemberEntity hiitMemberEntity,
			LocalDateTime startTime,
			LocalDateTime endTime) {
		QWithEntity with = QWithEntity.withEntity;

		return from(with)
				.where(
						with.inIt.eq(inIt),
						with.inIt.hiitMember.eq(hiitMemberEntity),
						with.createAt.between(startTime, endTime))
				.fetch();
	}
}
