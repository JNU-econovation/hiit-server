package com.hiit.api.repository.dao.bussiness.custom;

import com.hiit.api.repository.entity.business.it.InItEntity;
import com.hiit.api.repository.entity.business.member.HiitMemberEntity;
import com.hiit.api.repository.entity.business.with.QWithEntity;
import com.hiit.api.repository.entity.business.with.WithEntity;
import com.hiit.api.repository.entity.business.with.WithStatus;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
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
	public Page<WithEntity> findAllByInItAndStatus(
			InItEntity init,
			Pageable pageable,
			HiitMemberEntity member,
			LocalDateTime startTime,
			LocalDateTime endTime,
			WithStatus status) {
		QWithEntity with = QWithEntity.withEntity;

		List<WithEntity> withs =
				from(with)
						.where(
								with.inIt.eq(init),
								memberEqual(member),
								with.createAt.between(startTime, endTime),
								with.status.eq(status))
						.offset(pageable.getOffset())
						.limit(pageable.getPageSize())
						.fetch();

		long total =
				from(with)
						.where(with.inIt.eq(init), memberEqual(member), with.status.eq(status))
						.fetchCount();

		return new PageImpl<>(withs, pageable, total);
	}

	@Override
	public List<WithEntity> findAllByInItAndStatus(InItEntity init, HiitMemberEntity member) {
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

	@Override
	public Page<WithEntity> findAllByInItRandomAndStatus(
			Long initId, Integer size, WithStatus status) {

		QWithEntity with = QWithEntity.withEntity;

		List<WithEntity> withs =
				from(with)
						.where(with.inIt.id.eq(initId), with.status.eq(status))
						.orderBy(Expressions.numberTemplate(Double.class, "function('rand')").asc())
						.limit(size)
						.fetch();

		return new PageImpl<>(withs);
	}

	@Override
	public List<WithEntity> findAllByInItAnsStatus(
			InItEntity init, HiitMemberEntity member, WithStatus status) {
		QWithEntity with = QWithEntity.withEntity;

		return from(with)
				.where(with.inIt.eq(init), with.status.eq(status), memberEqual(member))
				.fetch();
	}

	@Override
	public Page<WithEntity> findAllByInItAndMemberIdAndStatus(
			InItEntity init, Long memberId, Pageable pageable, WithStatus status) {
		QWithEntity with = QWithEntity.withEntity;

		List<WithEntity> withs =
				from(with)
						.where(with.inIt.eq(init), with.memberId.eq(memberId), with.status.eq(status))
						.offset(pageable.getOffset())
						.limit(pageable.getPageSize())
						.fetch();

		long total =
				from(with)
						.where(with.inIt.eq(init), with.memberId.eq(memberId), with.status.eq(status))
						.fetchCount();

		return new PageImpl<>(withs, pageable, total);
	}
}
