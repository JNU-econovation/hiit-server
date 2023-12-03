package com.hiit.api.repository.dao.bussiness;

import com.hiit.api.repository.dao.bussiness.custom.InItCustomRepository;
import com.hiit.api.repository.dao.bussiness.jpa.InItJpaRepository;
import com.hiit.api.repository.entity.business.it.InItEntity;
import org.springframework.data.repository.RepositoryDefinition;

@RepositoryDefinition(domainClass = InItEntity.class, idClass = Long.class)
public interface InItRepository extends InItJpaRepository, InItCustomRepository {}
