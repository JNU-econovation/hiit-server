package com.hiit.api.repository.dao.bussiness;

import com.hiit.api.repository.dao.bussiness.jpa.ItRelationJpaRepository;
import com.hiit.api.repository.entity.business.it.ItRelationEntity;
import org.springframework.data.repository.RepositoryDefinition;

@RepositoryDefinition(domainClass = ItRelationEntity.class, idClass = Long.class)
public interface ItRelationRepository extends ItRelationJpaRepository {}
