package com.hiit.api.domain.dao.bar;

import com.hiit.api.domain.dao.AbstractDataConverter;
import com.hiit.api.domain.dao.AbstractJpaDao;
import com.hiit.api.repository.entity.business.BarEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/** 연관관계를 가지고 있는 엔티티의 경우 신경쓸 것이 더 많다. */
@Repository
public class BarDaoImpl extends AbstractJpaDao<BarEntity, Long, BarData> implements BarDao {

	public BarDaoImpl(
			JpaRepository<BarEntity, Long> jpaRepository,
			AbstractDataConverter<BarEntity, BarData> converter) {
		super(jpaRepository, converter);
	}
}
