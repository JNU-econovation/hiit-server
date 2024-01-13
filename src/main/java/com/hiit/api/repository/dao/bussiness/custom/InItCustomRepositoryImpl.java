package com.hiit.api.repository.dao.bussiness.custom;

import com.hiit.api.repository.entity.business.it.DayCodeList;
import com.hiit.api.repository.entity.business.it.InItEntity;
import com.hiit.api.repository.entity.business.it.ItStatus;
import com.hiit.api.repository.entity.business.it.QInItEntity;
import com.hiit.api.repository.entity.business.it.QItRelationEntity;
import com.hiit.api.repository.entity.business.member.HiitMemberEntity;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

public class InItCustomRepositoryImpl extends QuerydslRepositorySupport
		implements InItCustomRepository {

	public InItCustomRepositoryImpl() {
		super(InItEntity.class);
	}

	@Override
	public void deleteByIdWithItRelation(Long id) {
		QInItEntity inIt = QInItEntity.inItEntity;

		update(inIt).set(inIt.deleted, true).where(inIt.id.eq(id)).execute();

		QItRelationEntity relation = QItRelationEntity.itRelationEntity;
		update(relation).set(relation.deleted, true).where(relation.inIt.id.eq(id)).execute();
	}

	@Override
	public void endByIdWithItRelation(Long id, String title) {
		QInItEntity inIt = QInItEntity.inItEntity;

		update(inIt)
				.set(inIt.status, ItStatus.END)
				.set(inIt.title, title)
				.where(inIt.id.eq(id))
				.execute();

		QItRelationEntity relation = QItRelationEntity.itRelationEntity;
		update(relation).set(relation.deleted, true).where(relation.inIt.id.eq(id)).execute();
	}

	@Override
	public List<InItEntity> findAllByTargetIdAndStatusAndDayCode(
			Long targetId, ItStatus status, DayCodeList dayCode) {

		QInItEntity inIt = QInItEntity.inItEntity;
		QItRelationEntity relation = QItRelationEntity.itRelationEntity;

		return from(inIt)
				.leftJoin(inIt.itRelationEntity, relation)
				.fetchJoin()
				.where(
						inIt.status
								.eq(status)
								.and(inIt.dayCode.eq(dayCode).and(relation.targetItId.eq(targetId))))
				.fetch();
	}

	@Override
	public Optional<InItEntity> findByHiitMemberAndTargetIdAndStatus(
			HiitMemberEntity hiitMember, Long targetId, ItStatus status) {
		QInItEntity inIt = QInItEntity.inItEntity;
		QItRelationEntity relation = QItRelationEntity.itRelationEntity;

		return Optional.ofNullable(
				from(inIt)
						.leftJoin(inIt.itRelationEntity, relation)
						.fetchJoin()
						.where(
								inIt.status
										.eq(status)
										.and(inIt.hiitMember.eq(hiitMember))
										.and(relation.targetItId.eq(targetId)))
						.fetchOne());
	}
}
