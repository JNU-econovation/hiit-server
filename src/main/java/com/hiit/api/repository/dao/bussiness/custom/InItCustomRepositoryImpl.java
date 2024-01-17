package com.hiit.api.repository.dao.bussiness.custom;

import com.hiit.api.repository.entity.business.it.DayCodeList;
import com.hiit.api.repository.entity.business.it.InItEntity;
import com.hiit.api.repository.entity.business.it.ItStatus;
import com.hiit.api.repository.entity.business.it.QInItEntity;
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
	public void deleteByInItId(Long id) {
		QInItEntity inIt = QInItEntity.inItEntity;

		update(inIt).set(inIt.deleted, true).where(inIt.id.eq(id)).execute();
	}

	@Override
	public void endByIdWithItRelation(Long id, String title) {
		QInItEntity inIt = QInItEntity.inItEntity;

		update(inIt)
				.set(inIt.status, ItStatus.END)
				.set(inIt.title, title)
				.where(inIt.id.eq(id))
				.execute();
	}

	@Override
	public List<InItEntity> findAllByItIdAndStatusAndDayCode(
			Long itId, ItStatus status, DayCodeList dayCode) {

		QInItEntity inIt = QInItEntity.inItEntity;

		return from(inIt).where(inIt.status.eq(status).and(inIt.dayCode.eq(dayCode))).fetch();
	}

	@Override
	public Optional<InItEntity> findByHiitMemberAndTargetIdAndStatus(
			HiitMemberEntity hiitMember, Long itId, ItStatus status) {
		QInItEntity inIt = QInItEntity.inItEntity;

		return Optional.ofNullable(
				from(inIt).where(inIt.status.eq(status).and(inIt.hiitMember.eq(hiitMember))).fetchOne());
	}
}
