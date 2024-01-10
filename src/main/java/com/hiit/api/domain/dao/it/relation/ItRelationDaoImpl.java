package com.hiit.api.domain.dao.it.relation;

import com.hiit.api.domain.dao.AbstractJpaDao;
import com.hiit.api.domain.util.JsonConverter;
import com.hiit.api.domain.util.LogSourceGenerator;
import com.hiit.api.repository.dao.bussiness.jpa.ItRelationJpaRepository;
import com.hiit.api.repository.entity.business.it.ItRelationEntity;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public class ItRelationDaoImpl extends AbstractJpaDao<ItRelationEntity, Long>
		implements ItRelationDao {

	private final ItRelationJpaRepository repository;

	private final JsonConverter jsonConverter;
	private final LogSourceGenerator logSourceGenerator;

	public ItRelationDaoImpl(
			JpaRepository<ItRelationEntity, Long> jpaRepository,
			ItRelationJpaRepository repository,
			JsonConverter jsonConverter,
			LogSourceGenerator logSourceGenerator) {
		super(jpaRepository);
		this.repository = repository;
		this.jsonConverter = jsonConverter;
		this.logSourceGenerator = logSourceGenerator;
	}

	@Override
	public Long countByItId(Long itId) {
		return repository.countByTargetItId(itId);
	}

	@Override
	public Optional<ItRelationEntity> findByInItId(Long inItId) {
		return repository.findByInItId(inItId);
	}
}
