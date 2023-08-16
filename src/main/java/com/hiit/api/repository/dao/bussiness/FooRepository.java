package com.hiit.api.repository.dao.bussiness;

import com.hiit.api.repository.dao.bussiness.custom.FooCustomRepository;
import com.hiit.api.repository.dao.bussiness.jpa.FooJpaRepository;
import com.hiit.api.repository.entity.business.FooEntity;
import org.springframework.data.repository.RepositoryDefinition;

@RepositoryDefinition(domainClass = FooEntity.class, idClass = Long.class)
public interface FooRepository extends FooJpaRepository, FooCustomRepository {}
