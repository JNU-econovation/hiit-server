package com.hiit.api.repository.dao.bussiness;

import com.hiit.api.repository.dao.bussiness.custom.RegisteredItCustomRepository;
import com.hiit.api.repository.dao.bussiness.jpa.RegisteredItJpaRepository;
import com.hiit.api.repository.entity.business.it.RegisteredItEntity;
import org.springframework.data.repository.RepositoryDefinition;

@RepositoryDefinition(domainClass = RegisteredItEntity.class, idClass = Long.class)
public interface RegisteredItRepository
		extends RegisteredItJpaRepository, RegisteredItCustomRepository {}
