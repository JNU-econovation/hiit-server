package com.hiit.api.domain.dao.it.relation;

import com.hiit.api.domain.dao.AbstractDataConverter;
import com.hiit.api.domain.dao.AbstractJpaDao;
import com.hiit.api.repository.dao.bussiness.jpa.ItRelationJpaRepository;
import com.hiit.api.repository.entity.business.it.ItRelationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public class ItRelationDaoImpl extends AbstractJpaDao<ItRelationEntity, Long, ItRelationData>
		implements ItRelationDao {

	private final ItRelationJpaRepository repository;

	public ItRelationDaoImpl(
			JpaRepository<ItRelationEntity, Long> jpaRepository,
			AbstractDataConverter<ItRelationEntity, ItRelationData> converter,
			ItRelationJpaRepository repository) {
		super(jpaRepository, converter);
		this.repository = repository;
	}

	@Override
	public Long countByTargetItId(Long targetItId) {
		return repository.countByTargetItId(targetItId);
	}
}
