package com.hiit.api.repository.dao.bussiness;

import com.hiit.api.repository.dao.bussiness.custom.WithCustomRepository;
import com.hiit.api.repository.dao.bussiness.jpa.WithJpaRepository;
import com.hiit.api.repository.entity.business.with.WithEntity;
import org.springframework.data.repository.RepositoryDefinition;

@RepositoryDefinition(domainClass = WithEntity.class, idClass = Long.class)
public interface WithRepository extends WithJpaRepository, WithCustomRepository {}
