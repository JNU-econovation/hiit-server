package com.hiit.api.repository.dao.bussiness.custom;

import com.hiit.api.repository.config.EntityJpaDataSourceConfig;
import com.hiit.api.repository.entity.business.BarEntity;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

public class BarCustomRepositoryImpl extends QuerydslRepositorySupport
		implements BarCustomRepository {

	public BarCustomRepositoryImpl() {
		super(BarEntity.class);
	}

	@Override
	@PersistenceContext(unitName = EntityJpaDataSourceConfig.ENTITY_MANAGER_FACTORY_NAME)
	public void setEntityManager(
			@Qualifier(EntityJpaDataSourceConfig.ENTITY_MANAGER_FACTORY_NAME)
					EntityManager entityManager) {
		super.setEntityManager(entityManager);
	}
}
